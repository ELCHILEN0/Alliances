package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public class HelpCommand {
	
	@BaseCommand(aliases={"help", "?"}, desc="Display help for Alliances.", usage="[Page]")
	public void help(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage("HELP!");
	}
	
}
