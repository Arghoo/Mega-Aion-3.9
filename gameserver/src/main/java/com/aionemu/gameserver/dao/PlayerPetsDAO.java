package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.aionemu.commons.database.DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PetCommonData;
import com.aionemu.gameserver.model.templates.pet.PetDopingBag;
import com.aionemu.gameserver.services.toypet.PetHungryLevel;

/**
 * @author Xitanium, Kamui, Rolandas
 */
public class PlayerPetsDAO
{
	private static final Logger log = LoggerFactory.getLogger(PlayerPetsDAO.class);

	public static void insertPlayerPet(PetCommonData petCommonData)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("INSERT INTO player_pets(player_id, pet_id, decoration, name, despawn_time, expire_time) VALUES(?, ?, ?, ?, ?, ?)")) {
			stmt.setInt(1, petCommonData.getMasterObjectId());
			stmt.setInt(2, petCommonData.getPetId());
			stmt.setInt(3, petCommonData.getDecoration());
			stmt.setString(4, petCommonData.getName());
			stmt.setTimestamp(5, petCommonData.getDespawnTime());
			stmt.setInt(6, petCommonData.getExpireTime());
			stmt.execute();
		} catch (Exception e) {
			log.error("Error inserting new pet #" + petCommonData.getPetId() + "[" + petCommonData.getName() + "]", e);
		}
	}

	public static void removePlayerPet(Player player, int petId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("DELETE FROM player_pets WHERE player_id = ? AND pet_id = ?")) {
			stmt.setInt(1, player.getObjectId());
			stmt.setInt(2, petId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error removing pet #" + petId, e);
		}
	}

	public static void updatePetName(PetCommonData petCommonData)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE player_pets SET name = ? WHERE player_id = ? AND pet_id = ?")) {
			stmt.setString(1, petCommonData.getName());
			stmt.setInt(2, petCommonData.getMasterObjectId());
			stmt.setInt(3, petCommonData.getPetId());
			stmt.execute();
		} catch (Exception e) {
			log.error("Error update pet #" + petCommonData.getPetId(), e);
		}
	}

	public static List<PetCommonData> getPlayerPets(Player player)
	{
		List<PetCommonData> pets = new ArrayList<>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT * FROM player_pets WHERE player_id = ?")) {
			stmt.setInt(1, player.getObjectId());
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					PetCommonData petCommonData = new PetCommonData(rs.getInt("pet_id"), player.getObjectId(), rs.getInt("expire_time"));
					petCommonData.setName(rs.getString("name"));
					petCommonData.setDecoration(rs.getInt("decoration"));
					if (petCommonData.getFeedProgress() != null) {
						petCommonData.getFeedProgress().setHungryLevel(PetHungryLevel.fromId(rs.getInt("hungry_level")));
						petCommonData.getFeedProgress().setData(rs.getInt("feed_progress"));
						petCommonData.setCurentTime(rs.getLong("reuse_time"));
					}
					if (petCommonData.getDopingBag() != null) {
						String dopings = rs.getString("dopings");
						if (dopings != null) {
							String[] ids = dopings.split(",");
							for (int i = 0; i < ids.length; i++)
								petCommonData.getDopingBag().setItem(Integer.parseInt(ids[i]), i);
						}
					}
					petCommonData.setBirthday(rs.getTimestamp("birthday"));
					if (petCommonData.getTime() != 0) {
						petCommonData.setIsFeedingTime(false);
						petCommonData.setReFoodTime(petCommonData.getTime());
					}
					petCommonData.setStartMoodTime(rs.getLong("mood_started"));
					petCommonData.setShuggleCounter(rs.getInt("counter"));
					petCommonData.setMoodCdStarted(rs.getLong("mood_cd_started"));
					petCommonData.setGiftCdStarted(rs.getLong("gift_cd_started"));
					Timestamp ts = null;
					try {
						ts = rs.getTimestamp("despawn_time");
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					if (ts == null)
						ts = new Timestamp(System.currentTimeMillis());
					petCommonData.setDespawnTime(ts);
					pets.add(petCommonData);
				}
			}
		} catch (Exception e) {
			log.error("Error getting pets for " + player.getObjectId(), e);
		}
		return pets;
	}

	public static void setTime(Player player, int petId, long time)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE player_pets SET reuse_time = ? WHERE player_id = ? AND pet_id = ?")) {
			stmt.setLong(1, time);
			stmt.setInt(2, player.getObjectId());
			stmt.setInt(3, petId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error update pet #" + petId, e);
		}
	}

	public static void saveFeedStatus(Player player, int petId, int hungryLevel, int feedProgress, long reuseTime)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE player_pets SET hungry_level = ?, feed_progress = ?, reuse_time = ? WHERE player_id = ? AND pet_id = ?")) {
			stmt.setInt(1, hungryLevel);
			stmt.setInt(2, feedProgress);
			stmt.setLong(3, reuseTime);
			stmt.setInt(4, player.getObjectId());
			stmt.setInt(5, petId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error update pet #" + petId, e);
		}
	}

	public static boolean savePetMoodData(PetCommonData petCommonData)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE player_pets SET mood_started = ?, counter = ?, mood_cd_started = ?, gift_cd_started = ?, despawn_time = ? WHERE player_id = ? AND pet_id = ?")) {
			stmt.setLong(1, petCommonData.getMoodStartTime());
			stmt.setInt(2, petCommonData.getShuggleCounter());
			stmt.setLong(3, petCommonData.getMoodCdStarted());
			stmt.setLong(4, petCommonData.getGiftCdStarted());
			stmt.setTimestamp(5, petCommonData.getDespawnTime());
			stmt.setInt(6, petCommonData.getMasterObjectId());
			stmt.setInt(7, petCommonData.getPetId());
			stmt.execute();
		} catch (Exception e) {
			log.error("Error updating mood for pet #" + petCommonData.getPetId(), e);
			return false;
		}

		return true;
	}

	public static void saveDopingBag(Player player, int petId, PetDopingBag bag)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE player_pets SET dopings = ? WHERE player_id = ? AND pet_id = ?")) {
			String itemIds = bag.getFoodItem() + "," + bag.getDrinkItem();
			for (int itemId : bag.getScrollsUsed())
				itemIds += "," + itemId;
			stmt.setString(1, itemIds);
			stmt.setInt(2, player.getObjectId());
			stmt.setInt(3, petId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Error update doping for pet #" + petId, e);
		}
	}
}
