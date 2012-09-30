package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Locale.Locale;

public class JoinCmd implements AllianceCommand {
	private Alliances plugin;
	
	public JoinCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(player.isInAlliance()) {
			sender.sendMessage(Locale.getString("Alliances.Commands.MustLeaveAlliance"));
			return false;
		}
		
		if(!(plugin.getAllianceManager().allianceExists(args[1]))) {
			sender.sendMessage(Locale.getString("Alliances.Commands.AllianceDoesntExist"));
			return false;
		}
		
		if(!(plugin.getAllianceManager().getAlliance(args[1]).isInvited(sender.getName()))) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInvitedToAlliance", args[1]));
			return false;
		}
		
		plugin.getAllianceManager().broadcastToAlliance(Locale.getString("Alliances.Events.PlayerHasJoinedAlliance", sender.getName()), args[1]);
		sender.sendMessage(Locale.getString("Alliances.Events.JoinedAlliance", plugin.getAllianceManager().getAlliance(args[1]).getName()));
		player.setAlliance(plugin.getAllianceManager().getAllianceId(args[1]));
		plugin.getAllianceManager().getAlliance(args[1]).uninvite(sender.getName());
		plugin.getPlayerManager().savePlayer(sender.getName());
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"join"};
	}

	@Override
	public String permission() {
		return "alliances.commands.player.join";
	}

	@Override
	public String usage() {
		return "join <Alliance>";
	}

	@Override
	public String description() {
		return "Join an Alliance.";
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.joinAllianceCost");
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
