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
package com.aionemu.gameserver.questEngine.handlers.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.CraftingRewards;

/**
 * @author Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CraftingRewardsData")
public class CraftingRewardsData extends XMLQuest
{
	@XmlAttribute(name = "start_npc_id", required = true)
	protected int startNpcId;
	@XmlAttribute(name = "end_npc_id")
	protected int endNpcId;
	@XmlAttribute(name = "skill_id")
	protected int skillId;
	@XmlAttribute(name = "level_reward")
	protected int levelReward;

	@Override
	public void register(QuestEngine questEngine)
	{
		CraftingRewards template = new CraftingRewards(id, startNpcId, skillId, levelReward, endNpcId, questMovie);
		questEngine.addQuestHandler(template);
	}
}
