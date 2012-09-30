package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;
import com.JnaniDev.Locale.Locale;

public class DisbandCmd implements AllianceCommand {
	private Alliances plugin;
	
	public DisbandCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(!(player.isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInAlliance"));
			return false;
		}
		
		if(!(player.isOwner())) {
			sender.sendMessage(Locale.getString("Alliances.NoPermission"));
			return false;
		}
		
		plugin.getServer().broadcastMessage(Locale.getString("Alliances.Events.AllianceCrumbled", plugin.getAllianceManager().getAlliance(player.getAlliance()).getName()));
		
		for(AlliancePlayer member : plugin.getAllianceManager().getPlayers(player.getAlliance())) {
			member.setAlliance(0);
			member.setRank(Rank.MEMBER.ordinal());
		}
		
		player.setAlliance(0);
		player.setRank(Rank.MEMBER.ordinal());
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"disband"};
	}

	@Override
	public String permission() {
		return "alliances.commands.alliances.disband";
	}

	@Override
	public String usage() {
		return "disband";
	}

	@Override
	public String description() {
		return "Disbands your alliance.";
	}

	@Override
	public int minLength() {
		return 0;
	}
	
	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.disbandAllianceCost");
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
