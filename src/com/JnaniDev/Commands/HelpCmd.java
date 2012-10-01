package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class HelpCmd implements BaseCommand {
	private Alliances plugin;
	
	public HelpCmd(Alliances plugin) {
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String commandLabel, String[] args) {

	}

	@Override
	public boolean validate(CommandSender sender, String commandLabel, String[] args) {
		return true;
	}

	@Override
	public int minArgs() {
		return 0;
	}

	@Override
	public int maxArgs() {
		return 1;
	}

	@Override
	public double cost() {
		return 0;
	}

	@Override
	public boolean allowPlayerExecution() {
		return true;
	}

	@Override
	public boolean allowConsoleExecution() {
		return true;
	}
}
