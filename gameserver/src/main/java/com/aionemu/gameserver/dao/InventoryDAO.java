/*
 * This file is part of aion-unique <aionu-unique.com>.
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
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import com.aionemu.gameserver.utils.idfactory.IDFactory;

import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.utils.GenericValidator;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.PlayerStorage;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.services.item.ItemService;

/**
 * @author ATracer
 */
public class InventoryDAO extends IDFactoryAwareDAO
{
	private static final Logger log = LoggerFactory.getLogger(InventoryDAO.class);

	public static final String SELECT_QUERY = "SELECT `item_unique_id`, `item_id`, `item_count`, `item_color`, `color_expires`, `item_creator`, `expire_time`, `activation_count`, `is_equiped`, `is_soul_bound`, `slot`, `enchant`, `item_skin`, `fusioned_item`, `optional_socket`, `optional_fusion_socket`, `charge`, `rnd_bonus` FROM `inventory` WHERE `item_owner`=? AND `item_location`=? AND `is_equiped`=?";
	public static final String INSERT_QUERY = "INSERT INTO `inventory` (`item_unique_id`, `item_id`, `item_count`, `item_color`, `color_expires`, `item_creator`, `expire_time`, `activation_count`, `item_owner`, `is_equiped`, is_soul_bound, `slot`, `item_location`, `enchant`, `item_skin`, `fusioned_item`, `optional_socket`, `optional_fusion_socket`, `charge`, `rnd_bonus`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_QUERY = "UPDATE inventory SET  item_count=?, item_color=?, color_expires=?, item_creator=?, expire_time=?, activation_count=?,item_owner=?, is_equiped=?, is_soul_bound=?, slot=?, item_location=?, enchant=?, item_skin=?, fusioned_item=?, optional_socket=?, optional_fusion_socket=?, charge=?, rnd_bonus=? WHERE item_unique_id=?";
	public static final String DELETE_QUERY = "DELETE FROM inventory WHERE item_unique_id=?";
	public static final String DELETE_CLEAN_QUERY = "DELETE FROM inventory WHERE item_owner=? AND item_location != 2"; // legion warehouse needs not to be excluded, since players and legions are IDAwareDAOs
	public static final String SELECT_ACCOUNT_QUERY = "SELECT `account_id` FROM `players` WHERE `id`=?";
	public static final String SELECT_LEGION_QUERY = "SELECT `legion_id` FROM `legion_members` WHERE `player_id`=?";
	public static final String DELETE_ACCOUNT_WH = "DELETE FROM inventory WHERE item_owner=? AND item_location=2";
	public static final String SELECT_QUERY2 = "SELECT * FROM `inventory` WHERE `item_owner`=? AND `item_location`=?";

	/**
	 * @param playerId
	 * @param storageType
	 * @return IStorage
	 */
	public static Storage loadStorage(int playerId, StorageType storageType)
	{
		final Storage inventory = new PlayerStorage(storageType);
		final int storage = storageType.getId();
		final int equipped = 0;

		if (storageType == StorageType.ACCOUNT_WAREHOUSE) {
			playerId = loadPlayerAccountId(playerId);
		}

		final int owner = playerId;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, owner);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				Item item = constructItem(storage, rset);
				item.setPersistentState(PersistentState.UPDATED);
				if (item.getItemTemplate() == null) {
					log.error(playerId + "loaded error item, itemUniqueId is: " + item.getObjectId());
				} else {
					inventory.onLoadHandler(item);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore storage data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return inventory;
	}

	public static List<Item> loadStorageDirect(int playerId, StorageType storageType)
	{
		List<Item> list = FastList.newInstance();
		final int storage = storageType.getId();

		if (storageType == StorageType.ACCOUNT_WAREHOUSE) {
			playerId = loadPlayerAccountId(playerId);
		}

		final int owner = playerId;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY2)) {
			stmt.setInt(1, owner);
			stmt.setInt(2, storageType.getId());
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				list.add(constructItem(storage, rset));
			}
		} catch (Exception e) {
			log.error("Could not restore loadStorageDirect data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}
		return list;
	}

	/**
	 * @param player Player
	 * @return Equipment
	 */
	public static Equipment loadEquipment(Player player)
	{
		Equipment equipment = new Equipment(player);

		int playerId = player.getObjectId();
		final int storage = 0;
		final int equipped = 1;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					Item item = constructItem(storage, rset);
					item.setPersistentState(PersistentState.UPDATED);
					equipment.onLoadHandler(item);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore Equipment data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return equipment;
	}

	/**
	 * @param playerId
	 */
	public static List<Item> loadEquipment(int playerId)
	{
		final List<Item> items = new ArrayList<Item>();
		final int storage = 0;
		final int equipped = 1;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				Item item = constructItem(storage, rset);
				items.add(item);
			}
		} catch (Exception e) {
			log.error("Could not restore Equipment data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return items;
	}

	public static boolean store(Player player)
	{
		int playerId = player.getObjectId();
		Integer accountId = player.getPlayerAccount() != null ? player.getPlayerAccount().getId() : null;
		Integer legionId = player.getLegion() != null ? player.getLegion().getLegionId() : null;

		List<Item> allPlayerItems = player.getDirtyItemsToUpdate();
		return store(allPlayerItems, playerId, accountId, legionId);
	}

	public static boolean store(Item item, Player player)
	{
		int playerId = player.getObjectId();
		int accountId = player.getPlayerAccount().getId();
		Integer legionId = player.getLegion() != null ? player.getLegion().getLegionId() : null;

		return store(item, playerId, accountId, legionId);
	}

	public static boolean store(Item item, int playerId)
	{
		return store(Collections.singletonList(item), playerId);
	}

	public static boolean store(List<Item> items, int playerId)
	{
		Integer accountId = null;
		Integer legionId = null;

		for (Item item : items) {
			if (accountId == null && item.getItemLocation() == StorageType.ACCOUNT_WAREHOUSE.getId()) {
				accountId = loadPlayerAccountId(playerId);
			}

			if (legionId == null && item.getItemLocation() == StorageType.LEGION_WAREHOUSE.getId()) {
				int localLegionId = loadLegionId(playerId);
				if (localLegionId > 0)
					legionId = localLegionId;
			}
		}

		return store(items, playerId, accountId, legionId);
	}

	/**
	 * @param item
	 */
	public static boolean store(Item item, Integer playerId, Integer accountId, Integer legionId)
	{
		return store(Collections.singletonList(item), playerId, accountId, legionId);
	}

	public static boolean store(List<Item> items, Integer playerId, Integer accountId, Integer legionId)
	{
		Collection<Item> itemsToUpdate = items.stream().filter(item -> item != null && PersistentState.NEW == item.getPersistentState()).collect(Collectors.toList());
		Collection<Item> itemsToInsert = items.stream().filter(item -> item != null && PersistentState.UPDATE_REQUIRED == item.getPersistentState()).collect(Collectors.toList());
		Collection<Item> itemsToDelete = items.stream().filter(item -> item != null && PersistentState.DELETED == item.getPersistentState()).collect(Collectors.toList());

		boolean deleteResult = false;
		boolean insertResult = false;
		boolean updateResult = false;

		try (Connection con = DatabaseFactory.getConnection()) {
			con.setAutoCommit(false);
			deleteResult = deleteItems(con, itemsToDelete);
			insertResult = insertItems(con, itemsToInsert, playerId, accountId, legionId);
			updateResult = updateItems(con, itemsToUpdate, playerId, accountId, legionId);
		} catch (SQLException e) {
			log.error("Can't save inventory for player: " + playerId, e);
		}

		for (Item item : items) {
			item.setPersistentState(PersistentState.UPDATED);
		}

		if (!itemsToDelete.isEmpty() && deleteResult) {
			ItemService.releaseItemIds(itemsToDelete);
		}

		return deleteResult && insertResult && updateResult;
	}

	/**
	 * @param playerId
	 */
	public static boolean deletePlayerItems(final int playerId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_CLEAN_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error Player all items. PlayerObjId: " + playerId, e);
			return false;
		}

		return true;
	}

	public static void deleteAccountWH(final int accountId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_ACCOUNT_WH)) {
			stmt.setInt(1, accountId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error deleting all items from account WH. AccountId: " + accountId, e);
		}
	}

	public static int[] getUsedIDs()
	{
		return DB.getUsedIDs("inventory", "item_unique_id");
	}

	private static Item constructItem(final int storage, ResultSet rset) throws SQLException
	{
		int itemUniqueId = rset.getInt("item_unique_id");
		int itemId = rset.getInt("item_id");
		long itemCount = rset.getLong("item_count");
		int itemColor = rset.getInt("item_color");
		int colorExpireTime = rset.getInt("color_expires");
		String itemCreator = rset.getString("item_creator");
		int expireTime = rset.getInt("expire_time");
		int activationCount = rset.getInt("activation_count");
		int isEquiped = rset.getInt("is_equiped");
		int isSoulBound = rset.getInt("is_soul_bound");
		long slot = rset.getLong("slot");
		int enchant = rset.getInt("enchant");
		int itemSkin = rset.getInt("item_skin");
		int fusionedItem = rset.getInt("fusioned_item");
		int optionalSocket = rset.getInt("optional_socket");
		int optionalFusionSocket = rset.getInt("optional_fusion_socket");
		int charge = rset.getInt("charge");
		Integer randomBonus = rset.getInt("rnd_bonus");

		return new Item(itemUniqueId, itemId, itemCount, itemColor, colorExpireTime, itemCreator, expireTime, activationCount, isEquiped == 1, isSoulBound == 1, slot, storage, enchant, itemSkin, fusionedItem, optionalSocket, optionalFusionSocket,
			charge, randomBonus);
	}

	private static int loadPlayerAccountId(final int playerId)
	{
		int accountId = 0;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ACCOUNT_QUERY)) {
			stmt.setInt(1, playerId);
			try (ResultSet rset = stmt.executeQuery()) {

				if (rset.next()) {
					accountId = rset.getInt("account_id");
				}
			}
		} catch (Exception e) {
			log.error("Could not restore accountId data for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return accountId;
	}

	public static int loadLegionId(final int playerId)
	{
		int legionId = 0;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_LEGION_QUERY)) {
			stmt.setInt(1, playerId);
			try (ResultSet rset = stmt.executeQuery()) {
				if (rset.next()) {
					legionId = rset.getInt("legion_id");
				}
			}
		} catch (Exception e) {
			log.error("Failed to load legion id for player id: " + playerId, e);
		}

		return legionId;
	}

	private static int getItemOwnerId(Item item, Integer playerId, Integer accountId, Integer legionId)
	{
		if (item.getItemLocation() == StorageType.ACCOUNT_WAREHOUSE.getId()) {
			return accountId;
		}

		if (item.getItemLocation() == StorageType.LEGION_WAREHOUSE.getId()) {
			return legionId != null ? legionId : playerId;
		}

		return playerId;
	}

	private static boolean insertItems(Connection con, Collection<Item> items, Integer playerId, Integer accountId, Integer legionId)
	{
		if (GenericValidator.isBlankOrNull(items)) {
			return true;
		}

		try (PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			for (Item item : items) {
				stmt.setInt(1, item.getObjectId());
				stmt.setInt(2, item.getItemTemplate().getTemplateId());
				stmt.setLong(3, item.getItemCount());
				stmt.setInt(4, item.getItemColor());
				stmt.setInt(5, item.getColorExpireTime());
				stmt.setString(6, item.getItemCreator());
				stmt.setInt(7, item.getExpireTime());
				stmt.setInt(8, item.getActivationCount());
				stmt.setInt(9, getItemOwnerId(item, playerId, accountId, legionId));
				stmt.setBoolean(10, item.isEquipped());
				stmt.setInt(11, item.isSoulBound() ? 1 : 0);
				stmt.setLong(12, item.getEquipmentSlot());
				stmt.setInt(13, item.getItemLocation());
				stmt.setInt(14, item.getEnchantLevel());
				stmt.setInt(15, item.getItemSkinTemplate().getTemplateId());
				stmt.setInt(16, item.getFusionedItemId());
				stmt.setInt(17, item.getOptionalSocket());
				stmt.setInt(18, item.getOptionalFusionSocket());
				stmt.setInt(19, item.getChargePoints());
				if (item.getBonusNumber() == null)
					stmt.setNull(20, Types.SMALLINT);
				else
					stmt.setInt(20, item.getBonusNumber());
				stmt.addBatch();
			}

			stmt.executeBatch();
			con.commit();
		} catch (Exception e) {
			log.error("Failed to execute insert batch", e);
			return false;
		}

		return true;
	}

	private static boolean updateItems(Connection con, Collection<Item> items, Integer playerId, Integer accountId, Integer legionId)
	{
		if (GenericValidator.isBlankOrNull(items)) {
			return true;
		}

		try (PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY)) {
			for (Item item : items) {
				stmt.setLong(1, item.getItemCount());
				stmt.setInt(2, item.getItemColor());
				stmt.setInt(3, item.getColorExpireTime());
				stmt.setString(4, item.getItemCreator());
				stmt.setInt(5, item.getExpireTime());
				stmt.setInt(6, item.getActivationCount());
				stmt.setInt(7, getItemOwnerId(item, playerId, accountId, legionId));
				stmt.setBoolean(8, item.isEquipped());
				stmt.setInt(9, item.isSoulBound() ? 1 : 0);
				stmt.setLong(10, item.getEquipmentSlot());
				stmt.setInt(11, item.getItemLocation());
				stmt.setInt(12, item.getEnchantLevel());
				stmt.setInt(13, item.getItemSkinTemplate().getTemplateId());
				stmt.setInt(14, item.getFusionedItemId());
				stmt.setInt(15, item.getOptionalSocket());
				stmt.setInt(16, item.getOptionalFusionSocket());
				stmt.setInt(17, item.getChargePoints());
				if (item.getBonusNumber() == null)
					stmt.setNull(18, Types.SMALLINT);
				else
					stmt.setInt(18, item.getBonusNumber());
				stmt.setInt(19, item.getObjectId());
				stmt.addBatch();
			}

			stmt.executeBatch();
			con.commit();
		} catch (Exception e) {
			log.error("Failed to execute update batch", e);
			return false;
		}

		return true;
	}

	private static boolean deleteItems(Connection con, Collection<Item> items)
	{
		if (GenericValidator.isBlankOrNull(items)) {
			return true;
		}

		try (PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)) {
			for (Item item : items) {
				stmt.setInt(1, item.getObjectId());
				stmt.addBatch();
			}

			stmt.executeBatch();
			con.commit();
		} catch (Exception e) {
			log.error("Failed to execute delete batch", e);
			return false;
		}

		return true;
	}
}

