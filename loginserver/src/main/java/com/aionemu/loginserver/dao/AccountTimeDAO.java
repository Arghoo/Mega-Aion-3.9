package com.aionemu.loginserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aionemu.commons.database.DatabaseFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.loginserver.model.AccountTime;

/**
 * DAO to manage account time
 */
public class AccountTimeDAO
{
	private static final Logger log = LoggerFactory.getLogger(AccountTimeDAO.class);

	/**
	 * Updates @link com.aionemu.loginserver.model.AccountTime data of account
	 *
	 * @param accountId   account id
	 * @param accountTime account time set
	 * @return was update successfull or not
	 */
	public static boolean updateAccountTime(final int accountId, final AccountTime accountTime)
	{
		return DB.insertUpdate("REPLACE INTO account_time (account_id, last_active, expiration_time, "
			+ "session_duration, accumulated_online, accumulated_rest, penalty_end) values " + "(?,?,?,?,?,?,?)", preparedStatement -> {
				preparedStatement.setLong(1, accountId);
				preparedStatement.setTimestamp(2, accountTime.getLastLoginTime());
				preparedStatement.setTimestamp(3, accountTime.getExpirationTime());
				preparedStatement.setLong(4, accountTime.getSessionDuration());
				preparedStatement.setLong(5, accountTime.getAccumulatedOnlineTime());
				preparedStatement.setLong(6, accountTime.getAccumulatedRestTime());
				preparedStatement.setTimestamp(7, accountTime.getPenaltyEnd());
				preparedStatement.execute();
			});
	}

	/**
	 * Updates @link com.aionemu.loginserver.model.AccountTime data of account
	 *
	 * @param accountId
	 * @return AccountTime
	 */
	public static AccountTime getAccountTime(int accountId)
	{
		AccountTime accountTime = null;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = DB.prepareStatement("SELECT * FROM account_time WHERE account_id = ?")) {
			st.setLong(1, accountId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					accountTime = new AccountTime();
					accountTime.setLastLoginTime(rs.getTimestamp("last_active"));
					accountTime.setSessionDuration(rs.getLong("session_duration"));
					accountTime.setAccumulatedOnlineTime(rs.getLong("accumulated_online"));
					accountTime.setAccumulatedRestTime(rs.getLong("accumulated_rest"));
					accountTime.setPenaltyEnd(rs.getTimestamp("penalty_end"));
					accountTime.setExpirationTime(rs.getTimestamp("expiration_time"));
				}
			}
		} catch (Exception e) {
			log.error("Can't get account time for account with id: " + accountId, e);
		}

		return accountTime;
	}
}
