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
import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.model.PetitionStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zdead
 */
public class PetitionDAO
{
	private static final Logger log = LoggerFactory.getLogger(PetitionDAO.class);

	public static synchronized int getNextAvailableId()
	{
		int result = 0;
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT MAX(id) as nextid FROM petitions")) {
			try (ResultSet rset = stmt.executeQuery()) {
				rset.next();
				result = rset.getInt("nextid") + 1;
			}
		} catch (Exception e) {
			log.error("Cannot get next available petition id", e);
			return 0;
		}

		return result;
	}

	public static void insertPetition(Petition petition)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("INSERT INTO petitions (id, player_id, type, title, message, add_data, time, status) VALUES(?,?,?,?,?,?,?,?)")) {
			stmt.setInt(1, petition.getPetitionId());
			stmt.setInt(2, petition.getPlayerObjId());
			stmt.setInt(3, petition.getPetitionType().getElementId());
			stmt.setString(4, petition.getTitle());
			stmt.setString(5, petition.getContentText());
			stmt.setString(6, petition.getAdditionalData());
			stmt.setLong(7, new Date().getTime() / 1000);
			stmt.setString(8, petition.getStatus().toString());
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot insert petition", e);
		}
	}

	public static void deletePetition(int playerObjId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("DELETE FROM petitions WHERE player_id = ? AND (status = 'PENDING' OR status='IN_PROGRESS')")) {
			stmt.setInt(1, playerObjId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot delete petition", e);
		}
	}

	public static Set<Petition> getPetitions()
	{
		Set<Petition> results = new HashSet<>();
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT * FROM petitions WHERE status = 'PENDING' OR status = 'IN_PROGRESS' ORDER BY id ASC")) {
			try (ResultSet rset = stmt.executeQuery()) {
				while (rset.next()) {
					String statusValue = rset.getString("status");
					PetitionStatus status;
					if (statusValue.equals("PENDING"))
						status = PetitionStatus.PENDING;
					else if (statusValue.equals("IN_PROGRESS"))
						status = PetitionStatus.IN_PROGRESS;
					else
						status = PetitionStatus.PENDING;

					Petition p = new Petition(rset.getInt("id"), rset.getInt("player_id"), rset.getInt("type"), rset.getString("title"), rset.getString("message"), rset.getString("add_data"), status.getElementId());
					results.add(p);
				}
			}
		} catch (Exception e) {
			log.error("Cannot get next available petition id", e);
			return null;
		}

		return results;
	}

	public static Petition getPetitionById(int petitionId)
	{
		Petition result = null;
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT * FROM petitions WHERE id = ?")) {
			stmt.setInt(1, petitionId);
			try (ResultSet rset = stmt.executeQuery()) {
				if (!rset.next()) {
					return null;
				}

				String statusValue = rset.getString("status");
				PetitionStatus status;
				if (statusValue.equals("PENDING"))
					status = PetitionStatus.PENDING;
				else if (statusValue.equals("IN_PROGRESS"))
					status = PetitionStatus.IN_PROGRESS;
				else
					status = PetitionStatus.PENDING;

				result = new Petition(rset.getInt("id"), rset.getInt("player_id"), rset.getInt("type"), rset.getString("title"), rset.getString("message"), rset.getString("add_data"), status.getElementId());
			}
		} catch (Exception e) {
			log.error("Cannot get petition #" + petitionId, e);
		}

		return result;
	}

	public static void setReplied(int petitionId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			 PreparedStatement stmt = con.prepareStatement("UPDATE petitions SET status = 'REPLIED' WHERE id = ?")) {
			stmt.setInt(1, petitionId);
			stmt.execute();
		} catch (Exception e) {
			log.error("Cannot set petition replied", e);
		}
	}
}
