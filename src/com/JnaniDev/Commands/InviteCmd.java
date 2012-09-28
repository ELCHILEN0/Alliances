package com.JnaniDev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Locale.Locale;

public class InviteCmd implements AllianceCommand {
	private Alliances plugin;
	
	public InviteCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(!(player.isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInAlliance"));
			return false;
		}
		
		if(player.isMember()) {
			sender.sendMessage(Locale.getString("Alliances.NoPermission"));
			return false;
		}
		
		if(plugin.getAllianceManager().getAlliance(player.getAlliance()).isInvited(args[1])) {
			sender.sendMessage(Locale.getString("Alliances.Commands.PlayerAlreadyInvited"));
			return false;
		}
						
		sender.sendMessage(Locale.getString("Alliances.Events.InviteSent"));
		if(Bukkit.getPlayer(args[1]) != null)
			Bukkit.getPlayer(args[1]).sendMessage(Locale.getString("Alliances.Events.Invited", plugin.getAllianceManager().getAlliance(player.getAlliance()).getName()));
		plugin.getAllianceManager().getAlliance(player.getAlliance()).invite(args[1]);
		plugin.getAllianceManager().saveAlliances();
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"invite"};
	}

	@Override
	public String permission() {
		return "alliances.commands.alliances.invite";
	}

	@Override
	public String usage() {
		return "invite <Player>";
	}

	@Override
	public String description() {
		return "Invite a player.";
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.inviteCost");
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
