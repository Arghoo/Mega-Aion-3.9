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
package com.aionemu.gameserver.questEngine.handlers.template;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.*;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Wakizashi, vlog, Bobobear
 * @reworked Luzien
 */
public class FountainRewards extends QuestHandler {

	private final int questId;
	private final Set<Integer> startNpcs = new HashSet<Integer>();

	public FountainRewards(int questId, List<Integer> startNpcIds) {
		super(questId);
		this.questId = questId;
		this.startNpcs.addAll(startNpcIds);
		this.startNpcs.remove(0);
	}

	@Override
	public void register() {
		Iterator<Integer> iterator = startNpcs.iterator();
		while (iterator.hasNext()) {
			int startNpc = iterator.next();
			qe.registerQuestNpc(startNpc).addOnQuestStart(getQuestId());
			qe.registerQuestNpc(startNpc).addOnTalkEvent(getQuestId());
		}
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (startNpcs.contains(targetId)) {  // Coin Fountain
				switch (dialog) {
					case USE_OBJECT: {
						if (!QuestService.inventoryItemCheck(env, true)) {
							return true;
						}
						else return sendQuestSelectionDialog(env);
					}
					case STEP_TO_1: {
						if (QuestService.collectItemCheck(env, false)) {
							if (!player.getInventory().isFullSpecialCube()) {
								if (QuestService.startQuest(env)) {
									changeQuestStep(env, 0, 0, true);
									return sendQuestDialog(env, 5);
								}
							}
							else {
								PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_FULL_INVENTORY);
								return sendQuestSelectionDialog(env);
							}
						}
						else {
							return sendQuestSelectionDialog(env);
						}
					}
				}
			}
		}
		else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			// Coin Fountain
			if (startNpcs.contains(targetId)) {  // Coin Fountain
				if (dialog == QuestDialog.SELECT_NO_REWARD) {
					if (QuestService.collectItemCheck(env, true))
						return sendQuestEndDialog(env);
				}
				else {
					return QuestService.abandonQuest(player, questId);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onCanAct(QuestEnv env, QuestActionType questEventType, Object... objects) {
		if (startNpcs.contains(env.getTargetId())) {  // Coin Fountain
			return true;
		}
		return false;
	}
}
