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

package quest.theobomos;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RewardType;
import com.aionemu.gameserver.model.templates.quest.Rewards;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Balthazar
 */

public class _3055FugitiveScopind extends QuestHandler
{
	private final static int questId = 3055;

	public _3055FugitiveScopind()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.registerQuestNpc(730146).addOnQuestStart(questId);
		qe.registerQuestNpc(730146).addOnTalkEvent(questId);
		qe.registerQuestNpc(798195).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 730146) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						return sendQuestDialog(env, 4762);
					}
					case STEP_TO_1: {
						QuestService.startQuest(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
						return true;
					}
					default:
						return sendQuestStartDialog(env);
				}
			}
		}

		if (qs == null)
			return false;

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 798195: {
					switch (env.getDialog()) {
						case START_DIALOG: {
							long itemCount = player.getInventory().getItemCountByItemId(182208040);
							if (itemCount >= 1) {
								return sendQuestDialog(env, 5);
							}
						}
						case SELECT_NO_REWARD: {
							qs.setStatus(QuestStatus.COMPLETE);
							qs.setCompleteCount(1);
							removeQuestItem(env, 182208040, 1);
							Rewards rewards = DataManager.QUEST_DATA.getQuestById(questId).getRewards().get(0);
							int rewardExp = rewards.getExp();
							int rewardKinah = (int) (player.getRates().getQuestKinahRate() * rewards.getGold());
							giveQuestItem(env, 182400001, rewardKinah);
							player.getCommonData().addExp(rewardExp, RewardType.QUEST);
							PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(questId, QuestStatus.COMPLETE, 2));
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
							updateQuestStatus(env);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
