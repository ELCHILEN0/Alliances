package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;
import com.JnaniDev.Locale.Locale;

public class CreateCmd implements AllianceCommand {
	private Alliances plugin;
	
	public CreateCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(player.isInAlliance()) {
			sender.sendMessage(Locale.getString("Alliances.Commands.MustLeaveAlliance"));
			return false;
		}
		
		if(plugin.getAllianceManager().allianceExists(args[1])) {
			sender.sendMessage(Locale.getString("Alliances.Commands.AllianceExists"));
			return false;
		}
		
		plugin.getAllianceManager().createAlliance(args[1]);
		player.setAlliance(plugin.getAllianceManager().getAllianceId(args[1]));
		player.setRank(Rank.OWNER.ordinal());
		sender.sendMessage(Locale.getString("Alliances.Events.JoinedAlliance", plugin.getAllianceManager().getAlliance(args[1]).getName()));
		plugin.getPlayerManager().savePlayer(sender.getName());
		plugin.getAllianceManager().saveAlliances();
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"create"};
	}

	@Override
	public String permission() {
		return "alliances.commands.player.create";
	}

	@Override
	public String usage() {
		return "create <Name>";
	}

	@Override
	public String description() {
		return "Create an alliance.";
	}

	@Override
	public int minLength() {
		return 1;
	}
	
	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.createAllianceCost");
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
