package com.JnaniDev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;
import com.JnaniDev.Locale.Locale;

public class KickCmd implements AllianceCommand {
	private Alliances plugin;
	
	public KickCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(!(player.isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInAlliance"));
			return false;
		}		
		
		if(sender.getName().equals(args[1])) {
			sender.sendMessage(Locale.getString("Alliances.Commands.CannotLeaveAlliance"));
			return false;
		}	
		
		if(!(plugin.getPlayerManager().getPlayer(args[1]).isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.PlayerNotInAlliance"));
			return false;
		}
		
		if(!(plugin.getPlayerManager().getPlayer(args[1]).getAlliance() == player.getAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.PlayerNotInYourAlliance"));
			return false;
		}
		
		if(player.getRank() < plugin.getPlayerManager().getPlayer(args[1]).getRank()) {
			sender.sendMessage(Locale.getString("Alliances.NoPermission"));
			return false;
		}
		
		plugin.getPlayerManager().getPlayer(args[1]).setAlliance(0);
		plugin.getPlayerManager().getPlayer(args[1]).setRank(Rank.MEMBER.ordinal());
		if(Bukkit.getPlayer(args[1]) != null)
			Bukkit.getPlayer(args[1]).sendMessage(Locale.getString("Alliances.Events.Kicked", plugin.getAllianceManager().getAlliance(player.getAlliance()).getName()));
		
		plugin.getAllianceManager().broadcastToAlliance(Locale.getString("Alliances.Events.PlayerKicked", args[1]), player.getAlliance());
		plugin.getPlayerManager().savePlayer(args[1]);
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"kick"};
	}

	@Override
	public String permission() {
		return "alliances.commands.player.kick";
	}

	@Override
	public String usage() {
		return "kick <Player>";
	}

	@Override
	public String description() {
		return "Leave your alliance.";
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.kickCost");
	}

	@Override
	public boolean canRunAsPlayer() {
		return true;
	}

	@Override
	public boolean canRunAsConsole() {
		return false;
	}
}
