/**
 * This file is part of aion-emu <aion-emu.com>.
 * <p>
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network;

/**
 * This Exception will be thrown when <code>Crypt</code> setKey method will be called more than one time.
 *
 * @author -Nemesiss-
 */
@SuppressWarnings("serial")
public class KeyAlreadySetException extends RuntimeException
{
	/**
	 * Constructs an <code>KeyAlreadySetException</code> with no detail message.
	 */
	public KeyAlreadySetException()
	{
		super();
	}

	/**
	 * Constructs an <code>KeyAlreadySetException</code> with the specified detail message.
	 *
	 * @param s
	 *          the detail message.
	 */
	public KeyAlreadySetException(String s)
	{
		super(s);
	}

	/**
	 * Creates new error
	 *
	 * @param message
	 *          exception description
	 * @param cause
	 *          reason of this exception
	 */
	public KeyAlreadySetException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates new error
	 *
	 * @param cause
	 *          reason of this exception
	 */
	public KeyAlreadySetException(Throwable cause)
	{
		super(cause);
	}
}
