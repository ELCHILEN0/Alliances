package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class AllianceCommands {
	
	@BaseCommand(aliases={"create"}, desc="Create an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public void create(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"disband"}, desc="Disband your Alliance.", min=1, allowConsole=false)
	public void disband(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"transfer"}, desc="Transfer ownership to another Player.", usage="<Player>", min=2, allowConsole=false)
	public void transfer(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"desc", "description"}, desc="Transfer ownership to another Player.", usage="<Description>", min=2, allowConsole=false)
	public void desc(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"invite"}, desc="Invite a player to your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void invite(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"uninvite"}, desc="UnInvite a player to your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void uninvite(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"kick"}, desc="Kick a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void kick(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"ban"}, desc="Ban a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void ban(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
}
