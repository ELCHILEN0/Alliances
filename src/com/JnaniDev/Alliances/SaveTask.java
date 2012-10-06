package com.JnaniDev.Alliances;

public class SaveTask implements Runnable {
	private Alliances plugin;
	
	public SaveTask(Alliances plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.getPlayerManager().savePlayers();
		plugin.getAllianceManager().saveAlliances();
	}

}
