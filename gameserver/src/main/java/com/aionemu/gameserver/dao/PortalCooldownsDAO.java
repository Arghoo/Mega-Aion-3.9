package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PortalCooldownsDAO
{
	private static final Logger log = LoggerFactory.getLogger(PortalCooldownsDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `portal_cooldowns` (`player_id`, `world_id`, `reuse_time`) VALUES (?,?,?)";
	public static final String DELETE_QUERY = "DELETE FROM `portal_cooldowns` WHERE `player_id`=?";
	public static final String SELECT_QUERY = "SELECT `world_id`, `reuse_time` FROM `portal_cooldowns` WHERE `player_id`=?";

	/**
	 * @param player
	 */
	public static void loadPortalCooldowns(final Player player)
	{
		Map<Integer, Long> portalCoolDowns = new HashMap<>();
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int worldId = rset.getInt("world_id");
					long reuseTime = rset.getLong("reuse_time");
					if (reuseTime > System.currentTimeMillis()) {
						portalCoolDowns.put(worldId, reuseTime);
					}
				}
				player.getPortalCooldownList().setPortalCoolDowns(portalCoolDowns);
			}
		} catch (SQLException e) {
			log.error("LoadPortalCooldowns", e);
		}
	}

	/**
	 * @param player
	 */
	public static void storePortalCooldowns(final Player player)
	{
		deletePortalCooldowns(player);
		Map<Integer, Long> portalCoolDowns = player.getPortalCooldownList().getPortalCoolDowns();

		if (portalCoolDowns == null)
			return;

		for (Map.Entry<Integer, Long> entry : portalCoolDowns.entrySet()) {
			final int worldId = entry.getKey();
			final long reuseTime = entry.getValue();

			if (reuseTime < System.currentTimeMillis())
				continue;

			try (Connection con = DatabaseFactory.getConnection();
				 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
				stmt.setInt(1, player.getObjectId());
				stmt.setInt(2, worldId);
				stmt.setLong(3, reuseTime);
				stmt.execute();
			} catch (SQLException e) {
				log.error("storePortalCooldowns", e);
			}
		}
	}

	private static void deletePortalCooldowns(final Player player)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			stmt.execute();
		} catch (SQLException e) {
			log.error("deletePortalCooldowns", e);
		}
	}
}
