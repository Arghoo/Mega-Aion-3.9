package com.aionemu.gameserver.model.templates.mail;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailPart")
@XmlSeeAlso({ Sender.class, Header.class, Body.class, Tail.class, Title.class })
public abstract class MailPart extends StringParamList implements IMailFormatter
{
	@XmlAttribute(name = "id")
	protected Integer id;

	@Override
	public MailPartType getType()
	{
		return MailPartType.CUSTOM;
	}

	public Integer getId()
	{
		return id;
	}

	public String getFormattedString(IMailFormatter customFormatter)
	{
		IMailFormatter formatter = this;
		if (customFormatter != null) {
			formatter = customFormatter;
		}

		String result = getFormattedString(getType());

		String[] paramValues = new String[getParam().size()];
		for (int i = 0; i < getParam().size(); i++) {
			Param param = getParam().get(i);
			paramValues[i] = formatter.getParamValue(param.getId());
		}
		String joinedParams = String.join(",", paramValues);
		if (result.isEmpty())
			return joinedParams;
		else if (!joinedParams.isEmpty())
			result += "," + joinedParams;

		return result;
	}

	public String getFormattedString(MailPartType partType)
	{
		String result = "";
		if (id > 0)
			result += id.toString();
		return result;
	}
}
