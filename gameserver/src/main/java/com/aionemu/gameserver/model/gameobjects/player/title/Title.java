/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.aionemu.gameserver.model.gameobjects.player.title;

import com.aionemu.gameserver.model.IExpirable;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.TitleTemplate;

/**
 * @author Mr. Poke
 */
public class Title implements IExpirable
{
	private TitleTemplate template;
	private int id;
	private int dispearTime;

	/**
	 * @param template
	 * @param id
	 * @param dispearTime
	 */
	public Title(TitleTemplate template, int id, int dispearTime)
	{
		this.template = template;
		this.id = id;
		this.dispearTime = dispearTime;
	}

	/**
	 * @return Returns the template.
	 */
	public TitleTemplate getTemplate()
	{
		return template;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @return Returns the dispearTime.
	 */
	public int getRemainingTime()
	{
		if (dispearTime == 0)
			return 0;
		return dispearTime - (int) (System.currentTimeMillis() / 1000);
	}

	@Override
	public int getExpireTime()
	{
		return dispearTime;
	}

	@Override
	public void expireEnd(Player player)
	{
		player.getTitleList().removeTitle(id);
	}

	@Override
	public void expireMessage(Player player, int time)
	{
	}

	@Override
	public boolean canExpireNow()
	{
		return true;
	}
}
