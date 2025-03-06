package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class TimingsConfig {

	/**
	 * Supplement Additional Rates
	 */
	@Property(key = "gameserver.timing.enchant.speed", defaultValue = "5000")
	public static int ENCHANT_SPEED;

  @Property(key = "gameserver.timing.extract.speed", defaultValue = "5000")
	public static int EXTRACT_SPEED;

  @Property(key = "gameserver.timing.riding.speed", defaultValue = "3000")
	public static int RIDING_SPEED;

	@Property(key = "gameserver.timing.decompose.speed", defaultValue = "3000")
	public static int DECOMPOSE_SPEED;
}
