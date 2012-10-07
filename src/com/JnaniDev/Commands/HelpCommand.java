package com.JnaniDev.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Util.Log;

public class HelpCommand {
	
	@BaseCommand(aliases={"help", "?"}, desc="Display help for Alliances.", usage="[Page]")
	public void help(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		for(BaseCommand command : plugin.getCommandManager().getBaseCommands()) {
			Log.info(command.aliases()[0]);
		}
	}
	
}
