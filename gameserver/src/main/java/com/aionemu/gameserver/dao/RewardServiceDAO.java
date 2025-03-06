package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.DatabaseFactory;

import javolution.util.FastList;

import com.aionemu.gameserver.model.templates.rewards.RewardEntryItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KID
 */
public class RewardServiceDAO
{
	private static final Logger log = LoggerFactory.getLogger(RewardServiceDAO.class);
	public static final String UPDATE_QUERY = "UPDATE `web_reward` SET `rewarded`=?, received=NOW() WHERE `unique`=?";
	public static final String SELECT_QUERY = "SELECT * FROM `web_reward` WHERE `item_owner`=? AND `rewarded`=?";

	public static List<RewardEntryItem> getAvailable(int playerId)
	{
		List<RewardEntryItem> list = new ArrayList<>();
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, 0);

			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int unique = rset.getInt("unique");
					int item_id = rset.getInt("item_id");
					long count = rset.getLong("item_count");
					list.add(new RewardEntryItem(unique, item_id, count));
				}
			}
		} catch (Exception e) {
			log.warn("getAvailable() for " + playerId + " from DB: " + e.getMessage(), e);
		}

		return list;
	}

	public static void uncheckAvailable(FastList<Integer> ids)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY)) {
			con.setAutoCommit(false);
			for (int uniqid : ids) {
				stmt.setInt(1, 1);
				stmt.setInt(2, uniqid);
				stmt.addBatch();
			}
			stmt.executeBatch();
			con.commit();
		} catch (Exception e) {
			log.error("uncheckAvailable", e);
		}
	}
}
