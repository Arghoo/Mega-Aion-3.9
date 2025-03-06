/*
 * This file is part of aion-unique <aionu-unique.org>.
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

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.utils.GenericValidator;
import com.aionemu.gameserver.configs.main.EnchantsConfig;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemStone;
import com.aionemu.gameserver.model.items.ItemStone.ItemStoneType;
import com.aionemu.gameserver.model.items.ManaStone;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ATracer modified by Wakizashi
 */
public class ItemStoneListDAO
{
	private static final Logger log = LoggerFactory.getLogger(ItemStoneListDAO.class);

	public static final String INSERT_QUERY = "INSERT INTO `item_stones` (`item_unique_id`, `item_id`, `slot`, `category`) VALUES (?,?,?, ?)";
	public static final String UPDATE_QUERY = "UPDATE `item_stones` SET `item_id`=?, `slot`=? where `item_unique_id`=? AND `category`=?";
	public static final String DELETE_QUERY = "DELETE FROM `item_stones` WHERE `item_unique_id`=? AND slot=? AND category=?";
	public static final String SELECT_QUERY = "SELECT `item_id`, `slot`, `category` FROM `item_stones` WHERE `item_unique_id`=?";

	private static final Predicate<ItemStone> itemStoneAddPredicate = itemStone -> itemStone != null && PersistentState.NEW == itemStone.getPersistentState();
	private static final Predicate<ItemStone> itemStoneDeletedPredicate = itemStone -> itemStone != null && PersistentState.DELETED == itemStone.getPersistentState();
	private static final Predicate<ItemStone> itemStoneUpdatePredicate = itemStone -> itemStone != null && PersistentState.UPDATE_REQUIRED == itemStone.getPersistentState();

	/**
	 * Loads stones of item
	 *
	 * @param items list of items to load stones
	 */
	public static void load(final Collection<Item> items)
	{
		List<Item> validItems = items.stream().filter(i -> i.getItemTemplate().isArmor() || i.getItemTemplate().isWeapon()).collect(Collectors.toList());
		if (validItems.isEmpty())
			return;

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			for (Item item : validItems) {
				stmt.setInt(1, item.getObjectId());
				try (ResultSet rset = stmt.executeQuery()) {
					while (rset.next()) {
						int itemId = rset.getInt("item_id");
						int slot = rset.getInt("slot");
						int stoneType = rset.getInt("category");
						if (stoneType == 0) {
							if (item.getSockets(false) <= item.getItemStonesSize()) {
								log.warn("Deleting manastone " + itemId + " due to slot overload from " + item);
								if (EnchantsConfig.CLEAN_STONE) {
									deleteItemStone(con, item.getObjectId(), slot, stoneType);
								}
								continue;
							}
							item.getItemStones().add(new ManaStone(item.getObjectId(), itemId, slot, PersistentState.UPDATED));
						} else if (stoneType == 1) {
							item.setGodStone(new GodStone(item.getObjectId(), itemId, PersistentState.UPDATED));
						} else {
							if (item.getSockets(true) <= item.getFusionStonesSize()) {
								log.warn("Manastone slots overloaded. ObjectId: " + item.getObjectId());
								if (EnchantsConfig.CLEAN_STONE) {
									deleteItemStone(con, item.getObjectId(), slot, stoneType);
								}
								continue;
							}
							item.getFusionStones().add(new ManaStone(item.getObjectId(), itemId, slot, PersistentState.UPDATED));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Could not restore ItemStoneList data from DB: " + e.getMessage(), e);
		}
	}

	public static void storeManaStones(Set<ManaStone> manaStones)
	{
		store(manaStones, ItemStoneType.MANASTONE);
	}

	public static void storeFusionStone(Set<ManaStone> manaStones)
	{
		store(manaStones, ItemStoneType.FUSIONSTONE);
	}

	/**
	 * Saves stones of player
	 *
	 * @param player whos stones we need to save
	 */
	public static void save(Player player)
	{
		save(player.getAllItems());
	}

	private static void store(Set<? extends ItemStone> stones, ItemStoneType ist)
	{
		if (GenericValidator.isBlankOrNull(stones)) {
			return;
		}

		Set<? extends ItemStone> stonesToAdd = Sets.filter(stones, itemStoneAddPredicate);
		Set<? extends ItemStone> stonesToDelete = Sets.filter(stones, itemStoneDeletedPredicate);
		Set<? extends ItemStone> stonesToUpdate = Sets.filter(stones, itemStoneUpdatePredicate);

		try (Connection con = DatabaseFactory.getConnection()) {
			con.setAutoCommit(false);

			deleteItemStones(con, stonesToDelete, ist);
			addItemStones(con, stonesToAdd, ist);
			updateItemStones(con, stonesToUpdate, ist);
		} catch (SQLException e) {
			log.error("Can't save stones", e);
		}

		for (ItemStone is : stones) {
			is.setPersistentState(PersistentState.UPDATED);
		}
	}

	public static void save(List<Item> items)
	{
		if (GenericValidator.isBlankOrNull(items)) {
			return;
		}

		Set<ManaStone> manaStones = Sets.newHashSet();
		Set<ManaStone> fusionStones = Sets.newHashSet();
		Set<GodStone> godStones = Sets.newHashSet();

		for (Item item : items) {
			if (item.hasManaStones()) {
				manaStones.addAll(item.getItemStones());
			}

			if (item.hasFusionStones()) {
				fusionStones.addAll(item.getFusionStones());
			}

			GodStone godStone = item.getGodStone();
			if (godStone != null) {
				godStones.add(godStone);
			}
		}

		store(manaStones, ItemStoneType.MANASTONE);
		store(fusionStones, ItemStoneType.FUSIONSTONE);
		store(godStones, ItemStoneType.GODSTONE);
	}

	private static void addItemStones(Connection con, Collection<? extends ItemStone> itemStones, ItemStoneType ist)
	{
		if (GenericValidator.isBlankOrNull(itemStones)) {
			return;
		}

		try (PreparedStatement st = con.prepareStatement(INSERT_QUERY)) {
			for (ItemStone is : itemStones) {
				st.setInt(1, is.getItemObjId());
				st.setInt(2, is.getItemId());
				st.setInt(3, is.getSlot());
				st.setInt(4, ist.ordinal());
				st.addBatch();
			}

			st.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Error occured while saving item stones", e);
		}
	}

	private static void updateItemStones(Connection con, Collection<? extends ItemStone> itemStones, ItemStoneType ist)
	{
		if (GenericValidator.isBlankOrNull(itemStones)) {
			return;
		}

		try (PreparedStatement st = con.prepareStatement(UPDATE_QUERY)) {
			for (ItemStone is : itemStones) {
				st.setInt(1, is.getItemId());
				st.setInt(2, is.getSlot());
				st.setInt(3, is.getItemObjId());
				st.setInt(4, ist.ordinal());
				st.addBatch();
			}

			st.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Error occured while saving item stones", e);
		}
	}

	private static void deleteItemStones(Connection con, Collection<? extends ItemStone> itemStones, ItemStoneType ist)
	{
		if (GenericValidator.isBlankOrNull(itemStones)) {
			return;
		}

		try (PreparedStatement st = con.prepareStatement(DELETE_QUERY)) {
			// TODO: Shouldn't we update stone slot?
			for (ItemStone is : itemStones) {
				st.setInt(1, is.getItemObjId());
				st.setInt(2, is.getSlot());
				st.setInt(3, ist.ordinal());
				st.execute();
				st.addBatch();
			}

			st.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Error occured while saving item stones", e);
		}
	}

	private static void deleteItemStone(Connection con, int uid, int slot, int category)
	{
		try (PreparedStatement st = con.prepareStatement(DELETE_QUERY)) {
			st.setInt(1, uid);
			st.setInt(2, slot);
			st.setInt(3, category);
			st.execute();
		} catch (SQLException e) {
			log.error("Error occured while saving item stones", e);
		}
	}
}
