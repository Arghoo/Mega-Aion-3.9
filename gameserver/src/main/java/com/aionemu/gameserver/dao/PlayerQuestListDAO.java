package com.aionemu.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.utils.GenericValidator;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author MrPoke
 * @modified vlog
 */
public class PlayerQuestListDAO
{
	private static final Logger log = LoggerFactory.getLogger(PlayerQuestListDAO.class);

	public static final String SELECT_QUERY = "SELECT `quest_id`, `status`, `quest_vars`, `complete_count`, `next_repeat_time`, `reward`, `complete_time` FROM `player_quests` WHERE `player_id`=?";
	public static final String UPDATE_QUERY = "UPDATE `player_quests` SET `status`=?, `quest_vars`=?, `complete_count`=?, `next_repeat_time`=?, `reward`=?, `complete_time`=? WHERE `player_id`=? AND `quest_id`=?";
	public static final String DELETE_QUERY = "DELETE FROM `player_quests` WHERE `player_id`=? AND `quest_id`=?";
	public static final String INSERT_QUERY = "INSERT INTO `player_quests` (`player_id`, `quest_id`, `status`, `quest_vars`, `complete_count`, `next_repeat_time`, `reward`, `complete_time`) VALUES (?,?,?,?,?,?,?,?)";

	/**
	 * @param player
	 * @return QuestStateList
	 */
	public static QuestStateList load(final Player player)
	{
		QuestStateList questStateList = new QuestStateList();

		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					int questId = rset.getInt("quest_id");
					int questVars = rset.getInt("quest_vars");
					int completeCount = rset.getInt("complete_count");
					Timestamp nextRepeatTime = rset.getTimestamp("next_repeat_time");
					Integer reward = rset.getInt("reward");
					if (rset.wasNull())
						reward = 0;
					Timestamp completeTime = rset.getTimestamp("complete_time");
					QuestStatus status = QuestStatus.valueOf(rset.getString("status"));
					QuestState questState = new QuestState(questId, status, questVars, completeCount, nextRepeatTime, reward, completeTime);
					questState.setPersistentState(PersistentState.UPDATED);
					questStateList.addQuest(questId, questState);
				}
			}
		} catch (Exception e) {
			log.error("Could not restore QuestStateList data for player: " + player.getObjectId() + " from DB: " + e.getMessage(), e);
		}

		return questStateList;
	}

	/**
	 * @param player
	 */
	public static void store(Player player)
	{
		Collection<QuestState> qsList = player.getQuestStateList().getAllQuestState();
		if (GenericValidator.isBlankOrNull(qsList)) {
			return;
		}

		try (Connection con = DatabaseFactory.getConnection()) {
			con.setAutoCommit(false);

			deleteQuest(con, player.getObjectId(), qsList);

			addQuests(con, player.getObjectId(), qsList);
			updateQuests(con, player.getObjectId(), qsList);
		} catch (SQLException e) {
			log.error("Can't save quests for player " + player.getObjectId(), e);
		}

		for (QuestState qs : qsList) {
			qs.setPersistentState(PersistentState.UPDATED);
		}
	}

	private static void addQuests(Connection con, int playerId, Collection<QuestState> states)
	{
		states = states.stream().filter(i -> i != null && PersistentState.NEW == i.getPersistentState()).collect(Collectors.toList());
		if (GenericValidator.isBlankOrNull(states)) {
			return;
		}

		try (PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {
			for (QuestState qs : states) {
				ps.setInt(1, playerId);
				ps.setInt(2, qs.getQuestId());
				ps.setString(3, qs.getStatus().toString());
				ps.setInt(4, qs.getQuestVars().getQuestVars());
				ps.setInt(5, qs.getCompleteCount());
				if (qs.getNextRepeatTime() != null) {
					ps.setTimestamp(6, qs.getNextRepeatTime());
				} else {
					ps.setNull(6, Types.TIMESTAMP);
				}
				if (qs.getReward() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, qs.getReward());
				}
				if (qs.getCompleteTime() == null)
					ps.setNull(8, Types.TIMESTAMP);
				else
					ps.setTimestamp(8, qs.getCompleteTime());
				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Failed to insert new quests for player " + playerId);
		}
	}

	private static void updateQuests(Connection con, int playerId, Collection<QuestState> states)
	{
		states = states.stream().filter(i -> i != null && PersistentState.UPDATE_REQUIRED == i.getPersistentState()).collect(Collectors.toList());
		if (GenericValidator.isBlankOrNull(states)) {
			return;
		}

		try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
			for (QuestState qs : states) {
				ps.setString(1, qs.getStatus().toString());
				ps.setInt(2, qs.getQuestVars().getQuestVars());
				ps.setInt(3, qs.getCompleteCount());
				if (qs.getNextRepeatTime() != null) {
					ps.setTimestamp(4, qs.getNextRepeatTime());
				} else {
					ps.setNull(4, Types.TIMESTAMP);
				}
				if (qs.getReward() == null) {
					ps.setNull(5, Types.SMALLINT);
				} else {
					ps.setInt(5, qs.getReward());
				}
				if (qs.getCompleteTime() == null)
					ps.setNull(6, Types.TIMESTAMP);
				else
					ps.setTimestamp(6, qs.getCompleteTime());
				ps.setInt(7, playerId);
				ps.setInt(8, qs.getQuestId());
				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Failed to update existing quests for player " + playerId);
		}
	}

	private static void deleteQuest(Connection con, int playerId, Collection<QuestState> states)
	{
		states = states.stream().filter(i -> i != null && PersistentState.DELETED == i.getPersistentState()).collect(Collectors.toList());
		if (GenericValidator.isBlankOrNull(states)) {
			return;
		}

		try (PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {
			for (QuestState qs : states) {
				ps.setInt(1, playerId);
				ps.setInt(2, qs.getQuestId());
				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			log.error("Failed to delete existing quests for player " + playerId);
		}
	}
}
