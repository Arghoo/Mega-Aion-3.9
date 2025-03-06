package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.house.HouseStatus;
import com.aionemu.gameserver.model.templates.housing.Building;
import com.aionemu.gameserver.model.templates.housing.BuildingType;
import com.aionemu.gameserver.model.templates.housing.HouseAddress;
import com.aionemu.gameserver.model.templates.housing.HousingLand;

/**
 * @author Rolandas
 */
public class HousesDAO extends IDFactoryAwareDAO
{
	private static final Logger log = LoggerFactory.getLogger(IDFactoryAwareDAO.class);

	private static final String SELECT_HOUSES_QUERY = "SELECT * FROM houses WHERE address <> 2001 AND address <> 3001";
	private static final String SELECT_STUDIOS_QUERY = "SELECT * FROM houses WHERE address = 2001 OR address = 3001";
	private static final String ADD_HOUSE_QUERY = "INSERT INTO houses (id, address, building_id, player_id, acquire_time, settings, status, fee_paid, next_pay, sell_started) " + " VALUES (?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_HOUSE_QUERY = "UPDATE houses SET building_id=?, player_id=?, acquire_time=?, settings=?, status=?, fee_paid=?, next_pay=?, sell_started=? WHERE id=?";
	private static final String DELETE_HOUSE_QUERY = "DELETE FROM houses WHERE player_id=?";

	public static void storeHouse(House house)
	{
		if (house.getPersistentState() == PersistentState.NEW)
			insertNewHouse(house);
		else
			updateHouse(house);
	}

	public static Map<Integer, House> loadHouses(Collection<HousingLand> lands, boolean studios)
	{
		Map<Integer, House> houses = new HashMap<>();
		Map<Integer, HouseAddress> addressesById = new HashMap<>();
		Map<Integer, List<Building>> buildingsForAddress = new HashMap<>();
		for (HousingLand land : lands) {
			for (HouseAddress address : land.getAddresses()) {
				addressesById.put(address.getId(), address);
				buildingsForAddress.put(address.getId(), land.getBuildings());
			}
		}

		HashMap<Integer, Integer> addressHouseIds = new HashMap<>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(studios ? SELECT_STUDIOS_QUERY : SELECT_HOUSES_QUERY)) {
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int houseId = rset.getInt("id");
					int buildingId = rset.getInt("building_id");
					HouseAddress address = addressesById.get(rset.getInt("address"));
					Building building = null;
					for (Building b : buildingsForAddress.get(address.getId())) {
						if (b.getId() == buildingId) {
							building = b;
							break;
						}
					}

					House house = null;
					if (building == null) {
						log.warn("Missing building type for address " + address.getId());
						continue;
					} else if (addressHouseIds.containsKey(address.getId())) {
						log.warn("Duplicate house address " + address.getId() + "!");
						continue;
					} else {
						house = new House(houseId, building, address, 0);
						if (building.getType() == BuildingType.PERSONAL_FIELD)
							addressHouseIds.put(address.getId(), houseId);
					}

					house.setOwnerId(rset.getInt("player_id"));
					house.setAcquiredTime(rset.getTimestamp("acquire_time"));
					house.setSettingFlags(rset.getInt("settings"));
					house.setStatus(HouseStatus.valueOf(rset.getString("status")));
					house.setFeePaid(rset.getInt("fee_paid") != 0);
					house.setNextPay(rset.getTimestamp("next_pay"));
					house.setSellStarted(rset.getTimestamp("sell_started"));

					int id = studios ? house.getOwnerId() : address.getId();
					houses.put(id, house);
				}
			}
		} catch (Exception e) {
			log.error("Could not load houses", e);
		}

		return houses;
	}

	public static void deleteHouse(int playerId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(DELETE_HOUSE_QUERY)) {
			stmt.setInt(1, playerId);
			stmt.execute();
		} catch (SQLException e) {
			log.error("Delete House failed", e);
		}
	}

	private static void insertNewHouse(House house)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(ADD_HOUSE_QUERY)) {
			stmt.setInt(1, house.getObjectId());
			stmt.setInt(2, house.getAddress().getId());
			stmt.setInt(3, house.getBuilding().getId());
			stmt.setInt(4, house.getOwnerId());
			if (house.getAcquiredTime() == null)
				stmt.setNull(5, Types.TIMESTAMP);
			else
				stmt.setTimestamp(5, house.getAcquiredTime());

			stmt.setInt(6, house.getSettingFlags());
			stmt.setString(7, house.getStatus().toString());
			stmt.setInt(8, house.isFeePaid() ? 1 : 0);

			if (house.getNextPay() == null)
				stmt.setNull(9, Types.TIMESTAMP);
			else
				stmt.setTimestamp(9, house.getNextPay());

			if (house.getSellStarted() == null)
				stmt.setNull(10, Types.TIMESTAMP);
			else
				stmt.setTimestamp(10, house.getSellStarted());

			stmt.execute();
			house.setPersistentState(PersistentState.UPDATED);
		} catch (Exception e) {
			log.error("Could not insert house " + house.getObjectId(), e);
		}
	}

	private static void updateHouse(House house)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(UPDATE_HOUSE_QUERY)) {
			stmt.setInt(1, house.getBuilding().getId());
			stmt.setInt(2, house.getOwnerId());
			if (house.getAcquiredTime() == null)
				stmt.setNull(3, Types.TIMESTAMP);
			else
				stmt.setTimestamp(3, house.getAcquiredTime());

			stmt.setInt(4, house.getSettingFlags());
			stmt.setString(5, house.getStatus().toString());
			stmt.setInt(6, house.isFeePaid() ? 1 : 0);

			if (house.getNextPay() == null)
				stmt.setNull(7, Types.TIMESTAMP);
			else
				stmt.setTimestamp(7, house.getNextPay());

			if (house.getSellStarted() == null)
				stmt.setNull(8, Types.TIMESTAMP);
			else
				stmt.setTimestamp(8, house.getSellStarted());
			stmt.setInt(9, house.getObjectId());
			stmt.execute();
		} catch (Exception e) {
			log.error("Could not store house " + house.getObjectId(), e);
		}
	}

	public static int[] getUsedIDs()
	{
		return DB.getUsedIDs("houses", "id");
	}
}
