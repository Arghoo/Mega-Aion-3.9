/*
 * This file is part of mega-aion <mega-aion.com>.
 *
 * mega-aion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mega-aion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mega-aion.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * @author synchro2
 */
public class CraftCooldownsDAO
{
	private static final Logger log = LoggerFactory.getLogger(CraftCooldownsDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `craft_cooldowns` (`player_id`, `delay_id`, `reuse_time`) VALUES (?,?,?)";
	public static final String DELETE_QUERY = "DELETE FROM `craft_cooldowns` WHERE `player_id`=?";
	public static final String SELECT_QUERY = "SELECT `delay_id`, `reuse_time` FROM `craft_cooldowns` WHERE `player_id`=?";

	public static void loadCraftCooldowns(Player player)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			try (ResultSet rset = stmt.executeQuery()) {
				FastMap<Integer, Long> craftCoolDowns = new FastMap<>();
				while (rset.next()) {
					int delayId = rset.getInt("delay_id");
					long reuseTime = rset.getLong("reuse_time");
					int delay = (int) ((reuseTime - System.currentTimeMillis()) / 1000);
					if (delay > 0) {
						craftCoolDowns.put(delayId, reuseTime);
					}
				}
				player.getCraftCooldownList().setCraftCoolDowns(craftCoolDowns);
			}
		} catch (SQLException e) {
			log.error("Couldn't load craft cooldowns for " + player, e);
		}
	}

	public static void storeCraftCooldowns(final Player player)
	{
		deleteCraftCoolDowns(player);

		for (Map.Entry<Integer, Long> entry : player.getCraftCooldownList().getCraftCoolDowns().entrySet()) {
			int delayId = entry.getKey();
			long reuseTime = entry.getValue();

			if (reuseTime < System.currentTimeMillis())
				continue;

			try (Connection con = DatabaseFactory.getConnection();
				 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
				stmt.setInt(1, player.getObjectId());
				stmt.setInt(2, delayId);
				stmt.setLong(3, reuseTime);
				stmt.execute();
			} catch (SQLException e) {
				log.error("Couldn't store craft cooldowns for " + player, e);
			}
		}
	}

	private static void deleteCraftCoolDowns(final Player player)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			stmt.execute();
		} catch (SQLException e) {
			log.error("Couldn't delete craft cooldowns for " + player, e);
		}
	}
}
