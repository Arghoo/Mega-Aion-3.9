package com.aionemu.gameserver.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.aionemu.commons.database.DatabaseFactory;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.model.broker.BrokerRace;
import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;

public class BrokerDAO
{
	private static final Logger log = LoggerFactory.getLogger(BrokerDAO.class);

	public static List<BrokerItem> loadBroker()
	{
		List<BrokerItem> brokerItems = new ArrayList<>();
		List<Item> items = getBrokerItems();

		DB.select("SELECT * FROM broker", rset -> {
			while (rset.next()) {
				int itemPointer = rset.getInt("item_pointer");
				int itemId = rset.getInt("item_id");
				long itemCount = rset.getLong("item_count");
				String itemCreator = rset.getString("item_creator");
				String seller = rset.getString("seller");
				int sellerId = rset.getInt("seller_id");
				long price = rset.getLong("price");
				BrokerRace itemBrokerRace = BrokerRace.valueOf(rset.getString("broker_race"));
				Timestamp expireTime = rset.getTimestamp("expire_time");
				Timestamp settleTime = rset.getTimestamp("settle_time");
				int sold = rset.getInt("is_sold");
				int settled = rset.getInt("is_settled");

				boolean isSold = sold == 1;
				boolean isSettled = settled == 1;

				Item item = null;
				if (!isSold)
					for (Item brItem : items) {
						if (itemPointer == brItem.getObjectId()) {
							item = brItem;
							break;
						}
					}

				brokerItems.add(new BrokerItem(item, itemId, itemPointer, itemCount, itemCreator, price, seller, sellerId, itemBrokerRace, isSold, isSettled, expireTime, settleTime));
			}
		});

		return brokerItems;
	}

	public static boolean store(BrokerItem item)
	{
		boolean result = false;

		if (item == null) {
			log.warn("Null broker item on save");
			return result;
		}

		switch (item.getPersistentState()) {
			case NEW:
				result = insertBrokerItem(item);
				if (item.getItem() != null)
					InventoryDAO.store(item.getItem(), item.getSellerId());
				break;

			case DELETED:
				result = deleteBrokerItem(item);
				break;

			case UPDATE_REQUIRED:
				result = updateBrokerItem(item);
				break;
		}

		if (result)
			item.setPersistentState(PersistentState.UPDATED);

		return result;
	}

	public static boolean preBuyCheck(int itemForCheck)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement st = con.prepareStatement("SELECT * FROM broker WHERE `item_pointer` = ? and `is_sold` = 0")) {
			log.info("Checking broker item: " + itemForCheck);
			try {
				st.setInt(1, itemForCheck);
				ResultSet rs = st.executeQuery();
				if (rs.next())
					return true;
			} catch (SQLException e) {
				log.error("Can't to prebuy broker check: ", e);
			}
		} catch (SQLException e) {
			log.error("Can't to prebuy broker check: ", e);
		}

		return false;
	}

	private static List<Item> getBrokerItems()
	{
		List<Item> brokerItems = new ArrayList<Item>();

		DB.select("SELECT * FROM inventory WHERE `item_location` = 126", rset -> {
			while (rset.next()) {
				int itemUniqueId = rset.getInt("item_unique_id");
				int itemId = rset.getInt("item_id");
				long itemCount = rset.getLong("item_count");
				int itemColor = rset.getInt("item_color");
				int colorExpireTime = rset.getInt("color_expires");
				String itemCreator = rset.getString("item_creator");
				int expireTime = rset.getInt("expire_time");
				int activationCount = rset.getInt("activation_count");
				long slot = rset.getLong("slot");
				int location = rset.getInt("item_location");
				int enchant = rset.getInt("enchant");
				int itemSkin = rset.getInt("item_skin");
				int fusionedItem = rset.getInt("fusioned_item");
				int optionalSocket = rset.getInt("optional_socket");
				int optionalFusionSocket = rset.getInt("optional_fusion_socket");
				int charge = rset.getInt("charge");
				brokerItems.add(new Item(itemUniqueId, itemId, itemCount, itemColor, colorExpireTime, itemCreator, expireTime, activationCount, false, false, slot, location, enchant, itemSkin, fusionedItem, optionalSocket, optionalFusionSocket, charge, null));
			}
		});

		return brokerItems;
	}

	private static boolean insertBrokerItem(final BrokerItem item)
	{
		return DB.insertUpdate("INSERT INTO `broker` (`item_pointer`, `item_id`, `item_count`, `item_creator`, `seller`, `price`, `broker_race`, `expire_time`, `seller_id`, `is_sold`, `is_settled`) VALUES (?,?,?,?,?,?,?,?,?,?,?)", stmt -> {
			stmt.setInt(1, item.getItemUniqueId());
			stmt.setInt(2, item.getItemId());
			stmt.setLong(3, item.getItemCount());
			stmt.setString(4, item.getItemCreator());
			stmt.setString(5, item.getSeller());
			stmt.setLong(6, item.getPrice());
			stmt.setString(7, String.valueOf(item.getItemBrokerRace()));
			stmt.setTimestamp(8, item.getExpireTime());
			stmt.setInt(9, item.getSellerId());
			stmt.setBoolean(10, item.isSold());
			stmt.setBoolean(11, item.isSettled());
			stmt.execute();
		});
	}

	private static boolean deleteBrokerItem(final BrokerItem item)
	{
		return DB.insertUpdate("DELETE FROM `broker` WHERE `item_pointer` = ? AND `seller_id` = ? AND `expire_time` = ?", stmt -> {
			stmt.setInt(1, item.getItemUniqueId());
			stmt.setInt(2, item.getSellerId());
			stmt.setTimestamp(3, item.getExpireTime());
			stmt.execute();
		});
	}

	private static boolean updateBrokerItem(final BrokerItem item)
	{
		return DB.insertUpdate("UPDATE broker SET `is_sold` = ?, `is_settled` = 1, `settle_time` = ? WHERE `item_pointer` = ? AND `expire_time` = ? AND `seller_id` = ? AND `is_settled` = 0", stmt -> {
			stmt.setBoolean(1, item.isSold());
			stmt.setTimestamp(2, item.getSettleTime());
			stmt.setInt(3, item.getItemUniqueId());
			stmt.setTimestamp(4, item.getExpireTime());
			stmt.setInt(5, item.getSellerId());
			stmt.execute();
		});
	}
}
