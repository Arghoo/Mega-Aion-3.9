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
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.MacroList;

/**
 * Macros DAO
 * <p/>
 * Created on: 13.07.2009 17:05:56
 *
 * @author Aquanox
 */
public abstract class PlayerMacrosDAO
{
	private static final Logger log = LoggerFactory.getLogger(PlayerMacrosDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `player_macros` (`player_id`, `order`, `macro`) VALUES (?,?,?)";
	public static final String UPDATE_QUERY = "UPDATE `player_macros` SET `macro`=? WHERE `player_id`=? AND `order`=?";
	public static final String DELETE_QUERY = "DELETE FROM `player_macros` WHERE `player_id`=? AND `order`=?";
	public static final String SELECT_QUERY = "SELECT `order`, `macro` FROM `player_macros` WHERE `player_id`=?";

	/**
	 * Returns a list of macros for player
	 *
	 * @param playerId Player object id.
	 * @return a list of macros for player
	 */
	public static MacroList restoreMacros(final int playerId)
	{
		final Map<Integer, String> macros = new HashMap<>();
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			try (ResultSet rset = stmt.executeQuery()) {
				log.debug("[DAO: PlayerMacrosDAO] loading macros for playerId: " + playerId);
				while (rset.next()) {
					int order = rset.getInt("order");
					String text = rset.getString("macro");
					macros.put(order, text);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore MacroList data for player " + playerId + " from DB: " + e.getMessage(), e);
		}

		return new MacroList(macros);
	}

	/**
	 * Add macro information into database
	 *
	 * @param playerId      player object id
	 * @param macroPosition macro order # of player
	 * @param macro         macro contents.
	 */
	public static void addMacro(final int playerId, final int macroPosition, final String macro)
	{
		DB.insertUpdate(INSERT_QUERY, stmt -> {
			log.debug("[DAO: PlayerMacrosDAO] storing macro " + playerId + " " + macroPosition);
			stmt.setInt(1, playerId);
			stmt.setInt(2, macroPosition);
			stmt.setString(3, macro);
			stmt.execute();
		});
	}

	/**
	 * Update macro information into database
	 *
	 * @param playerId      player object id
	 * @param macroPosition macro order # of player
	 * @param macro         macro contents.
	 */
	public static void updateMacro(final int playerId, final int macroPosition, final String macro)
	{
		DB.insertUpdate(UPDATE_QUERY, stmt -> {
			log.debug("[DAO: PlayerMacros] updating macro " + playerId + " " + macroPosition);
			stmt.setString(1, macro);
			stmt.setInt(2, playerId);
			stmt.setInt(3, macroPosition);
			stmt.execute();
		});
	}

	/**
	 * Remove macro in database
	 *
	 * @param playerId      player object id
	 * @param macroPosition order of macro in macro list
	 */
	public static void deleteMacro(final int playerId, final int macroPosition)
	{
		DB.insertUpdate(DELETE_QUERY, stmt -> {
			log.debug("[DAO: PlayerMacros] removing macro " + playerId + " " + macroPosition);
			stmt.setInt(1, playerId);
			stmt.setInt(2, macroPosition);
			stmt.execute();
		});
	}
}
