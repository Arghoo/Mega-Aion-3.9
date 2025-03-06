package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;

/**
 * @author Ben
 */
public class FriendListDAO
{
	private static final Logger log = LoggerFactory.getLogger(FriendListDAO.class);

	public static final String LOAD_QUERY = "SELECT * FROM `friends` WHERE `player`=?";
	public static final String ADD_QUERY = "INSERT INTO `friends` (`player`,`friend`) VALUES (?, ?)";
	public static final String DEL_QUERY = "DELETE FROM friends WHERE player = ? AND friend = ?";

	/**
	 * Loads the friend list for the given player
	 *
	 * @param player Player to get friend list of
	 * @return FriendList for player
	 */
	public static FriendList load(final Player player)
	{
		List<Friend> friends = new ArrayList<>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(LOAD_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				int objId = rset.getInt("friend");
				PlayerCommonData pcd = PlayerDAO.loadPlayerCommonData(objId);
				if (pcd != null) {
					Friend friend = new Friend(pcd);
					friends.add(friend);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore FriendList data for player: " + player.getObjectId() + " from DB: " + e.getMessage(), e);
		}

		return new FriendList(player, friends);
	}

	/**
	 * Makes the given players friends
	 * <ul>
	 * <li>Note: Adds for both players</li>
	 * </ul>
	 *
	 * @param player Player who is adding
	 * @param friend Friend to add to the friend list
	 * @return Success
	 */
	public static boolean addFriends(final Player player, final Player friend)
	{
		return DB.insertUpdate(ADD_QUERY, ps -> {
			ps.setInt(1, player.getObjectId());
			ps.setInt(2, friend.getObjectId());
			ps.addBatch();

			ps.setInt(1, friend.getObjectId());
			ps.setInt(2, player.getObjectId());
			ps.addBatch();

			ps.executeBatch();
		});
	}

	/**
	 * Deletes the friends from eachothers lists
	 *
	 * @param playerOid PlayerId who is deleting
	 * @param friendOid PlayerId of friend to delete
	 * @return Success
	 */
	public static boolean delFriends(final int playerOid, final int friendOid)
	{
		return DB.insertUpdate(DEL_QUERY, ps -> {
			ps.setInt(1, playerOid);
			ps.setInt(2, friendOid);
			ps.addBatch();

			ps.setInt(1, friendOid);
			ps.setInt(2, playerOid);
			ps.addBatch();

			ps.executeBatch();
		});
	}
}
