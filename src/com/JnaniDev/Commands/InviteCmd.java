package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public class InviteCmd {

	@BaseCommand(aliases={"invite"}, desc="Invite a player to your Alliance.", usage="<Player>", min=2)
	public boolean invite(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
}
