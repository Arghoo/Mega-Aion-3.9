package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Collection;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @author ginho1
 * @edit Cheatkiller
 */
public class SM_CHAT_WINDOW extends AionServerPacket
{
	private Player target;
	private boolean isGroup;

	public SM_CHAT_WINDOW(Player target, boolean isGroup)
	{
		this.target = target;
		this.isGroup = isGroup;
	}

	@Override
	protected void writeImpl(AionConnection con)
	{
		if (target == null)
			return;

		World world = World.getInstance();
		String targetName = Util.formatNameWithTags(target);

		if (isGroup) {
			if (target.isInGroup2()) {
				writeC(2); // group
				writeS(targetName);

				PlayerGroup group = target.getPlayerGroup2();
				Player leader = world.findPlayer(Util.convertName(group.getLeader().getName()));
				String leaderName = Util.formatNameWithTags(leader);

				writeD(group.getTeamId());
				writeS(leaderName);

				Collection<Player> members = group.getMembers();
				for (Player groupMember : members)
					writeC(groupMember.getLevel());

				for (int i = group.size(); i < 6; i++)
					writeC(0);

				for (Player groupMember : members)
					writeC(groupMember.getPlayerClass().getClassId());

				for (int i = group.size(); i < 6; i++)
					writeC(0);
			} else if (target.isInAlliance2()) {
				writeC(2); // alliance
				writeS(targetName);

				PlayerAlliance alliance = target.getPlayerAlliance2();
				Player leader = world.findPlayer(Util.convertName(alliance.getLeader().getName()));
				String leaderName = Util.formatNameWithTags(leader);

				writeD(alliance.getTeamId());
				writeS(leaderName);

				Collection<Player> members = alliance.getMembers();
				for (Player groupMember : members)
					writeC(groupMember.getLevel());

				for (int i = alliance.size(); i < 24; i++)
					writeC(0);

				for (Player groupMember : members)
					writeC(groupMember.getPlayerClass().getClassId());

				for (int i = alliance.size(); i < 24; i++)
					writeC(0);
			} else {
				writeC(4); // no group
				writeS(targetName);
				writeD(0); // no group yet
				writeC(target.getPlayerClass().getClassId());
				writeC(target.getLevel());
				writeC(0); // unk
			}
		} else {
			writeC(1);
			writeS(targetName);
			writeS(target.getLegion() != null ? target.getLegion().getLegionName() : "");
			writeC(target.getLevel());
			writeH(target.getPlayerClass().getClassId());
			writeS(target.getCommonData().getNote());
			writeD(1);
		}
	}
}
