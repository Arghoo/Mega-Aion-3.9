/*
 * This file is part of mega-aion <mega-aion.com>.
 *
 * mega-aion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mega-aion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mega-aion. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class MembershipConfig
{
	@Property(key = "gameserver.instances.title.requirement", defaultValue = "10")
	public static byte INSTANCES_TITLE_REQ;

	@Property(key = "gameserver.instances.race.requirement", defaultValue = "10")
	public static byte INSTANCES_RACE_REQ;

	@Property(key = "gameserver.instances.level.requirement", defaultValue = "10")
	public static byte INSTANCES_LEVEL_REQ;

	@Property(key = "gameserver.instances.group.requirement", defaultValue = "10")
	public static byte INSTANCES_GROUP_REQ;

	@Property(key = "gameserver.instances.quest.requirement", defaultValue = "10")
	public static byte INSTANCES_QUEST_REQ;

	@Property(key = "gameserver.instances.cooldown", defaultValue = "10")
	public static byte INSTANCES_COOLDOWN;

	@Property(key = "gameserver.store.wh.all", defaultValue = "10")
	public static byte STORE_WH_ALL;

	@Property(key = "gameserver.store.accountwh.all", defaultValue = "10")
	public static byte STORE_AWH_ALL;

	@Property(key = "gameserver.store.legionwh.all", defaultValue = "10")
	public static byte STORE_LWH_ALL;

	@Property(key = "gameserver.trade.all", defaultValue = "10")
	public static byte TRADE_ALL;

	@Property(key = "gameserver.disable.soulbind", defaultValue = "10")
	public static byte DISABLE_SOULBIND;

	@Property(key = "gameserver.remodel.all", defaultValue = "10")
	public static byte REMODEL_ALL;

	@Property(key = "gameserver.emotions.all", defaultValue = "10")
	public static byte EMOTIONS_ALL;

	@Property(key = "gameserver.quest.stigma.slot", defaultValue = "10")
	public static byte STIGMA_SLOT_QUEST;

	@Property(key = "gameserver.soulsickness.disable", defaultValue = "10")
	public static byte DISABLE_SOULSICKNESS;

	@Property(key = "gameserver.autolearn.skill", defaultValue = "10")
	public static byte SKILL_AUTOLEARN;

	@Property(key = "gameserver.autolearn.stigma", defaultValue = "10")
	public static byte STIGMA_AUTOLEARN;

	@Property(key = "gameserver.quest.limit.disable", defaultValue = "10")
	public static byte QUEST_LIMIT_DISABLED;

	@Property(key = "gameserver.titles.additional.enable", defaultValue = "10")
	public static byte TITLES_ADDITIONAL_ENABLE;

	@Property(key = "gameserver.character.additional.enable", defaultValue = "10")
	public static byte CHARACTER_ADDITIONAL_ENABLE;

	@Property(key = "gameserver.advanced.friendlist.enable", defaultValue = "10")
	public static byte ADVANCED_FRIENDLIST_ENABLE;

	@Property(key = "gameserver.character.additional.count", defaultValue = "8")
	public static byte CHARACTER_ADDITIONAL_COUNT;

	@Property(key = "gameserver.advanced.friendlist.size", defaultValue = "90")
	public static int ADVANCED_FRIENDLIST_SIZE;

	@Property(key = "gameserver.membership.tag.display.enable", defaultValue = "true")
	public static boolean MEMBERSHIP_TAG_ENABLED;

	@Property(key = "gameserver.membership.tag.premium", defaultValue = "\uE02C %s \uE02C")
	public static String MEMBERSHIP_TAG_PREMIUM;

	@Property(key = "gameserver.membership.tag.vip", defaultValue = "\uE02D %s \uE02D")
	public static String MEMBERSHIP_TAG_VIP;
}
