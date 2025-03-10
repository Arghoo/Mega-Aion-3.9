package com.aionemu.gameserver.instance.handlers;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.StageType;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneInstance;

/**
 * @author ATracer
 */
public class GeneralInstanceHandler implements InstanceHandler
{
	protected final long creationTime;
	protected WorldMapInstance instance;
	protected int instanceId;
	protected Integer mapId;

	public GeneralInstanceHandler()
	{
		creationTime = System.currentTimeMillis();
	}

	@Override
	public void onInstanceCreate(WorldMapInstance instance)
	{
		this.instance = instance;
		this.instanceId = instance.getInstanceId();
		this.mapId = instance.getMapId();
	}

	@Override
	public void onInstanceDestroy()
	{
	}

	@Override
	public void onPlayerLogin(Player player)
	{
	}

	@Override
	public void onPlayerLogOut(Player player)
	{
	}

	@Override
	public void onEnterInstance(Player player)
	{
	}

	@Override
	public void onLeaveInstance(Player player)
	{
	}

	@Override
	public void onOpenDoor(int door)
	{
	}

	@Override
	public void onEnterZone(Player player, ZoneInstance zone)
	{
	}

	@Override
	public void onLeaveZone(Player player, ZoneInstance zone)
	{
	}

	@Override
	public void onPlayMovieEnd(Player player, int movieId)
	{
	}

	@Override
	public boolean onReviveEvent(Player player)
	{
		return false;
	}

	protected VisibleObject spawn(int npcId, float x, float y, float z, byte heading)
	{
		SpawnTemplate template = SpawnEngine.addNewSingleTimeSpawn(mapId, npcId, x, y, z, heading);
		return SpawnEngine.spawnObject(template, instanceId);
	}

	protected VisibleObject spawn(int npcId, float x, float y, float z, byte heading, int staticId)
	{
		SpawnTemplate template = SpawnEngine.addNewSingleTimeSpawn(mapId, npcId, x, y, z, heading);
		template.setStaticId(staticId);
		return SpawnEngine.spawnObject(template, instanceId);
	}

	protected Npc getNpc(int npcId)
	{
		return instance.getNpc(npcId);
	}

	protected void sendMsg(int msg, int Obj, boolean isShout, int color)
	{
		sendMsg(msg, Obj, isShout, color, 0);
	}

	protected void sendMsg(int msg, int Obj, boolean isShout, int color, int time)
	{
		NpcShoutsService.getInstance().sendMsg(instance, msg, Obj, isShout, color, time);
	}

	protected void sendMsg(int msg)
	{
		sendMsg(msg, 0, false, 25);
	}

	@Override
	public void onExitInstance(Player player)
	{
	}

	@Override
	public void doReward(Player player)
	{
	}

	@Override
	public boolean onDie(Player player, Creature lastAttacker)
	{
		return false;
	}

	@Override
	public void onStopTraining(Player player)
	{
	}

	@Override
	public void onDie(Npc npc)
	{
	}

	@Override
	public void onChangeStage(StageType type)
	{
	}

	@Override
	public StageType getStage()
	{
		return StageType.DEFAULT;
	}

	@Override
	public void onDropRegistered(Npc npc)
	{
	}

	@Override
	public void onGather(Player player, Gatherable gatherable)
	{
	}

	@Override
	public InstanceReward<?> getInstanceReward()
	{
		return null;
	}

	@Override
	public boolean onPassFlyingRing(Player player, String flyingRing)
	{
		return false;
	}

	@Override
	public void handleUseItemFinish(Player player, Npc npc)
	{
	}

	@Override
	public boolean isEnemy(Player attacker, Player target)
	{
		return false;
	}
}
