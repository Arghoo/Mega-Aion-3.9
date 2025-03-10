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

package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.aionemu.gameserver.model.templates.staticdoor.StaticDoorWorld;

/**
 * @author Wakizashi
 */
@XmlRootElement(name = "staticdoor_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class StaticDoorData
{
	@XmlElement(name = "world")
	private List<StaticDoorWorld> staticDorWorlds;

	/** A map containing all door templates */
	private TIntObjectHashMap<StaticDoorWorld> staticDoorData = new TIntObjectHashMap<StaticDoorWorld>();

	/**
	 * @param u
	 * @param parent
	 */
	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		staticDoorData.clear();

		for (StaticDoorWorld world : staticDorWorlds) {
			staticDoorData.put(world.getWorld(), world);
		}
	}

	public int size()
	{
		return staticDoorData.size();
	}

	/**
	 * @param world
	 */
	public StaticDoorWorld getStaticDoorWorlds(int world)
	{
		return staticDoorData.get(world);
	}

	/**
	 * @return the staticDorWorlds
	 */
	public List<StaticDoorWorld> getStaticDorWorlds()
	{
		return staticDorWorlds;
	}

	/**
	 *
	 */
	public void setStaticDorWorlds(List<StaticDoorWorld> staticDorWorlds)
	{
		this.staticDorWorlds = staticDorWorlds;
		afterUnmarshal(null, null);
	}
}
