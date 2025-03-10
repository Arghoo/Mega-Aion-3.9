/**
 * This file is part of aion-unique <aion-unique.smfnew.com>.
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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.SerialKillerService;

/**
 * @author Lyahim
 */
public class CM_SHOW_MAP extends AionClientPacket
{
	public CM_SHOW_MAP(int opcode, State state, State... restStates)
	{
		super(opcode, state, restStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{
		// empty packet
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{
		Player player = getConnection().getActivePlayer();
		SerialKillerService.getInstance().updateIcons(player);
	}
}
