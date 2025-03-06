package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.DatabaseFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ben
 */
public class ServerVariablesDAO
{
	private static Logger log = LoggerFactory.getLogger(ServerVariablesDAO.class);

	/**
	 * Loads the server variables stored in the database
	 *
	 * @return variable stored in database
	 */
	public static int load(String var)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement ps = con.prepareStatement("SELECT `value` FROM `server_variables` WHERE `key`=?")) {
			ps.setString(1, var);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return Integer.parseInt(rs.getString("value"));
			}
		} catch (SQLException e) {
			log.error("Error loading last saved server time", e);
		}

		return 0;
	}

	/**
	 * Stores the server variables
	 */
	public static boolean store(String var, int time)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement ps = con.prepareStatement("REPLACE INTO `server_variables` (`key`,`value`) VALUES (?,?)")) {
			ps.setString(1, var);
			ps.setString(2, String.valueOf(time));
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error("Error storing server time", e);
			return false;
		}
	}
}
