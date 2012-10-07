package com.JnaniDev.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class HelpCommand {
	
	@BaseCommand(aliases={"help", "?"}, desc="Display help for Alliances.", usage="[Page]")
	public void help(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		sender.sendMessage(ChatColor.DARK_RED + "<>---------------[" + ChatColor.GOLD + "Alliances" + ChatColor.DARK_RED + "]--------------<>");
		sender.sendMessage(ChatColor.GRAY + "Required: < > Optional: [ ]");
		for(BaseCommand command : plugin.getCommandManager().getBaseCommands()) {
			String commandUsage = "%s- %s/%s %s %s";
			String commandInfo = "%s - %s";
			commandUsage = String.format(commandUsage, ChatColor.DARK_RED, ChatColor.GOLD,commandLabel, command.aliases()[0], command.usage());
			commandInfo = String.format(commandInfo, ChatColor.DARK_RED, command.desc());
			sender.sendMessage(commandUsage.trim() + commandInfo);
		}
	}
	
}
