/*
 * This file is part of mega-aion <mega-aion.com>
 *
 * mega-aion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mega-aion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mega-aion. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dataholders;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.ai.Ai;
import com.aionemu.gameserver.model.templates.ai.AITemplate;

/**
 * @author xTz
 */
@XmlRootElement(name = "ai_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class AIData
{
	@XmlElement(name = "ai", type = Ai.class)
	private List<Ai> templates;
	private FastMap<Integer, AITemplate> aiTemplate = new FastMap<Integer, AITemplate>();

	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		aiTemplate.clear();
		for (Ai template : templates)
			aiTemplate.put(template.getNpcId(), new AITemplate(template));
	}

	public int size()
	{
		return aiTemplate.size();
	}

	public FastMap<Integer, AITemplate> getAiTemplate()
	{
		return aiTemplate;
	}
}
