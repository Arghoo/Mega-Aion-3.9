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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.guide.Guide;

/**
 * @author xTz
 */
public class GuideDAO extends IDFactoryAwareDAO
{
	private static final Logger log = LoggerFactory.getLogger(GuideDAO.class);

	public static final String DELETE_QUERY = "DELETE FROM `guides` WHERE `guide_id`=?";
	public static final String SELECT_QUERY = "SELECT * FROM `guides` WHERE `player_id`=?";
	public static final String SELECT_GUIDE_QUERY = "SELECT * FROM `guides` WHERE `guide_id`=? AND `player_id`=?";

	public static boolean deleteGuide(int guide_id)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, guide_id);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error delete guide_id: " + guide_id, e);
			return false;
		}

		return true;
	}

	public static List<Guide> loadGuides(int playerId)
	{
		List<Guide> guides = new ArrayList<>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int guide_id = rset.getInt("guide_id");
					int player_id = rset.getInt("player_id");
					String title = rset.getString("title");

					Guide guide = new Guide(guide_id, player_id, title);
					guides.add(guide);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore Guide data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return guides;
	}

	public static Guide loadGuide(int player_id, int guide_id)
	{
		Guide guide = null;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_GUIDE_QUERY)) {
			stmt.setInt(1, guide_id);
			stmt.setInt(2, player_id);

			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					String title = rset.getString("title");
					guide = new Guide(guide_id, player_id, title);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore Survey data for player: " + player_id + " from DB: " + e.getMessage(), e);
		}

		return guide;
	}

	public static void saveGuide(int guide_id, Player player, String title)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("INSERT INTO guides(guide_id, title, player_id)" + "VALUES (?, ?, ?)")) {
			stmt.setInt(1, guide_id);
			stmt.setString(2, title);
			stmt.setInt(3, player.getObjectId());
			stmt.execute();
		} catch (Exception e) {
			log.error("Error saving playerName: " + player, e);
		}
	}

	public static int[] getUsedIDs()
	{
		return DB.getUsedIDs("guides", "guide_id");
	}
}
