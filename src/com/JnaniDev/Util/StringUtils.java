package com.JnaniDev.Util;

import org.bukkit.ChatColor;

public class StringUtils {
	public static String parseColors(String string) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), string);
	}
	
	public static String stringFromArray(String[] array, int startIndex) {
		StringBuilder string = new StringBuilder();
		for(int i=0; i<array.length; i++) {
			if(i >= startIndex) {
				string.append(array[i] + " ");
			}
		}

		return string.toString().trim();
	}
}
