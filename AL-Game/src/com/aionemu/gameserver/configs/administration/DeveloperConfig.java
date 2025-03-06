package com.aionemu.gameserver.configs.administration;

import com.aionemu.commons.configuration.Property;

/**
 * @author ATracer
 */
public class DeveloperConfig {

	/**
	 * if false - not spawns will be loaded
	 */
	@Property(key = "gameserver.developer.spawn.enable", defaultValue = "true")
	public static boolean SPAWN_ENABLE;

	/**
	 * if true - checks spawns being outside any known zones
	 */
	@Property(key = "gameserver.developer.spawn.check", defaultValue = "false")
	public static boolean SPAWN_CHECK;

	/**
	 * if set, adds specified stat bonus for items with random bonusess
	 */
	@Property(key = "gameserver.developer.itemstat.id", defaultValue = "0")
	public static int ITEM_STAT_ID;

}
