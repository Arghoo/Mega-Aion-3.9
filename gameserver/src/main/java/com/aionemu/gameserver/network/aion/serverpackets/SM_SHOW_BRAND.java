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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Sweetkr
 */
public class SM_SHOW_BRAND extends AionServerPacket
{
	private int brandId;
	private int targetObjectId;

	public SM_SHOW_BRAND(int brandId, int targetObjectId)
	{
		this.brandId = brandId;
		this.targetObjectId = targetObjectId;
	}

	@Override
	protected void writeImpl(AionConnection con)
	{
		writeH(0x01);
		writeD(0x01); // unk
		writeD(brandId);
		writeD(targetObjectId);
	}
}
