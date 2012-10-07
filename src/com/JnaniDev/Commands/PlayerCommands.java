package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public class PlayerCommands {
	@BaseCommand(aliases={"join", "enter"}, desc="Join an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public boolean join(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"leave", "enter"}, desc="Leave your Alliance.", usage="", min=1, allowConsole=false)
	public boolean leave(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
}
