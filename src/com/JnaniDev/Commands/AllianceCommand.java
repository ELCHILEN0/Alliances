package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public interface AllianceCommand {
	boolean execute(CommandSender sender, String commandLabel, String[] args);
	
	String[] aliases();
			
	String permission();
	
	String usage();
	
	String description();
	
	double cost();
		
	int minLength();
			
	boolean canRunAsPlayer();
	
	boolean canRunAsConsole();
}
