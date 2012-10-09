package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliance;
import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;

public class PlayerCommands {
	@BaseCommand(aliases={"join", "enter"}, desc="Join an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public void join(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		Alliance alliance = plugin.getAllianceManager().getAlliance(args[1]);
		
		if(alliance == null) {
			sender.sendMessage("The specified Alliance doesnt exist!");
			return;
		}
		
		if(!(alliance.isInvited(sender.getName()))) {
			sender.sendMessage("You are not invited to this Alliance!");
			return;
		}
		
		if(player.isInAlliance()) {
			sender.sendMessage("You must first leave your current Alliance!");
			return;
		}
				
		for(Player p : plugin.getAllianceManager().getOnlinePlayers(plugin.getAllianceManager().getAllianceId(args[1])))
			p.sendMessage(sender.getName() + " has joined your Alliance!");
		sender.sendMessage("You have joined " + alliance.getName());

		player.setAlliance(plugin.getAllianceManager().getAllianceId(args[1]));
		alliance.uninvite(sender.getName());

	}
	
	@BaseCommand(aliases={"leave", "enter"}, desc="Leave your Alliance.", usage="", min=1, allowConsole=false)
	public void leave(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		Alliance alliance = plugin.getAllianceManager().getAlliance(player.getAlliance());
		int id = player.getAlliance();
		
		if(!(player.isInAlliance())) {
			sender.sendMessage("You are not in any Alliance!");
			return;
		}
		
		if(player.isOwner()) {
			sender.sendMessage("You cannot leave your Alliance!");
			return;
		}
				
		player.setAlliance(0);
		player.setRank(Rank.MEMBER.ordinal());
		alliance.uninvite(sender.getName());
		
		sender.sendMessage("You have left " + alliance.getName());
		for(Player p : plugin.getAllianceManager().getOnlinePlayers(id))
			p.sendMessage(sender.getName() + " has left your Alliance!");
		
	}
}
