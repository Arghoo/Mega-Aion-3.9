/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Rhys2002
 */
public class _2056ThawingKurngalfberg extends QuestHandler
{

	private final static int questId = 2056;
	private final static int[] npc_ids = { 204753, 790016, 730036, 279000 };

	public _2056ThawingKurngalfberg()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.registerQuestItem(182204313, questId);
		qe.registerQuestItem(182204314, questId);
		qe.registerQuestItem(182204315, questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		for (int npc_id : npc_ids)
			qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env)
	{
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env)
	{
		return defaultOnLvlUpEvent(env, 2500, true);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;

		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();

		if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204753) {
				if (env.getDialog() == QuestDialog.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else {
					int[] questItems = { 182204313, 182204314, 182204315 };
					return sendQuestEndDialog(env, questItems);
				}
			}
		} else if (qs.getStatus() != QuestStatus.START) {
			return false;
		}
		if (targetId == 204753) { // Kistenian
			switch (env.getDialog()) {
				case START_DIALOG:
					if (var == 0)
						return sendQuestDialog(env, 1011);
					else if (var == 1)
						return sendQuestDialog(env, 2375);
				case SELECT_ACTION_1012:
					playQuestMovie(env, 242);
					break;
				case SELECT_ACTION_2376:
					if (QuestService.collectItemCheck(env, false))
						return sendQuestDialog(env, 2376);
					else
						return sendQuestDialog(env, 2461);
				case STEP_TO_1:
					if (var == 0) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						TeleportService2.teleportTo(player, 220020000, 942.39685f, 2814.404f, 231.25f, (byte) 31, TeleportAnimation.BEAM_ANIMATION);
						return closeDialogWindow(env);
						//return true;
					}
				case STEP_TO_5:
					if (var == 1) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
			}
		} else if (targetId == 790016) { // Etun
			switch (env.getDialog()) {
				case START_DIALOG:
					if (var == 1)
						return sendQuestDialog(env, 2034);
				case SELECT_ACTION_2035:
					if (var == 1 && player.getInventory().getItemCountByItemId(182204315) != 1) {
						if (giveQuestItem(env, 182204315, 1))
							return sendQuestDialog(env, 2035);
						else
							return true;
					} else
						return sendQuestDialog(env, 2120);
			}
		} else if (targetId == 730036) { // Anoramas
			switch (env.getDialog()) {
				case START_DIALOG:
					if (var == 1)
						return sendQuestDialog(env, 1352);
				case SELECT_ACTION_1353:
					if (var == 1 && player.getInventory().getItemCountByItemId(182204313) != 1) {
						if (giveQuestItem(env, 182204313, 1)) {
							TeleportService2.teleportTo(player, 400010000, 1654.739f, 1137.1085f, 1497.5137f, (byte) 59, TeleportAnimation.BEAM_ANIMATION);
							return closeDialogWindow(env);
						}
						// return sendQuestDialog(env, 1353);
						else
							return true;
					} else
						return sendQuestDialog(env, 1438);
			}
		} else if (targetId == 279000) { // Triminos
			switch (env.getDialog()) {
				case START_DIALOG:
					if (var == 1)
						return sendQuestDialog(env, 1693);
				case SELECT_ACTION_1694:
					if (var == 1 && player.getInventory().getItemCountByItemId(182204314) != 1) {
						if (giveQuestItem(env, 182204314, 1)) {
							TeleportService2.teleportTo(player, 220040000, 1913.1738f, 481.16934f, 411.8228f, (byte) 35, TeleportAnimation.BEAM_ANIMATION);
							return closeDialogWindow(env);
						}
						// return sendQuestDialog(env, 1694);
						else
							return true;
					} else
						return sendQuestDialog(env, 1779);
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item)
	{
		final Player player = env.getPlayer();
		final int id = item.getItemTemplate().getTemplateId();
		final int itemObjId = item.getObjectId();

		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (!player.isInsideZone(ZoneName.get("DF3_ITEMUSEAREA_Q2056")))
			return HandlerResult.FAILED;

		if (id != 182204313 && qs.getQuestVarById(0) == 2 || id != 182204314 && qs.getQuestVarById(0) == 3 || id != 182204315 && qs.getQuestVarById(0) == 4)
			return HandlerResult.UNKNOWN;

		PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 2000, 0, 0), true);
		ThreadPoolManager.getInstance().schedule(() -> {
			PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
			if (qs.getQuestVarById(0) == 2) {
				playQuestMovie(env, 243);
				removeQuestItem(env, id, 1);
				qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
				updateQuestStatus(env);
			} else if (qs.getQuestVarById(0) == 3) {
				playQuestMovie(env, 244);
				removeQuestItem(env, id, 1);
				qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
				updateQuestStatus(env);
			} else if (qs.getQuestVarById(0) == 4 && qs.getStatus() != QuestStatus.COMPLETE && qs.getStatus() != QuestStatus.NONE) {
				removeQuestItem(env, id, 1);
				playQuestMovie(env, 245);
				qs.setStatus(QuestStatus.REWARD);
				updateQuestStatus(env);
			}
		}, 2000);
		return HandlerResult.SUCCESS;
	}
}
