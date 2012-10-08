package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliance;
import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;

public class PlayerCommands {
	@BaseCommand(aliases={"join", "enter"}, desc="Join an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public void join(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender.getName());
		Alliance alliance = plugin.getAllianceManager().getAlliance(args[1]);
		if(player == null) player = new AlliancePlayer();
		
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
				
		alliance.uninvite(sender.getName());
		player.setAlliance(plugin.getAllianceManager().getAllianceId(args[1]));
		sender.sendMessage("You have joined " + alliance.getName());
	}
	
	@BaseCommand(aliases={"leave", "enter"}, desc="Leave your Alliance.", usage="", min=1, allowConsole=false)
	public void leave(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
}
