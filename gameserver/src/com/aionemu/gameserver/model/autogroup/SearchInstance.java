/**
 * This file is part of mega-aion <mega-aion.com>.
 *
 *  mega-aion is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with mega-aion.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.autogroup;

import static ch.lambdaj.Lambda.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author xTz
 */
public class SearchInstance {

	private long registrationTime = System.currentTimeMillis();
	private byte instanceMaskId;
	private EntryRequestType ert;
	private List<Integer> members;

	public SearchInstance(byte instanceMaskId, EntryRequestType ert, Collection<Player> members) {
		this.instanceMaskId = instanceMaskId;
		this.ert = ert;
		if (members != null) {
			this.members = extract(members, on(Player.class).getObjectId());
		}
	}

	public List<Integer> getMembers() {
		return members;
	}

	public byte getInstanceMaskId() {
		return instanceMaskId;
	}

	public int getRemainingTime() {
		return (int) (System.currentTimeMillis() - registrationTime) / 1000 * 256;
	}

	public EntryRequestType getEntryRequestType() {
		return ert;
	}

	public boolean isDredgion() {
		return instanceMaskId == 1 || instanceMaskId == 2 || instanceMaskId == 3;
	}
}
