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

import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author xTz, Source
 */
public class SM_SHIELD_EFFECT extends AionServerPacket
{
	private Collection<SiegeLocation> locations;

	public SM_SHIELD_EFFECT(Collection<SiegeLocation> locations)
	{
		this.locations = locations;
	}

	public SM_SHIELD_EFFECT(int location)
	{
		this.locations = new ArrayList<SiegeLocation>();
		this.locations.add(SiegeService.getInstance().getSiegeLocation(location));
	}

	@Override
	protected void writeImpl(AionConnection con)
	{
		writeH(locations.size());
		for (SiegeLocation loc : locations) {
			writeD(loc.getLocationId());
			writeC(loc.isUnderShield() ? 1 : 0);
		}
	}
}
