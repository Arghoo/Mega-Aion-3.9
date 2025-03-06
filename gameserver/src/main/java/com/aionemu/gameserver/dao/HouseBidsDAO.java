package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.house.PlayerHouseBid;

/**
 * @author Rolandas
 */
public class HouseBidsDAO
{
	private static final Logger log = LoggerFactory.getLogger(HouseBidsDAO.class);

	public static final String LOAD_QUERY = "SELECT * FROM `house_bids`";
	public static final String INSERT_QUERY = "INSERT INTO `house_bids` (`player_id`,`house_id`, `bid`, `bid_time`) VALUES (?, ?, ?, ?)";
	public static final String DELETE_QUERY = "DELETE FROM `house_bids` WHERE `house_id` = ?";
	public static final String UPDATE_QUERY = "UPDATE `house_bids` SET bid = ?, bid_time = ? WHERE player_id = ? AND house_id = ?";

	public static Set<PlayerHouseBid> loadBids()
	{
		Set<PlayerHouseBid> results = new HashSet<>();
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(LOAD_QUERY)) {
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int playerId = rset.getInt("player_id");
					int houseId = rset.getInt("house_id");
					long bidOffer = rset.getLong("bid");
					Timestamp time = rset.getTimestamp("bid_time");
					PlayerHouseBid bid = new PlayerHouseBid(playerId, houseId, bidOffer, time);
					results.add(bid);
				}
			}
		} catch (Exception e) {
			log.error("Cannot read house bids", e);
		}

		return results;
	}

	public static boolean addBid(int playerId, int houseId, long bidOffer, Timestamp time)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, houseId);
			stmt.setLong(3, bidOffer);
			stmt.setTimestamp(4, time);
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot insert house bid", e);
			return false;
		}

		return true;
	}

	public static void changeBid(int playerId, int houseId, long newBidOffer, Timestamp time)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY)) {
			stmt.setLong(1, newBidOffer);
			stmt.setTimestamp(2, time);
			stmt.setInt(3, playerId);
			stmt.setInt(4, houseId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot update house bid", e);
		}
	}

	public static void deleteHouseBids(int houseId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, houseId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot delete house bids", e);
		}
	}
}
