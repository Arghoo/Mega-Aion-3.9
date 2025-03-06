package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.PlayerScripts;

/**
 * @author Rolandas
 */
public class HouseScriptsDAO
{
	private static final Logger log = LoggerFactory.getLogger(HouseScriptsDAO.class);

	private static final String INSERT_QUERY = "INSERT INTO `house_scripts` (`house_id`,`index`,`script`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE house_id=VALUES(house_id), index=VALUES(index), script=VALUES(script)";
	public static final String DELETE_QUERY = "DELETE FROM `house_scripts` WHERE `house_id`=? AND `index`=?";
	private static final String SELECT_QUERY = "SELECT `index`,`script` FROM `house_scripts` WHERE `house_id`=?";

	public static PlayerScripts getPlayerScripts(int houseId)
	{
		PlayerScripts scripts = new PlayerScripts(houseId);
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, houseId);
			try (ResultSet rset = stmt.executeQuery()) {

				while (rset.next()) {
					int position = rset.getInt("index");
					String scriptXML = rset.getString("script");
					scripts.addScript(position, scriptXML);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore script data for houseId: " + houseId + " from DB: " + e.getMessage(), e);
		}

		return scripts;
	}

	public static void storeScript(int houseId, int position, String scriptXML)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, houseId);
			stmt.setInt(2, position);
			stmt.setString(3, scriptXML);
			stmt.executeUpdate();
		} catch (Exception e) {
			log.error("Could not save script data for houseId: {}", houseId, e);
		}
	}

	public static void deleteScript(int houseId, int position)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, houseId);
			stmt.setInt(2, position);
			stmt.executeUpdate();
		} catch (Exception e) {
			log.error("Could not delete script for houseId: {}", houseId, e);
		}
	}
}
