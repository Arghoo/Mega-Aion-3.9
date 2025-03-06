package com.aionemu.gameserver.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.player.BlockList;
import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;

/**
 * Responsible for saving and loading data on players' block lists
 *
 * @author Ben
 */
public abstract class BlockListDAO
{
	public static final String LOAD_QUERY = "SELECT blocked_player, reason FROM blocks WHERE player=?";
	public static final String ADD_QUERY = "INSERT INTO blocks (player, blocked_player, reason) VALUES (?, ?, ?)";
	public static final String DEL_QUERY = "DELETE FROM blocks WHERE player=? AND blocked_player=?";
	public static final String SET_REASON_QUERY = "UPDATE blocks SET reason=? WHERE player=? AND blocked_player=?";

	private static Logger log = LoggerFactory.getLogger(BlockListDAO.class);

	/**
	 * Loads the blocklist for the player given
	 *
	 * @param player
	 * @return BlockList
	 */
	public static BlockList load(final Player player)
	{
		final Map<Integer, BlockedPlayer> list = new HashMap<>();

		DB.select(LOAD_QUERY, new ParamReadStH()
		{
			@Override
			public void handleRead(ResultSet rset) throws SQLException
			{
				while (rset.next()) {
					int blockedOid = rset.getInt("blocked_player");
					PlayerCommonData pcd = PlayerDAO.loadPlayerCommonData(blockedOid);
					if (pcd == null) {
						log.error("Attempt to load block list for " + player.getName() + " tried to load a player which does not exist: " + blockedOid);
					} else {
						list.put(blockedOid, new BlockedPlayer(pcd, rset.getString("reason")));
					}
				}

			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException
			{
				stmt.setInt(1, player.getObjectId());
			}
		});
		return new BlockList(list);
	}

	/**
	 * Adds the given object id to the list of blocked players for the given player
	 *
	 * @param playerObjId  ID of player to edit the blocklist of
	 * @param objIdToBlock ID of player to add to the blocklist
	 * @return Success
	 */
	public static boolean addBlockedUser(final int playerObjId, final int objIdToBlock, final String reason)
	{
		return DB.insertUpdate(ADD_QUERY, stmt -> {
			stmt.setInt(1, playerObjId);
			stmt.setInt(2, objIdToBlock);
			stmt.setString(3, reason);
			stmt.execute();
		});
	}

	/**
	 * Deletes the given object id from the list of blocked players for the given player
	 *
	 * @param playerObjId   ID of player to edit the blocklist of
	 * @param objIdToDelete ID of player to remove from the blocklist
	 * @return Success
	 */
	public static boolean delBlockedUser(final int playerObjId, final int objIdToDelete)
	{
		return DB.insertUpdate(DEL_QUERY, stmt -> {
			stmt.setInt(1, playerObjId);
			stmt.setInt(2, objIdToDelete);
			stmt.execute();
		});
	}

	/**
	 * Sets the reason for blocking a player
	 *
	 * @param playerObjId  Object ID of the player who is list is being edited
	 * @param blockedPlayerObjId Object ID of the player who is reason is being edited
	 * @param reason       The reason to be set
	 * @return true or false
	 */
	public static boolean setReason(final int playerObjId, final int blockedPlayerObjId, final String reason)
	{
		return DB.insertUpdate(SET_REASON_QUERY, stmt -> {
			stmt.setString(1, reason);
			stmt.setInt(2, playerObjId);
			stmt.setInt(3, blockedPlayerObjId);
			stmt.execute();
		});
	}
}
