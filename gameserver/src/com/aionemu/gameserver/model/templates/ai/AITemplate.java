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
package com.aionemu.gameserver.model.templates.ai;

import com.aionemu.gameserver.model.ai.Ai;
import com.aionemu.gameserver.model.ai.Bombs;
import com.aionemu.gameserver.model.ai.Summons;

/**
 * @author xTz
 */
public class AITemplate {

	private int npcId;
	private Summons summons;
	private Bombs bombs;

	public AITemplate() {
	}

	public AITemplate(Ai template) {
		this.summons = template.getSummons();
		this.bombs = template.getBombs();
		this.npcId = template.getNpcId();
	}

	public int getNpcId() {
		return npcId;
	}

	public Summons getSummons() {
		return summons;
	}

	public Bombs getBombs() {
		return bombs;
	}
}
