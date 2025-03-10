package com.aionemu.loginserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aionemu.commons.database.DatabaseFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.loginserver.model.base.BannedMacEntry;

/**
 * @author KID
 */
public class BannedMacDAO
{
	private static final Logger log = LoggerFactory.getLogger(BannedMacDAO.class);

	public static boolean update(BannedMacEntry entry)
	{
		boolean success = false;
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement ps = con.prepareStatement("REPLACE INTO `banned_mac` (`address`,`time`,`details`) VALUES (?,?,?)")) {
			ps.setString(1, entry.getMac());
			ps.setTimestamp(2, entry.getTime());
			ps.setString(3, entry.getDetails());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error("Error storing BannedMacEntry " + entry.getMac(), e);
		}

		return false;
	}

	public static boolean remove(String address)
	{
		boolean success = false;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement ps = con.prepareStatement("DELETE FROM `banned_mac` WHERE address=?")) {
			ps.setString(1, address);
			success = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error("Error removing BannedMacEntry " + address, e);
		}

		return success;
	}

	public static Map<String, BannedMacEntry> load()
	{
		Map<String, BannedMacEntry> map = new HashMap<>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement ps = con.prepareStatement("SELECT `address`,`time`,`details` FROM `banned_mac`")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String address = rs.getString("address");
					map.put(address, new BannedMacEntry(address, rs.getTimestamp("time"), rs.getString("details")));
				}
			}
		} catch (SQLException e) {
			log.error("Error loading last saved server time", e);
		}
		return map;
	}

	public static void cleanExpiredBans()
	{
		DB.insertUpdate("DELETE FROM `banned_mac` WHERE time < current_date");
	}
}
