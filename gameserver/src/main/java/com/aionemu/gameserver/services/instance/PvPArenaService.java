package com.aionemu.gameserver.services.instance;

import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.configs.main.ArenaScheduleConfig;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

import org.joda.time.DateTime;

/**
 * @author xTz
 */
public class PvPArenaService
{
	public static boolean isPvPArenaAvailable(Player player, AutoGroupType agt)
	{
		if (AutoGroupConfig.START_TIME_ENABLE && !checkTime(agt)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401306, agt.getInstanceMapId()));
			return false;
		}
		if (!checkItem(player, agt)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400219, agt.getInstanceMapId()));
			return false;
		}
		// todo check cool down
		return true;
	}

	public static boolean checkItem(Player player, AutoGroupType agt)
	{
		Storage inventory = player.getInventory();
		if (agt.isPvPFFAArena() || agt.isPvPSoloArena()) {
			return inventory.getItemCountByItemId(186000135) > 0;
		} else if (agt.isHarmonyArena()) {
			return inventory.getItemCountByItemId(186000135) > 0;
		} else if (agt.isGloryArena()) {
			return inventory.getItemCountByItemId(186000185) >= 3;
		}
		return true;
	}

	private static boolean checkTime(AutoGroupType agt)
	{
		if (agt.isPvPFFAArena() || agt.isPvPSoloArena()) {
			return isPvPArenaAvailable();
		} else if (agt.isHarmonyArena()) {
			return isHarmonyArenaAvailable();
		} else if (agt.isGloryArena()) {
			return isGloryArenaAvailable();
		}
		return true;
	}

	private static boolean isPvPArenaAvailable()
	{
		DateTime now = DateTime.now();
		int hour = now.getHourOfDay();
		int startTime1 = ArenaScheduleConfig.DISCIPLINE_START1;
		int endTime1 = ArenaScheduleConfig.DISCIPLINE_END1;
		int startTime2 = ArenaScheduleConfig.DISCIPLINE_START2;
		int endTime2 = ArenaScheduleConfig.DISCIPLINE_END2;

		boolean window1 = startTime1 < endTime1 ? (hour >= startTime1 && hour <= endTime1) : (hour >= startTime1 || hour <= endTime1);
		boolean window2 = startTime2 < endTime2 ? (hour >= startTime2 && hour <= endTime2) : (hour >= startTime2 || hour <= endTime2);

		return (window1 || window2);
	}

	private static boolean isHarmonyArenaAvailable()
	{
		DateTime now = DateTime.now();
		int hour = now.getHourOfDay();
		int startTime1 = ArenaScheduleConfig.HARMONY_START1;
		int endTime1 = ArenaScheduleConfig.HARMONY_END1;
		int startTime2 = ArenaScheduleConfig.HARMONY_START2;
		int endTime2 = ArenaScheduleConfig.HARMONY_END2;

		boolean window1 = startTime1 < endTime1 ? (hour >= startTime1 && hour <= endTime1) : (hour >= startTime1 || hour <= endTime1);
		boolean window2 = startTime2 < endTime2 ? (hour >= startTime2 && hour <= endTime2) : (hour >= startTime2 || hour <= endTime2);

		return (window1 || window2);
	}

	private static boolean isGloryArenaAvailable()
	{
		DateTime now = DateTime.now();
		int hour = now.getHourOfDay();
		int startTime1 = ArenaScheduleConfig.GLORY_START1;
		int endTime1 = ArenaScheduleConfig.GLORY_END1;
		int startTime2 = ArenaScheduleConfig.GLORY_START2;
		int endTime2 = ArenaScheduleConfig.GLORY_END2;

		boolean window1 = startTime1 < endTime1 ? (hour >= startTime1 && hour <= endTime1) : (hour >= startTime1 || hour <= endTime1);
		boolean window2 = startTime2 < endTime2 ? (hour >= startTime2 && hour <= endTime2) : (hour >= startTime2 || hour <= endTime2);

		return (window1 || window2);
	}

	private static int getSmallerNumber(int num1, int num2)
	{
		if (num1 <= num2) {
			return num1;
		}
		return num2;
	}

	private static int getBiggerNumber(int num1, int num2)
	{
		if (num1 >= num2) {
			return num1;
		}
		return num2;
	}
}
