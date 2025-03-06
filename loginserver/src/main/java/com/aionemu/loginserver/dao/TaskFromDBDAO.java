package com.aionemu.loginserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.loginserver.taskmanager.handler.TaskFromDBHandler;
import com.aionemu.loginserver.taskmanager.handler.TaskFromDBHandlerHolder;
import com.aionemu.loginserver.taskmanager.trigger.TaskFromDBTrigger;

import com.aionemu.loginserver.taskmanager.trigger.TaskFromDBTriggerHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Divinity, nrg
 */
public class TaskFromDBDAO
{
	private static final Logger log = LoggerFactory.getLogger(TaskFromDBDAO.class);
	private static final String SELECT_ALL_QUERY = "SELECT * FROM tasks ORDER BY id";

	/**
	 * Return all tasks from DB
	 *
	 * @return all tasks
	 */
	public static ArrayList<TaskFromDBTrigger> getAllTasks()
	{
		final ArrayList<TaskFromDBTrigger> result = new ArrayList<TaskFromDBTrigger>();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ALL_QUERY)) {
			try (ResultSet rset = stmt.executeQuery()) {

				while (rset.next()) {
					try {
						TaskFromDBTrigger trigger = TaskFromDBTriggerHolder.valueOf(rset.getString("trigger_type")).getTriggerClass().newInstance();
						TaskFromDBHandler handler = TaskFromDBHandlerHolder.valueOf(rset.getString("task_type")).getTaskClass().newInstance();

						handler.setTaskId(rset.getInt("id"));

						String execParamsResult = rset.getString("exec_param");
						if (execParamsResult != null) {
							handler.setParams(rset.getString("exec_param").split(" "));
						}

						trigger.setHandlerToTrigger(handler);

						String triggerParamsResult = rset.getString("trigger_param");
						if (triggerParamsResult != null) {
							trigger.setParams(rset.getString("trigger_param").split(" "));
						}

						result.add(trigger);

					} catch (InstantiationException | IllegalAccessException ex) {
						log.error(ex.getMessage(), ex);
					}
				}
			}
		} catch (SQLException e) {
			log.error("Loading tasks failed: ", e);
		}

		return result;
	}
}
