package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author synchro2
 */
public class WeddingDAO
{
	private static final Logger log = LoggerFactory.getLogger(WeddingDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `weddings` (`player1`, `player2`) VALUES (?,?)";
	public static final String SELECT_QUERY = "SELECT `player1`, `player2` FROM `weddings` WHERE `player1`=? OR `player2`=?";
	public static final String DELETE_QUERY = "DELETE FROM `weddings` WHERE (`player1`=? AND `player2`=?) OR (`player2`=? AND `player1`=?)";

	public static int loadPartnerId(final Player player)
	{
		int playerId = player.getObjectId();
		int partnerId = 0;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, playerId);
			try (ResultSet rset = stmt.executeQuery()) {
				int partner1Id = 0;
				int partner2Id = 0;
				if (rset.next()) {
					partner1Id = rset.getInt("player1");
					partner2Id = rset.getInt("player2");
				}
				partnerId = playerId == partner1Id ? partner2Id : partner1Id;
			}
		} catch (Exception e) {
			log.error("Could not get partner for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return partnerId;
	}

	public static void storeWedding(final Player partner1, final Player partner2)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, partner1.getObjectId());
			stmt.setInt(2, partner2.getObjectId());
			stmt.execute();
		} catch (SQLException e) {
			log.error("storeWeddings", e);
		}
	}

	public static void deleteWedding(final Player partner1, final Player partner2)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, partner1.getObjectId());
			stmt.setInt(2, partner2.getObjectId());
			stmt.setInt(3, partner1.getObjectId());
			stmt.setInt(4, partner2.getObjectId());
			stmt.execute();
		} catch (SQLException e) {
			log.error("deleteWedding", e);
		}
	}
}
