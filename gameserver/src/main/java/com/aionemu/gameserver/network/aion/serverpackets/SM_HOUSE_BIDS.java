package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.house.HouseBidEntry;
import com.aionemu.gameserver.model.house.HouseStatus;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.HousingBidService;

/**
 * @author Rolandas
 */
public class SM_HOUSE_BIDS extends AionServerPacket
{
	private Player player;
	private List<HouseBidEntry> bidEntryList;
	private HouseBidEntry playerBid;
	private List<House> playerHouses;
	private boolean isFirstPacket;
	private boolean isLastPacket;

	public SM_HOUSE_BIDS(Player player, List<HouseBidEntry> bidEntryList, HouseBidEntry playerBid, List<House> playerHouses, boolean isFirstPacket, boolean isLastPacket)
	{
		this.player = player;
		this.bidEntryList = bidEntryList;
		this.playerBid = playerBid;
		this.playerHouses = playerHouses;
		this.isFirstPacket = isFirstPacket;
		this.isLastPacket = isLastPacket;
	}

	@Override
	protected void writeImpl(AionConnection con)
	{
		int secondsTillAuction = HousingBidService.getInstance().getSecondsTillAuction();

		writeC(isFirstPacket ? 1 : 0);
		writeC(isLastPacket ? 1 : 0);
		writeD(playerBid == null ? 0 : playerBid.getEntryIndex());
		writeQ(playerBid == null ? 0 : playerBid.getBidPrice());

		House sellHouse = null;
		for (House house : playerHouses) {
			if (house.getStatus() == HouseStatus.SELL_WAIT) {
				sellHouse = house;
				break;
			}
		}

		HouseBidEntry sellData = null;
		if (sellHouse != null) {
			sellData = HousingBidService.getInstance().getHouseBid(sellHouse.getObjectId());
			writeD(sellData.getEntryIndex());
			writeQ(sellData.getBidPrice());
		} else {
			writeD(0);
			writeQ(0);
		}

		writeH(bidEntryList.size());
		for (HouseBidEntry entry : bidEntryList) {
			writeD(entry.getEntryIndex());
			writeD(entry.getLandId());
			writeD(entry.getAddress());
			writeD(entry.getBuildingId());
			if (sellData != null && entry.getEntryIndex() == sellData.getEntryIndex()) {
				writeD(0);
			} else if (HousingBidService.canBidHouse(player, entry.getMapId(), entry.getLandId())) {
				writeD(entry.getHouseType().getId());
			} else {
				writeD(0);
			}
			writeQ(entry.getBidPrice());
			writeQ(entry.getUnk2());
			writeD(entry.getBidCount());
			writeD(secondsTillAuction);
		}
	}
}
