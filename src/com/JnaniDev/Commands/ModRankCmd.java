package com.JnaniDev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;

public class ModRankCmd implements AllianceCommand {
	private Alliances plugin;
	
	public ModRankCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {	
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);	
		if (Bukkit.getPlayer(args[1]) == null) {
			sender.sendMessage("Player does not exist!");
			return true;
		}
		if(!(plugin.getPlayerManager().getPlayer(args[1]).isInAlliance())) {
			sender.sendMessage(Bukkit.getPlayer(args[1]).getDisplayName() + " isn't part of an Alliance");
			return true;
		}
		
		if(plugin.getPlayerManager().getPlayer(args[1]).getRank() < Rank.ADMIN.ordinal()) {
			sender.sendMessage("You must be the Alliance owner or an admin to perform this command!");
			return true;
		}
		
		if(!(plugin.getPlayerManager().getPlayer(args[1]).getAlliance() == player.getAlliance() && (plugin.getPlayerManager().getPlayer(args[1]).isInAlliance()))) {
			sender.sendMessage(Bukkit.getPlayer(args[1]).getDisplayName() + " is currently in " + plugin.getAllianceManager().getAlliance(player.getAlliance()).getName());
			return true;
		}

		if (Bukkit.getPlayer(args[1]) != null) {
		plugin.getPlayerManager().getPlayer(args[1]).setRank(Rank.ADMIN.ordinal());
		sender.sendMessage(Bukkit.getPlayer(args[1]).getDisplayName() + " has been promoted to a Moderator!");
		return true;
	}
		return true;
	}


	@Override
	public String[] aliases() {
		return new String[] {"mod"};
	}

	@Override
	public String permission() {
		return "alliances.commands.player.addmod";
	}

	@Override
	public String usage() {
		return "mod <Player>";
	}

	@Override
	public String description() {
		return "Set a players rank";
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public boolean canRunAsPlayer() {
		return true;
	}

	@Override
	public boolean canRunAsConsole() {
		return false;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.joinAllianceCost");
	}
}