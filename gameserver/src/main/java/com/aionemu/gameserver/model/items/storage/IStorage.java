package com.aionemu.gameserver.model.items.storage;

import java.util.List;
import java.util.Queue;

import javolution.util.FastList;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemPacketService.ItemDeleteType;
import com.aionemu.gameserver.services.item.ItemPacketService.ItemUpdateType;

/**
 * Public interface for Storage, later will rename probably
 *
 * @author ATracer
 */
public interface IStorage
{
	/**
	 * @param player
	 */
	void setOwner(Player player);

	/**
	 * @return current kinah count
	 */
	long getKinah();

	/**
	 * @return kinah item or null if storage never had kinah
	 */
	Item getKinahItem();

	/**
	 *
	 */
	StorageType getStorageType();

	/**
	 * @param amount
	 */
	void increaseKinah(long amount);

	/**
	 * @param amount
	 * @param updateType
	 */
	void increaseKinah(long amount, ItemUpdateType updateType);

	/**
	 * @param amount
	 */
	boolean tryDecreaseKinah(long amount);

	/**
	 * @param amount
	 */
	void decreaseKinah(long amount);

	/**
	 * @param amount
	 * @param updateType
	 */
	void decreaseKinah(long amount, ItemUpdateType updateType);

	/**
	 * @param item
	 * @param count
	 */
	long increaseItemCount(Item item, long count);

	/**
	 * @param item
	 * @param count
	 * @param updateType
	 */
	long increaseItemCount(Item item, long count, ItemUpdateType updateType);

	/**
	 * @param item
	 * @param count
	 */
	long decreaseItemCount(Item item, long count);

	/**
	 * @param item
	 * @param count
	 * @param updateType
	 */
	long decreaseItemCount(Item item, long count, ItemUpdateType updateType);

	/**
	 * Add operation should be used for new items incoming into storage from outside
	 */
	Item add(Item item);

	/**
	 * Put operation is used in some operations like unequip
	 */
	Item put(Item item);

	/**
	 * @param item
	 */
	Item remove(Item item);

	/**
	 * @param item
	 */
	Item delete(Item item);

	/**
	 * @param item
	 * @param deleteType
	 */
	Item delete(Item item, ItemDeleteType deleteType);

	/**
	 * @param itemId
	 * @param count
	 */
	boolean decreaseByItemId(int itemId, long count);

	/**
	 * @param itemObjId
	 * @param count
	 */
	boolean decreaseByObjectId(int itemObjId, long count);

	/**
	 * @param itemObjId
	 * @param count
	 * @param updateType
	 */
	boolean decreaseByObjectId(int itemObjId, long count, ItemUpdateType updateType);

	/**
	 * @param itemId
	 */
	Item getFirstItemByItemId(int itemId);

	/**
	 *
	 */
	FastList<Item> getItemsWithKinah();

	/**
	 *
	 */
	List<Item> getItems();

	/**
	 * @param itemId
	 */
	List<Item> getItemsByItemId(int itemId);

	/**
	 * @param itemObjId
	 */
	Item getItemByObjId(int itemObjId);

	/**
	 * @param itemId
	 */
	long getItemCountByItemId(int itemId);

	/**
	 *
	 */
	boolean isFull();

	/**
	 *
	 */
	int getFreeSlots();

	/**
	 *
	 */
	int getLimit();

	/**
	 *
	 */
	int size();

	/**
	 *
	 */
	PersistentState getPersistentState();

	/**
	 * @param persistentState
	 */
	void setPersistentState(PersistentState persistentState);

	/**
	 *
	 */
	Queue<Item> getDeletedItems();

	/**
	 * @param item
	 */
	void onLoadHandler(Item item);
}
