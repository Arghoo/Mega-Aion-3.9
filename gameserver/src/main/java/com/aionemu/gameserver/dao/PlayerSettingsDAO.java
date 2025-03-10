/*
 * This file is part of aion-unique <aion-unique.com>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerSettings;

/**
 * @author ATracer
 */
public class PlayerSettingsDAO
{
	private static final Logger log = LoggerFactory.getLogger(PlayerSettingsDAO.class);

	/**
	 * @param player
	 */
	public static void loadSettings(final Player player)
	{
		final int playerId = player.getObjectId();
		final PlayerSettings playerSettings = new PlayerSettings();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement statement = con.prepareStatement("SELECT * FROM player_settings WHERE player_id = ?")) {
			statement.setInt(1, playerId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int type = resultSet.getInt("settings_type");
					switch (type) {
						case 0:
							playerSettings.setUiSettings(resultSet.getBytes("settings"));
							break;
						case 1:
							playerSettings.setShortcuts(resultSet.getBytes("settings"));
							break;
						case 2:
							playerSettings.setHouseBuddies(resultSet.getBytes("settings"));
							break;
						case -1:
							playerSettings.setDisplay(resultSet.getInt("settings"));
							break;
						case -2:
							playerSettings.setDeny(resultSet.getInt("settings"));
							break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Could not restore PlayerSettings data for player " + playerId + " from DB: " + e.getMessage(), e);
		}

		playerSettings.setPersistentState(PersistentState.UPDATED);
		player.setPlayerSettings(playerSettings);
	}

	/**
	 * @param player
	 */
	public static void saveSettings(final Player player)
	{
		final int playerId = player.getObjectId();

		PlayerSettings playerSettings = player.getPlayerSettings();
		if (playerSettings.getPersistentState() == PersistentState.UPDATED)
			return;

		final byte[] uiSettings = playerSettings.getUiSettings();
		final byte[] shortcuts = playerSettings.getShortcuts();
		final byte[] houseBuddies = playerSettings.getHouseBuddies();
		final int display = playerSettings.getDisplay();
		final int deny = playerSettings.getDeny();

		if (uiSettings != null) {
			DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", stmt -> {
				stmt.setInt(1, playerId);
				stmt.setInt(2, 0);
				stmt.setBytes(3, uiSettings);
				stmt.execute();
			});
		}

		if (shortcuts != null) {
			DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", stmt -> {
				stmt.setInt(1, playerId);
				stmt.setInt(2, 1);
				stmt.setBytes(3, shortcuts);
				stmt.execute();
			});
		}

		if (houseBuddies != null) {
			DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", stmt -> {
				stmt.setInt(1, playerId);
				stmt.setInt(2, 2);
				stmt.setBytes(3, houseBuddies);
				stmt.execute();
			});
		}

		DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", stmt -> {
			stmt.setInt(1, playerId);
			stmt.setInt(2, -1);
			stmt.setInt(3, display);
			stmt.execute();
		});

		DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", stmt -> {
			stmt.setInt(1, playerId);
			stmt.setInt(2, -2);
			stmt.setInt(3, deny);
			stmt.execute();
		});
	}
}
