package com.JnaniDev.Util;

import java.util.logging.Logger;

public class Log {
	private static final Logger log = Logger.getLogger("Minecraft");

	/**
	 * Send an info level log message to console
	 * @param msg message to send
	 */
	public static void info(String msg) {
		log.info("[Alliances] " + msg);
	}
	
	/**
	 * Send a warn level log message to console
	 * @param msg message to send
	 */
	public static void warning(String msg) {
		log.warning("[Alliances] " + msg);
	}
	
	/**
	 * Send a severe level log message to console
	 * @param msg message to send
	 */
	public static void severe(String msg) {
		log.severe("[Alliances] " + msg);
	}
}
