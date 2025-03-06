package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class ArenaScheduleConfig
{
	/**
	 * Supplement Additional Rates
	 */
	@Property(key = "gameserver.arena.discipline.start1", defaultValue = "9")
	public static int DISCIPLINE_START1;
	@Property(key = "gameserver.arena.discipline.end1", defaultValue = "14")
	public static int DISCIPLINE_END1;

	@Property(key = "gameserver.arena.discipline.start2", defaultValue = "21")
	public static int DISCIPLINE_START2;
	@Property(key = "gameserver.arena.discipline.end2", defaultValue = "2")
	public static int DISCIPLINE_END2;

	@Property(key = "gameserver.arena.harmony.start1", defaultValue = "9")
	public static int HARMONY_START1;
	@Property(key = "gameserver.arena.harmony.end1", defaultValue = "14")
	public static int HARMONY_END1;

	@Property(key = "gameserver.arena.harmony.start2", defaultValue = "21")
	public static int HARMONY_START2;
	@Property(key = "gameserver.arena.harmony.end2", defaultValue = "2")
	public static int HARMONY_END2;

	@Property(key = "gameserver.arena.glory.start1", defaultValue = "9")
	public static int GLORY_START1;
	@Property(key = "gameserver.arena.glory.end1", defaultValue = "14")
	public static int GLORY_END1;

	@Property(key = "gameserver.arena.glory.start2", defaultValue = "21")
	public static int GLORY_START2;
	@Property(key = "gameserver.arena.glory.end2", defaultValue = "2")
	public static int GLORY_END2;
}
