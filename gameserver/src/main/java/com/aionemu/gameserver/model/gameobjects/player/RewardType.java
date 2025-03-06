/*
 * This file is part of mega-aion <mega-aion.com>.
 *
 *  mega-aion is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  mega-aion is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with mega-aion.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aionemu.gameserver.model.gameobjects.player;

import java.util.Calendar;

import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author antness
 */

public enum RewardType
{
	AP_PLAYER {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 1.2f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getApPlayerGainRate() * statRate * dateRates);
		}
	},
	AP_NPC {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 1.2f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getApNpcRate() * statRate * dateRates);
		}
	},
	HUNTING {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 2.0f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_HUNTING_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getXpRate() * statRate * dateRates);
		}
	},
	GROUP_HUNTING {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 2.0f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_GROUP_HUNTING_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getGroupXpRate() * statRate * dateRates);
		}
	},
	PVP_KILL {
		@Override
		public long calcReward(Player player, long reward)
		{
			return (reward);
		}
	},
	QUEST {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 1.2f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getQuestXpRate() * statRate * dateRates);
		}
	},
	CRAFTING {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 1.2f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_CRAFTING_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getCraftingXPRate() * statRate * dateRates);
		}
	},
	GATHERING {
		@Override
		public long calcReward(Player player, long reward)
		{
			Calendar today = Calendar.getInstance();
			boolean isWeekend = today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			float dateRates = isWeekend ? 1.2f : 1.0f;
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_GATHERING_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getGatheringXPRate() * statRate * dateRates);
		}
	};

	public abstract long calcReward(Player player, long reward);
}
