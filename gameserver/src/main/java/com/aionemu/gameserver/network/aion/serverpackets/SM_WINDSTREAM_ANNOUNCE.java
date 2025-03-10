/**
 * This file is part of mega-aion <mega-aion.com>
 * <p>
 * mega-aion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * mega-aion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser Public License
 * along with mega-aion.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author LokiReborn
 *
 */
public class SM_WINDSTREAM_ANNOUNCE extends AionServerPacket
{
	private int bidirectional;
	private int mapId;
	private int streamId;
	private int state;

	public SM_WINDSTREAM_ANNOUNCE(int bidirectional, int mapId, int streamId, int state)
	{
		this.bidirectional = bidirectional;
		this.mapId = mapId;
		this.streamId = streamId;
		this.state = state;
	}

	@Override
	protected void writeImpl(AionConnection con)
	{
		writeD(bidirectional);
		writeD(mapId);
		writeD(streamId);
		writeC(state);
	}
}
