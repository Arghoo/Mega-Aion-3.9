/**
 * This file is part of aion-unique <aion-unique.org>.
 * <p>
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.taskmanager.fromdb.handler.TaskFromDBHandler;
import com.aionemu.gameserver.taskmanager.fromdb.handler.TaskFromDBHandlerHolder;
import com.aionemu.gameserver.taskmanager.fromdb.trigger.TaskFromDBTrigger;
import com.aionemu.gameserver.taskmanager.fromdb.trigger.TaskFromDBTriggerHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

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
		ArrayList<TaskFromDBTrigger> result = new ArrayList<TaskFromDBTrigger>();

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
