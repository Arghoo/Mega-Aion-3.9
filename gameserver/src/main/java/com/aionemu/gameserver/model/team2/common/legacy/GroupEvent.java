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
package com.aionemu.gameserver.model.team2.common.legacy;

/**
 * @author Lyahim
 */
public enum GroupEvent
{
	LEAVE(0),
	MOVEMENT(1),
	DISCONNECTED(3),
	JOIN(5),
	ENTER_OFFLINE(7),
	ENTER(13),
	UPDATE(13),
	UNK(9); // to do
	private int id;

	private GroupEvent(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}
}
