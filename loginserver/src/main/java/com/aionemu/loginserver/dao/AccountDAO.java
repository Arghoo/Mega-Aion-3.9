package com.aionemu.loginserver.dao;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.loginserver.model.Account;

import com.aionemu.loginserver.model.AccountTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * DAO that manages accounts.
 *
 * @author SoulKeeper
 */
public class AccountDAO
{
	private static final Logger log = LoggerFactory.getLogger(AccountDAO.class);

	/**
	 * Returns account by name or null
	 *
	 * @param name account name
	 * @return account object or null
	 */
	public static Account getAccount(String name)
	{
		return getAccount("SELECT * FROM account_data WHERE `name` = ?", name);
	}

	public static Account getAccount(int id)
	{
		return getAccount("SELECT * FROM account_data WHERE `id` = ?", id);
	}

	private static Account getAccount(String accountQuery, Object accountQueryParam)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement(accountQuery)) {
			st.setObject(1, accountQueryParam);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Account account = new Account();
					account.setId(rs.getInt("id"));
					account.setName(rs.getString("name"));
					account.setPasswordHash(rs.getString("password"));
					account.setAccessLevel(rs.getByte("access_level"));
					account.setMembership(rs.getByte("membership"));
					account.setActivated(rs.getByte("activated"));
					account.setLastServer(rs.getByte("last_server"));
					account.setLastIp(rs.getString("last_ip"));
					account.setLastMac(rs.getString("last_mac"));
					account.setIpForce(rs.getString("ip_force"));
					return account;
				}
			}
		} catch (SQLException e) {
			log.error("Could not load account for: " + accountQueryParam, e);
		}
		return null;
	}

	/**
	 * Reruns account count If error occured - returns -1
	 *
	 * @return account count
	 */
	public static int getAccountCount()
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement("SELECT count(*) AS c FROM account_data")) {
			try (ResultSet rs = st.executeQuery()) {
				rs.next();
				return rs.getInt("c");
			}
		} catch (SQLException e) {
			log.error("Can't get account count", e);
		}

		return -1;
	}

	/**
	 * Inserts new account to database. Sets account ID to id that was generated by DB.
	 *
	 * @param account account to insert
	 * @return true if was inserted, false in other case
	 */
	public static boolean insertAccount(Account account)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement("INSERT INTO account_data(`name`, `password`, access_level, membership, activated, last_server, last_ip, last_mac, ip_force, toll) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				 Statement.RETURN_GENERATED_KEYS)) {

			st.setString(1, account.getName());
			st.setString(2, account.getPasswordHash());
			st.setByte(3, account.getAccessLevel());
			st.setByte(4, account.getMembership());
			st.setByte(5, account.getActivated());
			st.setByte(6, account.getLastServer());
			st.setString(7, account.getLastIp());
			st.setString(8, account.getLastMac());
			st.setString(9, account.getIpForce());
			st.setLong(10, 0);

			if (st.executeUpdate() == 0)
				throw new SQLException();
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (!rs.next())
					throw new SQLException("Could not get ID of created account");
				account.setId(rs.getInt(1));
			}
			account.setAccountTime(new AccountTime());
			//account.setCreationDate(new Timestamp(System.currentTimeMillis()));
			return true;
		} catch (SQLException e) {
			log.error("Could not insert account for: " + account.getName(), e);
		}

		return false;
	}

	/**
	 * Updates account in database
	 *
	 * @param account account to update
	 * @return true if was updated, false in other case
	 */
	public static boolean updateAccount(Account account)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement("UPDATE account_data SET `name` = ?, `password` = ?, access_level = ?, membership = ?, last_server = ?, last_ip = ?, last_mac = ?, ip_force = ?, membershipExpiry = ? WHERE `id` = ?")) {
			st.setString(1, account.getName());
			st.setString(2, account.getPasswordHash());
			st.setByte(3, account.getAccessLevel());
			st.setByte(4, account.getMembership());
			st.setByte(5, account.getLastServer());
			st.setString(6, account.getLastIp());
			st.setString(7, account.getLastMac());
			st.setString(8, account.getIpForce());
			st.setTimestamp(9, account.getMembershipExpiry());
			st.setInt(10, account.getId());
			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error("Could not update account for: " + account.getName(), e);
		}

		return false;
	}

	/**
	 * Updates lastServer field of account
	 *
	 * @param accountId  account id
	 * @param lastServer last accessed server
	 * @return was updated successful or not
	 */
	public static boolean updateLastServer(final int accountId, final byte lastServer)
	{
		return DB.insertUpdate("UPDATE account_data SET last_server = ? WHERE id = ?", preparedStatement -> {
			preparedStatement.setByte(1, lastServer);
			preparedStatement.setInt(2, accountId);
			preparedStatement.execute();
		});
	}

	/**
	 * Updates last ip that was used to access an account
	 *
	 * @param accountId account id
	 * @param ip        ip address
	 * @return was update successful or not
	 */
	public static boolean updateLastIp(final int accountId, final String ip)
	{
		return DB.insertUpdate("UPDATE account_data SET last_ip = ? WHERE id = ?", preparedStatement -> {
			preparedStatement.setString(1, ip);
			preparedStatement.setInt(2, accountId);
			preparedStatement.execute();
		});
	}

	/**
	 * Get last ip that was used to access an account
	 *
	 * @param accountId account id
	 * @return ip address
	 */
	public static String getLastIp(final int accountId)
	{
		String lastIp = "";

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement("SELECT `last_ip` FROM `account_data` WHERE `id` = ?")) {
			st.setInt(1, accountId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					lastIp = rs.getString("last_ip");
				}
			}
		} catch (Exception e) {
			log.error("Can't select last IP of account ID: " + accountId, e);
		}

		return lastIp;
	}

	/**
	 * Updates last mac that was used to access an account
	 *
	 * @param accountId account id
	 * @param mac       mac address
	 * @return was update successful or not
	 */
	public static boolean updateLastMac(final int accountId, final String mac)
	{
		return DB.insertUpdate("UPDATE `account_data` SET `last_mac` = ? WHERE `id` = ?", preparedStatement -> {
			preparedStatement.setString(1, mac);
			preparedStatement.setInt(2, accountId);
			preparedStatement.execute();
		});
	}

	/**
	 * Updates account membership
	 *
	 * @param accountId account id
	 * @return was update successful or not
	 */
	public static boolean updateMembership(final int accountId)
	{
		return DB.insertUpdate("UPDATE account_data SET membership = old_membership, membershipExpiry = NULL WHERE id = ? and membershipExpiry < CURRENT_TIMESTAMP", preparedStatement -> {
			preparedStatement.setInt(1, accountId);
			preparedStatement.execute();
		});
	}

	/**
	 * Deletion of all accounts, inactive for more than dayOfInactivity days
	 *
	 * @param daysOfInactivity
	 */
	public static void deleteInactiveAccounts(int daysOfInactivity)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement statement = con.prepareStatement("DELETE FROM account_data WHERE id IN (SELECT account_id FROM account_time WHERE UNIX_TIMESTAMP(CURDATE())-UNIX_TIMESTAMP(last_active) > ? * 24 * 60 * 60)")) {
			statement.setInt(1, daysOfInactivity);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Some crap, can't set int parameter to PreparedStatement", e);
		}
	}
}
