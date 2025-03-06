package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.autogroup.AGPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.playerreward.HarmonyGroupReward;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INSTANCE_SCORE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javolution.util.FastList;

/**
 * @author xTz
 */
public class HarmonyArenaReward extends PvPArenaReward
{
	private FastList<HarmonyGroupReward> groups = new FastList<HarmonyGroupReward>();

	public HarmonyArenaReward(Integer mapId, int instanceId, WorldMapInstance instance)
	{
		super(mapId, instanceId, instance);
	}

	public HarmonyGroupReward getHarmonyGroupReward(Integer object)
	{
		for (InstancePlayerReward reward : groups) {
			HarmonyGroupReward harmonyReward = (HarmonyGroupReward) reward;
			if (harmonyReward.containPlayer(object)) {
				return harmonyReward;
			}
		}
		return null;
	}

	public FastList<HarmonyGroupReward> getHarmonyGroupInside()
	{
		FastList<HarmonyGroupReward> harmonyGroups = new FastList<HarmonyGroupReward>();
		for (HarmonyGroupReward group : groups) {
			for (AGPlayer agp : group.getAGPlayers()) {
				if (agp.isInInstance()) {
					harmonyGroups.add(group);
					break;
				}
			}
		}
		return harmonyGroups;
	}

	public FastList<Player> getPlayersInside(HarmonyGroupReward group)
	{
		FastList<Player> players = new FastList<Player>();
		for (Player playerInside : instance.getPlayersInside()) {
			if (group.containPlayer(playerInside.getObjectId())) {
				players.add(playerInside);
			}
		}
		return players;
	}

	public void addHarmonyGroup(HarmonyGroupReward reward)
	{
		groups.add(reward);
	}

	public FastList<HarmonyGroupReward> getGroups()
	{
		return groups;
	}

	public void sendPacket(final int type, final Integer object)
	{
		instance.doOnAllPlayers(player -> PacketSendUtility.sendPacket(player, new SM_INSTANCE_SCORE(type, getTime(), getInstanceReward(), object)));
	}

	@Override
	public int getRank(int points)
	{
		List<HarmonyGroupReward> sortedByPoints = groups.stream().sorted((r1, r2) -> Integer.compare(r2.getPoints(), r1.getPoints())).collect(Collectors.toList());
		int rank = -1;
		for (HarmonyGroupReward reward : sortedByPoints) {
			if (reward.getPoints() >= points) {
				rank++;
			}
		}
		return rank;
	}

	@Override
	public int getTotalPoints()
	{
		return groups.stream().mapToInt(HarmonyGroupReward::getPoints).sum();
	}

	@Override
	public void clear()
	{
		groups.clear();
		super.clear();
	}
}
