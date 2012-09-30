package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;
import com.JnaniDev.Locale.Locale;

public class LeaveCmd implements AllianceCommand {
	private Alliances plugin;
	
	public LeaveCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(!(player.isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInAlliance"));
			return false;
		}		
		
		if(player.isOwner()) {
			sender.sendMessage(Locale.getString("Alliances.Commands.CannotLeaveAlliance"));
			return false;
//			plugin.getServer().broadcastMessage(Locale.getString("Alliances.Events.AllianceCrumbled", plugin.getAllianceManager().getAlliance(allianceId).getName()));
//			plugin.getAllianceManager().cleanAlliance(allianceId);
//			for(AlliancePlayer member : plugin.getPlayerManager().getPlayers()) {
//				if(member.getAlliance() == allianceId) {
//					member.setAlliance(0);
//					member.setRank(Rank.MEMBER.ordinal());
//				}
//			}
//			plugin.getPlayerManager().savePlayers();
//			plugin.getAllianceManager().saveAlliances();
		}		
		
		plugin.getAllianceManager().broadcastToAlliance(Locale.getString("Alliances.Events.PlayerHasLeftAlliance", sender.getName()), player.getAlliance());
		sender.sendMessage(Locale.getString("Alliances.Events.LeftAlliance", plugin.getAllianceManager().getAlliance(player.getAlliance()).getName()));
		player.setAlliance(0);
		player.setRank(Rank.MEMBER.ordinal());
		plugin.getPlayerManager().savePlayer(sender.getName());
		return true;
	}

	@Override
	public String[] aliases() {
		return new String[] {"leave"};
	}

	@Override
	public String permission() {
		return "alliances.commands.player.leave";
	}

	@Override
	public String usage() {
		return "leave";
	}

	@Override
	public String description() {
		return "Leave your alliance.";
	}

	@Override
	public int minLength() {
		return 0;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.leaveAllianceCost");
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
