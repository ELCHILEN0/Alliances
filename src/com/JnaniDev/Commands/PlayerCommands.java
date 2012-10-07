package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class PlayerCommands {
	@BaseCommand(aliases={"join", "enter"}, desc="Join an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public void join(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"leave", "enter"}, desc="Leave your Alliance.", usage="", min=1, allowConsole=false)
	public void leave(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
}
