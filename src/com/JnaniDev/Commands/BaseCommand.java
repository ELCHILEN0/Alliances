package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public interface BaseCommand {
	
	void execute(CommandSender sender, String commandLabel, String[] args);
	
	boolean validate(CommandSender sender, String commandLabel, String[] args);
		
	int minArgs();
	
	int maxArgs();
	
	double cost();
	
	boolean allowPlayerExecution();
	
	boolean allowConsoleExecution();
	
}
