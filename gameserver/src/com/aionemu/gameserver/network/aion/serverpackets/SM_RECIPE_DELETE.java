/*
 * This file is part of mega-aion <mega-aion.com>.
 *
 * mega-aion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mega-aion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mega-aion.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;


import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author namedrisk
 */
public class SM_RECIPE_DELETE extends AionServerPacket {

	private int recipeId;

	public SM_RECIPE_DELETE(int recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeD(recipeId);
	}
}
