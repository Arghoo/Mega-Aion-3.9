package com.aionemu.commons.configs;

import com.aionemu.commons.configuration.Property;

import java.io.File;

/**
 * This class holds all configuration of database
 * 
 * @author SoulKeeper
 */
public class DatabaseConfig {

	/**
	 * Default database url.
	 */
	@Property(key = "database.url", defaultValue = "jdbc:mysql://localhost:3306/aion_uni")
	public static String DATABASE_URL;

	/**
	 * Name of database Driver
	 */
	@Property(key = "database.driver", defaultValue = "com.mysql.jdbc.Driver")
	public static Class<?> DATABASE_DRIVER;

	/**
	 * Default database user
	 */
	@Property(key = "database.user", defaultValue = "root")
	public static String DATABASE_USER;

	/**
	 * Default database password
	 */
	@Property(key = "database.password", defaultValue = "root")
	public static String DATABASE_PASSWORD;

    /**
     * Amount of partitions used by BoneCP
     */
    @Property(key = "database.bonecp.partition.count", defaultValue = "2")
    public static int DATABASE_BONECP_PARTITION_COUNT;

	/**
	 * Minimum amount of connections that are always active in bonecp partition
	 */
	@Property(key = "database.bonecp.partition.connections.min", defaultValue = "2")
	public static int DATABASE_BONECP_PARTITION_CONNECTIONS_MIN;

	/**
	 * Maximum amount of connections that are allowed to use in bonecp partition
	 */
	@Property(key = "database.bonecp.partition.connections.max", defaultValue = "5")
	public static int DATABASE_BONECP_PARTITION_CONNECTIONS_MAX;

	/**
	 * Location of database script context descriptor
	 */
	@Property(key = "database.scriptcontext.descriptor", defaultValue = "./data/scripts/system/database/database.xml")
	public static File DATABASE_SCRIPTCONTEXT_DESCRIPTOR;
	
}
