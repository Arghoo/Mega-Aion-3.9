package com.aionemu.commons.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Disturbing
 */
public final class DB
{
	private static final Logger log = LoggerFactory.getLogger(DB.class);

	/**
	 * Prevent instantiation
	 */
	private DB()
	{
	}

	/**
	 * Executes Select Query. Uses ReadSth to utilize params and return data. Recycles connection after completion.
	 *
	 * @param query
	 * @param reader
	 * @return boolean Success
	 */
	public static boolean select(String query, ReadStH reader)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(query)) {
			if (reader instanceof ParamReadStH)
				((ParamReadStH) reader).setParams(stmt);

			ResultSet rset = stmt.executeQuery();
			reader.handleRead(rset);
		} catch (Exception e) {
			log.error("Error executing select query " + query, e);
			return false;
		}

		return true;
	}

	/**
	 * Call stored procedure
	 *
	 * @param query
	 * @param reader
	 */
	public static boolean call(String query, ReadStH reader)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 CallableStatement stmt = con.prepareCall(query)) {
			if (reader instanceof CallReadStH) {
				((CallReadStH) reader).setParams(stmt);
			}
			ResultSet rset = stmt.executeQuery();
			reader.handleRead(rset);
		} catch (Exception e) {
			log.error("Error calling stored procedure " + query, e);
			return false;
		}
		return true;
	}

	/**
	 * Executes Insert or Update Query not needing any further modification or batching. Recycles connection after
	 * completion.
	 *
	 * @param query
	 * @return boolean Success
	 */
	public static boolean insertUpdate(String query)
	{
		return insertUpdate(query, null);
	}

	/**
	 * Executes Insert / Update Query. Utilizes IUSth for Batching and Query Editing. MUST MANUALLY EXECUTE QUERY / BATACH
	 * IN IUSth (No need to close Statement after execution)
	 *
	 * @param query
	 * @param batch
	 * @return boolean Success
	 */
	public static boolean insertUpdate(String query, IUStH batch)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(query)) {
			if (batch != null) {
				batch.handleInsertUpdate(stmt);
			} else {
				stmt.executeUpdate();
			}

		} catch (Exception e) {
			log.error("Failed to execute IU query " + query, e);
			return false;
		}
		return true;
	}

	/**
	 * Begins new transaction
	 *
	 * @return new Transaction object
	 * @throws java.sql.SQLException if was unable to create transaction
	 */
	public static Transaction beginTransaction() throws SQLException
	{
		Connection con = DatabaseFactory.getConnection();
		return new Transaction(con);
	}

	/**
	 * Creates PreparedStatement with given sql string.<br>
	 * Statemens are created with {@link java.sql.ResultSet#TYPE_FORWARD_ONLY} and
	 * {@link java.sql.ResultSet#CONCUR_READ_ONLY}
	 *
	 * @param sql SQL query
	 * @return Prepared statement if ok or null if error happened while creating
	 */
	public static PreparedStatement prepareStatement(String sql)
	{
		return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}

	/**
	 * Creates {@link java.sql.PreparedStatement} with given sql<br>
	 *
	 * @param sql                  SQL query
	 * @param resultSetType        a result set type; one of <br>
	 *                             <code>ResultSet.TYPE_FORWARD_ONLY</code>,<br>
	 *                             <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or <br>
	 *                             <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
	 * @param resultSetConcurrency a concurrency type; one of <br>
	 *                             <code>ResultSet.CONCUR_READ_ONLY</code> or <br>
	 *                             <code>ResultSet.CONCUR_UPDATABLE</code>
	 * @return Prepared Statement if ok or null if error happened while creating
	 */
	public static PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
	{
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DatabaseFactory.getConnection();
			ps = c.prepareStatement(sql, resultSetType, resultSetConcurrency);
		} catch (Exception e) {
			log.error("Can't create PreparedStatement for query: " + sql, e);
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e1) {
					log.error("Can't close connection after exception", e1);
				}
			}
		}

		return ps;
	}

	/**
	 * Executes PreparedStatement
	 *
	 * @param statement PreparedStatement to execute
	 * @return returns result of {@link java.sql.PreparedStatement#executeQuery()} or -1 in case of error
	 */
	public static int executeUpdate(PreparedStatement statement)
	{
		try {
			return statement.executeUpdate();
		} catch (Exception e) {
			log.error("Can't execute update for PreparedStatement", e);
		}

		return -1;
	}

	/**
	 * Executes PreparedStatement and closes it and it's connection
	 *
	 * @param statement PreparedStatement to close
	 */
	public static void executeUpdateAndClose(PreparedStatement statement)
	{
		executeUpdate(statement);
		close(statement);
	}

	/**
	 * Executes Query and returns ResultSet
	 *
	 * @param statement preparedStatement to execute
	 * @return ResultSet or null if error
	 */
	public static ResultSet executeQuery(PreparedStatement statement)
	{
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (Exception e) {
			log.error("Error while executing query", e);
		}
		return rs;
	}

	/**
	 * Closes PreparedStatement, it's connection and last ResultSet
	 *
	 * @param statement statement to close
	 */
	public static void close(PreparedStatement statement)
	{
		if (statement == null) // e.g. null from failed DB.prepareStatement call
			return;

		try {
			if (statement.isClosed()) {
				// noinspection ThrowableInstanceNeverThrown
				log.warn("Attempt to close PreparedStatement that is closed already", new Exception());
				return;
			}

			Connection c = statement.getConnection();
			statement.close();
			c.close();
		} catch (Exception e) {
			log.error("Error while closing PreparedStatement", e);
		}
	}

	/**
	 * @param table
	 * @param pkColumn
	 */
	public static int[] getUsedIDs(String table, String pkColumn)
	{
		return getUsedIDs(table, pkColumn, "");
	}

	/**
	 * @param table
	 * @param pkColumn
	 */
	public static int[] getUsedIDs(String table, String pkColumn, String conditionStatement)
	{
		String sql = "SELECT `" + pkColumn + "` FROM " + table;
		if (!conditionStatement.isEmpty()) {
			sql += " WHERE " + conditionStatement;
		}

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			ResultSet rs = statement.executeQuery();
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			int[] ids = new int[count];
			for (int i = 0; i < count; i++) {
				rs.next();
				ids[i] = rs.getInt("id");
			}
			return ids;
		} catch (SQLException e) {
			log.error("Can't get list of id's from players table", e);
		}

		return new int[0];
	}
}
