package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Rolandas
 */
public class HouseObjectCooldownsDAO
{
	private static final Logger log = LoggerFactory.getLogger(HouseObjectCooldownsDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `house_object_cooldowns` (`player_id`, `object_id`, `reuse_time`) VALUES (?,?,?)";
	public static final String DELETE_QUERY = "DELETE FROM `house_object_cooldowns` WHERE `player_id`=?";
	public static final String SELECT_QUERY = "SELECT `object_id`, `reuse_time` FROM `house_object_cooldowns` WHERE `player_id`=?";

	public static void loadHouseObjectCooldowns(final Player player)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			try (ResultSet rset = stmt.executeQuery()) {
				FastMap<Integer, Long> houseObjectCoolDowns = new FastMap<>();
				while (rset.next()) {
					int objectId = rset.getInt("object_id");
					long reuseTime = rset.getLong("reuse_time");
					int delay = (int) ((reuseTime - System.currentTimeMillis()) / 1000);
					if (delay > 0) {
						houseObjectCoolDowns.put(objectId, reuseTime);
					}
				}
				player.getHouseObjectCooldownList().setHouseObjectCooldowns(houseObjectCoolDowns);
			}
		} catch (SQLException e) {
			log.error("LoadHouseObjectCooldowns", e);
		}
	}

	public static void storeHouseObjectCooldowns(final Player player)
	{
		deleteHouseObjectCoolDowns(player);

		for (Map.Entry<Integer, Long> entry : player.getHouseObjectCooldownList().getHouseObjectCooldowns().entrySet()) {
			final int templateId = entry.getKey();
			final long reuseTime = entry.getValue();

			if (reuseTime < System.currentTimeMillis())
				continue;

			try (Connection con = DatabaseFactory.getConnection();
				 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
				stmt.setInt(1, player.getObjectId());
				stmt.setInt(2, templateId);
				stmt.setLong(3, reuseTime);
				stmt.execute();
			} catch (SQLException e) {
				log.error("storeHouseObjectCoolDowns", e);
			}
		}
	}

	private static void deleteHouseObjectCoolDowns(final Player player)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			stmt.execute();
		} catch (SQLException e) {
			log.error("deleteHouseObjectCoolDowns", e);
		}
	}
}
