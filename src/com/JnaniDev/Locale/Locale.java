package com.JnaniDev.Locale;

import java.util.ResourceBundle;

import org.bukkit.ChatColor;

public abstract class Locale {
	public static String getString(String key) {
		return getString(key, new Object());
	}

	public static String getString(String key, Object... messageArguments)
	{	
		java.util.Locale locale = new java.util.Locale( "en", "US" );
		ResourceBundle resource = ResourceBundle.getBundle("com.JnaniDev.Locale.locale", locale);
		
		String output = resource.getString(key);

		if (messageArguments != null) {
			output = String.format(output, messageArguments);
		}

		return ChatColor.translateAlternateColorCodes("&".charAt(0), output);
	}
}
