package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.HouseBidEntry;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_BIDS;
import com.aionemu.gameserver.services.HousingBidService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.collections.ListSplitter;

/**
 * @author Rolandas
 * @author Antraxx
 */
public class CM_GET_HOUSE_BIDS extends AionClientPacket
{
	public CM_GET_HOUSE_BIDS(int opcode, State state, State... restStates)
	{
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl()
	{
		// No data
	}

	@Override
	protected void runImpl()
	{
		Player player = getConnection().getActivePlayer();
		HousingBidService.getInstance().sendBidList(player);
	}
}
