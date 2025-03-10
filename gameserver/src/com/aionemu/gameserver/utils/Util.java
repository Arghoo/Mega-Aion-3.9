/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.utils;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.configs.main.NameConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author -Nemesiss-
 */
public class Util {

	/**
	 * @param s
	 */
	public static void printSection(String s) {
		s = "[ " + s + " ]";

		while (s.length() < 79)
			s = "=" + s + "=";

		System.out.println(s);
	}

	public static void printProgressBarHeader(int size) {
		StringBuilder header = new StringBuilder("0%[");
		for (int i = 0; i < size; i++) {
			header.append("-");
		}
		header.append("]100%");
		System.out.println(header);
		System.out.print("   ");
	}

	public static void printCurrentProgress() {
		System.out.print("+");
	}

	public static void printEndProgress() {
		System.out.print(" Done. \n");
	}

	/**
	 * Convert data from given ByteBuffer to hex
	 * 
	 * @param data
	 * @return hex
	 */
	public static String toHex(ByteBuffer data) {
		StringBuilder result = new StringBuilder();
		int counter = 0;
		int b;
		while (data.hasRemaining()) {
			if (counter % 16 == 0)
				result.append(String.format("%04X: ", counter));

			b = data.get() & 0xff;
			result.append(String.format("%02X ", b));

			counter++;
			if (counter % 16 == 0) {
				result.append("  ");
				toText(data, result, 16);
				result.append("\n");
			}
		}
		int rest = counter % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}
			toText(data, result, rest);
		}
		return result.toString();
	}

	/**
	 * Gets last <tt>cnt</tt> read bytes from the <tt>data</tt> buffer and puts into <tt>result</tt> buffer in special
	 * format:
	 * <ul>
	 * <li>if byte represents char from partition 0x1F to 0x80 (which are normal ascii chars) then it's put into buffer as
	 * it is</li>
	 * <li>otherwise dot is put into buffer</li>
	 * </ul>
	 * 
	 * @param data
	 * @param result
	 * @param cnt
	 */
	private static void toText(ByteBuffer data, StringBuilder result, int cnt) {
		int charPos = data.position() - cnt;
		for (int a = 0; a < cnt; a++) {
			int c = data.get(charPos++);
			if (c > 0x1f && c < 0x80)
				result.append((char) c);
			else
				result.append('.');
		}
	}

	/**
	 * Converts name to valid pattern For example : "atracer" -> "Atracer"
	 * 
	 * @param name
	 * @return String
	 */
	public static String convertName(String name) {
		if (!name.isEmpty()) {
			if(NameConfig.ALLOW_CUSTOM_NAMES) return name;
			else {
				int index = name.lastIndexOf(" ");
				if (index == -1) return name.substring(0, 1).toUpperCase() + name.toLowerCase().substring(1);
				return name.substring(index + 1, index + 2).toUpperCase() + name.toLowerCase().substring(index + 2);
			}
		}
		else
			return "";
	}

	public static String formatNameWithTags(Player player) {
		String nameFormat = "%s";
		// orphaned players - later find/remove them
		if (player.getClientConnection() != null) {
			if (MembershipConfig.MEMBERSHIP_TAG_ENABLED) {
				switch (player.getClientConnection().getAccount().getMembership()) {
					case 1:
						nameFormat = MembershipConfig.MEMBERSHIP_TAG_PREMIUM;
						break;
					case 2:
						nameFormat = MembershipConfig.MEMBERSHIP_TAG_VIP;
						break;
					default: 
						break;
				}
			}
			if (AdminConfig.CUSTOMTAG_ENABLE) {
				switch (player.getClientConnection().getAccount().getAccessLevel()) {
					case 1:
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS1;
						break;
					case 2:
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS2;
						break;
					case 3:
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS3;
						break;
					case 4:
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS4;
						break;
					case 5:
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS5;
						break;
				}
			}
		}
		return String.format(nameFormat, player.getName());
	}
}
