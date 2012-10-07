package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public class AllianceCommands {
	
	@BaseCommand(aliases={"create"}, desc="Create an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public boolean create(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"disband"}, desc="Disband your Alliance.", min=1, allowConsole=false)
	public boolean disband(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"transfer"}, desc="Transfer ownership to another Player.", usage="<Player>", min=2, allowConsole=false)
	public boolean transfer(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"desc", "description"}, desc="Transfer ownership to another Player.", usage="<Description>", min=2, allowConsole=false)
	public boolean desc(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"invite"}, desc="Invite a player to your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public boolean invite(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"uninvite"}, desc="UnInvite a player to your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public boolean uninvite(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"kick"}, desc="Kick a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public boolean kick(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
	
	@BaseCommand(aliases={"ban"}, desc="Ban a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public boolean ban(CommandSender sender, String commandLabel, String[] args) {

		return false;
	}
}
