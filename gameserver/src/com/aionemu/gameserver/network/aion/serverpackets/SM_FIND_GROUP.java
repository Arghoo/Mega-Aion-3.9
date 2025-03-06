package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Collection;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.FindGroup;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @author cura, MrPoke
 */
public class SM_FIND_GROUP extends AionServerPacket {

	private int action;
	private int lastUpdate;
	private Collection<FindGroup> findGroups;
	private int groupSize;
	private int unk;

	public SM_FIND_GROUP(int action, int lastUpdate, Collection<FindGroup> findGroups) {
		this.lastUpdate = lastUpdate;
		this.action = action;
		this.findGroups = findGroups;
		this.groupSize = findGroups.size();
	}

	public SM_FIND_GROUP(int action, int lastUpdate, int unk) {
		this.action = action;
		this.lastUpdate = lastUpdate;
		this.unk = unk;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		World world = World.getInstance();
		writeC(action);
		switch (action) {
			case 0x00:
			case 0x02:
				writeH(groupSize); // groupSize
				writeH(groupSize); // groupSize
				writeD(lastUpdate); // objId?
				for (FindGroup findGroup : findGroups) {
					Player leader = world.findPlayer(Util.convertName(findGroup.getName()));
					String leaderName = Util.formatNameWithTags(leader);
					writeD(findGroup.getObjectId()); // player object id
					writeD(findGroup.getUnk()); // unk (0 or 65557)
					writeC(findGroup.getGroupType()); // 0:group, 1:alliance
					writeS(findGroup.getMessage()); // text
					writeS(leaderName); // writer name
					writeC(findGroup.getSize()); // members count
					writeC(findGroup.getMinLevel()); // members	// level
					writeC(findGroup.getMaxLevel()); // members	// level
					writeD(findGroup.getLastUpdate()); // objId?
				}
				break;
			case 0x01:
			case 0x03:
				writeD(lastUpdate); // player object id
				writeD(unk); // unk (0 or 65557)
				break;
			case 0x04:
			case 0x06:
				writeH(groupSize); // groupSize
				writeH(groupSize); // groupSize
				writeD(lastUpdate); // objId?
				for (FindGroup findGroup : findGroups) {
					Player leader = world.findPlayer(Util.convertName(findGroup.getName()));
					String leaderName = Util.formatNameWithTags(leader);
					writeD(findGroup.getObjectId()); // player object id
					writeC(findGroup.getGroupType()); // 0:group, 1:alliance
					writeS(findGroup.getMessage()); // text
					writeS(leaderName); // writer name
					writeC(findGroup.getClassId()); // player class id
					writeC(findGroup.getMinLevel()); // player level
					writeD(findGroup.getLastUpdate()); // objId?
				}
				break;
			case 0x05:
				writeD(lastUpdate); // player object id
				break;
		}
	}
}
