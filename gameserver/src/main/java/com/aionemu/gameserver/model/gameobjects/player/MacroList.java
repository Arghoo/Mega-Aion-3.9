package com.aionemu.gameserver.model.gameobjects.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * Player macros collection, contains all player macros.
 * <p/>
 * Created on: 13.07.2009 16:28:23
 *
 * @author Aquanox, nrg
 */
public class MacroList
{
	/**
	 * Class logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MacroList.class);

	/**
	 * Container of macros, position to xml.
	 */
	private final Map<Integer, String> macros;

	/**
	 * Creates an empty macro list
	 */
	public MacroList()
	{
		this.macros = new HashMap<Integer, String>(12);
	}

	/**
	 * Create new instance of <tt>MacroList</tt>.
	 *
	 * @param arg
	 */
	public MacroList(Map<Integer, String> arg)
	{
		this.macros = arg;
	}

	/**
	 * Returns map with all macros
	 *
	 * @return all macros
	 */
	public Map<Integer, String> getMacros()
	{
		return Collections.unmodifiableMap(macros);
	}

	/**
	 * Add macro to the collection.
	 *
	 * @param macroPosition Macro order.
	 * @param macroXML      Macro Xml contents.
	 * @return <tt>true</tt> if macro addition was successful, and it can be stored into database. Otherwise
	 * <tt>false</tt>.
	 */
	public synchronized boolean addMacro(int macroPosition, String macroXML)
	{
		if (macros.containsKey(macroPosition)) {
			macros.remove(macroPosition);
			macros.put(macroPosition, macroXML);
			return false;
		}

		macros.put(macroPosition, macroXML);
		return true;
	}

	/**
	 * Remove macro from the list.
	 *
	 * @param macroPosition
	 * @return <tt>true</tt> if macro deletion was successful, and changes can be stored into database. Otherwise
	 * <tt>false</tt>.
	 */
	public synchronized boolean removeMacro(int macroPosition)
	{
		String m = macros.remove(macroPosition);
		if (m == null)//
		{
			logger.warn("Trying to remove non existing macro.");
			return false;
		}
		return true;
	}

	/**
	 * Returns count of available macros.
	 *
	 * @return count of available macros.
	 */
	public int getSize()
	{
		return macros.size();
	}

	/**
	 * Returns an unmodifiable map of macro id to macro contents.
	 * NOTE: Retail sends only 7 macros per packet, that's why we have to split macros
	 */
	public Map<Integer, String> getMarcosPart(boolean secondPart)
	{
		Map<Integer, String> macrosPart = new HashMap<Integer, String>();
		int currentIndex = secondPart ? 7 : 0;
		int endIndex = secondPart ? 11 : 6;

		for (; currentIndex <= endIndex; currentIndex++) {
			macrosPart.put(currentIndex, macros.get(currentIndex));
		}
		return Collections.unmodifiableMap(macrosPart);
	}
}
