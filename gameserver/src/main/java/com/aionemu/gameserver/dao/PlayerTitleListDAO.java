/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.title.TitleList;
import com.aionemu.gameserver.model.gameobjects.player.title.Title;

/**
 * @author xavier
 */
public class PlayerTitleListDAO
{
	private static final Logger log = LoggerFactory.getLogger(PlayerTitleListDAO.class);

	private static final String LOAD_QUERY = "SELECT `title_id`, `remaining` FROM `player_titles` WHERE `player_id`=?";
	private static final String INSERT_QUERY = "INSERT INTO `player_titles`(`player_id`,`title_id`, `remaining`) VALUES (?,?,?)";
	private static final String DELETE_QUERY = "DELETE FROM `player_titles` WHERE `player_id`=? AND `title_id` =?;";

	public static TitleList loadTitleList(final int playerId)
	{
		final TitleList tl = new TitleList();

		DB.select(LOAD_QUERY, new ParamReadStH()
		{
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException
			{
				stmt.setInt(1, playerId);
			}

			@Override
			public void handleRead(ResultSet rset) throws SQLException
			{
				while (rset.next()) {
					int id = rset.getInt("title_id");
					int remaining = rset.getInt("remaining");
					tl.addEntry(id, remaining);
				}
			}
		});
		return tl;
	}

	public static boolean storeTitles(Player player, Title entry)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			stmt.setInt(2, entry.getId());
			stmt.setInt(3, entry.getExpireTime());
			stmt.execute();
		} catch (Exception e) {
			log.error("Could not store emotionId for player " + player.getObjectId() + " from DB: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	public static boolean removeTitle(int playerId, int titleId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, titleId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Could not delete title for player " + playerId + " from DB: " + e.getMessage(), e);
			return false;
		}

		return true;
	}
}
