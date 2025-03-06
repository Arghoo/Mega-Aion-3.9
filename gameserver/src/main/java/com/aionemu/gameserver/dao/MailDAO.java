package com.aionemu.gameserver.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.LetterType;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.items.storage.StorageType;

import java.sql.Connection;

/**
 * @author kosyachok
 */
public class MailDAO extends IDFactoryAwareDAO
{
	private static final Logger log = LoggerFactory.getLogger(MailDAO.class);

	public static boolean storeLetter(Timestamp time, Letter letter)
	{
		boolean result = false;
		switch (letter.getLetterPersistentState()) {
			case NEW:
				result = saveLetter(time, letter);
				break;
			case UPDATE_REQUIRED:
				result = updateLetter(time, letter);
				break;
			/*
			 * case DELETED: return deleteLetter(letter);
			 */
		}
		letter.setPersistState(PersistentState.UPDATED);

		return result;
	}

	public static Mailbox loadPlayerMailbox(Player player)
	{
		final Mailbox mailbox = new Mailbox(player);
		final int playerId = player.getObjectId();
		DB.select("SELECT * FROM mail WHERE mail_recipient_id = ? ORDER BY recieved_time LIMIT 100", new ParamReadStH()
		{
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException
			{
				stmt.setInt(1, playerId);
			}

			@Override
			public void handleRead(ResultSet rset) throws SQLException
			{
				List<Item> mailboxItems = loadMailboxItems(playerId);
				while (rset.next()) {
					int mailUniqueId = rset.getInt("mail_unique_id");
					int recipientId = rset.getInt("mail_recipient_id");
					String senderName = rset.getString("sender_name");
					String mailTitle = rset.getString("mail_title");
					String mailMessage = rset.getString("mail_message");
					int unread = rset.getInt("unread");
					int attachedItemId = rset.getInt("attached_item_id");
					long attachedKinahCount = rset.getLong("attached_kinah_count");
					LetterType letterType = LetterType.getLetterTypeById(rset.getInt("express"));
					Timestamp recievedTime = rset.getTimestamp("recieved_time");
					Item attachedItem = null;
					if (attachedItemId != 0)
						for (Item item : mailboxItems)
							if (item.getObjectId() == attachedItemId) {
								if (item.getItemTemplate().isArmor() || item.getItemTemplate().isWeapon())
									ItemStoneListDAO.load(Collections.singletonList(item));

								attachedItem = item;
							}

					Letter letter = new Letter(mailUniqueId, recipientId, attachedItem, attachedKinahCount, mailTitle, mailMessage, senderName, recievedTime, unread == 1, letterType);
					letter.setPersistState(PersistentState.UPDATED);
					mailbox.putLetterToMailbox(letter);
				}
			}
		});

		return mailbox;
	}

	public static void storeMailbox(Player player)
	{
		Mailbox mailbox = player.getMailbox();
		if (mailbox == null)
			return;

		Collection<Letter> letters = mailbox.getLetters();
		for (Letter letter : letters) {
			storeLetter(letter.getTimeStamp(), letter);
		}
	}

	public static boolean deleteLetter(final int letterId)
	{
		return DB.insertUpdate("DELETE FROM mail WHERE mail_unique_id=?", stmt -> {
			stmt.setInt(1, letterId);
			stmt.execute();
		});
	}

	public static void updateOfflineMailCounter(final PlayerCommonData recipientCommonData)
	{
		DB.insertUpdate("UPDATE players SET mailbox_letters=? WHERE name=?", stmt -> {
			stmt.setInt(1, recipientCommonData.getMailboxLetters());
			stmt.setString(2, recipientCommonData.getName());
			stmt.execute();
		});
	}

	public static boolean haveUnread(int playerId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT * FROM mail WHERE mail_recipient_id = ? ORDER BY recieved_time LIMIT 100")) {
			stmt.setInt(1, playerId);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				int unread = rset.getInt("unread");
				if (unread == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("Could not read mail for player: " + playerId + " from DB: " + e.getMessage(), e);
		}

		return false;
	}

	private static List<Item> loadMailboxItems(final int playerId)
	{
		final List<Item> mailboxItems = new ArrayList<Item>();

		DB.select("SELECT * FROM inventory WHERE `item_owner` = ? AND `item_location` = 127", new ParamReadStH()
		{
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException
			{
				stmt.setInt(1, playerId);
			}

			@Override
			public void handleRead(ResultSet rset) throws SQLException
			{
				while (rset.next()) {
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
					int slot = rset.getInt("slot");
					int enchant = rset.getInt("enchant");
					int itemSkin = rset.getInt("item_skin");
					int fusionedItem = rset.getInt("fusioned_item");
					int optionalSocket = rset.getInt("optional_socket");
					int optionalFusionSocket = rset.getInt("optional_fusion_socket");
					int charge = rset.getInt("charge");
					Item item = new Item(itemUniqueId, itemId, itemCount, itemColor, colorExpireTime, itemCreator, expireTime, activationCount, isEquiped == 1, isSoulBound == 1, slot, StorageType.MAILBOX.getId(), enchant, itemSkin, fusionedItem,
						optionalSocket, optionalFusionSocket, charge, null);
					item.setPersistentState(PersistentState.UPDATED);
					mailboxItems.add(item);
				}
			}
		});

		return mailboxItems;
	}

	private static boolean saveLetter(final Timestamp time, final Letter letter)
	{
		int attachedItemId = 0;
		if (letter.getAttachedItem() != null)
			attachedItemId = letter.getAttachedItem().getObjectId();

		final int fAttachedItemId = attachedItemId;

		return DB.insertUpdate("INSERT INTO `mail` (`mail_unique_id`, `mail_recipient_id`, `sender_name`, `mail_title`, `mail_message`, `unread`, `attached_item_id`, `attached_kinah_count`, `express`, `recieved_time`) VALUES(?,?,?,?,?,?,?,?,?,?)",
			stmt -> {
				stmt.setInt(1, letter.getObjectId());
				stmt.setInt(2, letter.getRecipientId());
				stmt.setString(3, letter.getSenderName());
				stmt.setString(4, letter.getTitle());
				stmt.setString(5, letter.getMessage());
				stmt.setBoolean(6, letter.isUnread());
				stmt.setInt(7, fAttachedItemId);
				stmt.setLong(8, letter.getAttachedKinah());
				stmt.setInt(9, letter.getLetterType().getId());
				stmt.setTimestamp(10, time);
				stmt.execute();
			});
	}

	private static boolean updateLetter(final Timestamp time, final Letter letter)
	{
		int attachedItemId = 0;
		if (letter.getAttachedItem() != null)
			attachedItemId = letter.getAttachedItem().getObjectId();

		final int fAttachedItemId = attachedItemId;

		return DB.insertUpdate("UPDATE mail SET  unread=?, attached_item_id=?, attached_kinah_count=?, `express`=?, recieved_time=? WHERE mail_unique_id=?", stmt -> {
			stmt.setBoolean(1, letter.isUnread());
			stmt.setInt(2, fAttachedItemId);
			stmt.setLong(3, letter.getAttachedKinah());
			stmt.setInt(4, letter.getLetterType().getId());
			stmt.setTimestamp(5, time);
			stmt.setInt(6, letter.getObjectId());
			stmt.execute();
		});
	}

	public static int[] getUsedIDs()
	{
		return DB.getUsedIDs("mail", "mail_unique_id");
	}
}
