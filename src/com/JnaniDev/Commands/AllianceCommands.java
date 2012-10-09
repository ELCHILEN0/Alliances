package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliance;
import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;

public class AllianceCommands {
	
	@BaseCommand(aliases={"create"}, desc="Create an Alliance.", usage="<Name>", min=2, allowConsole=false)
	public void create(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		
		if(player.isInAlliance()) {
			sender.sendMessage("You must first leave your current Alliance.");
			return;
		}
		
		if(plugin.getAllianceManager().allianceExists(plugin.getAllianceManager().getID(args[1]))) {
			sender.sendMessage("An Alliance already exists with this name!");
			return;
		}
		
		Alliance alliance = new Alliance();
		alliance.setName(args[1]);
		
		plugin.getAllianceManager().createAlliance(alliance);
		sender.sendMessage("You have created " + alliance.getName() + "!");
		
		player.setAlliance(plugin.getAllianceManager().getID(args[1]));
		player.setRank(Rank.OWNER.ordinal());
	}
	
	@BaseCommand(aliases={"disband"}, desc="Disband your Alliance.", min=1, allowConsole=false)
	public void disband(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		int id = player.getAlliance();
		
		if(!(player.isInAlliance())) {
			sender.sendMessage("You are not in any Alliance!");
			return;
		}
		
		if(!(player.isOwner())) {
			sender.sendMessage("Only owners can disband an Alliance!");
			return;
		}
		
		for(Player p : plugin.getAllianceManager().getOnlinePlayers(id)) {
			p.sendMessage(plugin.getAllianceManager().getAlliance(player.getAlliance()).getName() + " has been disbanded!");
		}
		
		for(AlliancePlayer p : plugin.getAllianceManager().getAlliancePlayers(id)) {
			p.setAlliance(0);
		}
		
		plugin.getAllianceManager().cleanAlliance(id);
	}
	
	@BaseCommand(aliases={"transfer"}, desc="Transfer ownership to another Player.", usage="<Player>", min=2, allowConsole=false)
	public void transfer(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		int id = player.getAlliance();
		
		if(!(player.isInAlliance())) {
			sender.sendMessage("You are not in any Alliance!");
			return;
		}
		
		if(!(player.isOwner())) {
			sender.sendMessage("Only owners can transfer their Alliance!");
			return;
		}
		
		Player targetPlayer = plugin.getServer().getPlayer(args[1]);
		
		if(targetPlayer == null) {
			sender.sendMessage("The specified player is not online.");
			return;
		}
		
		AlliancePlayer target = plugin.getPlayerManager().getPlayer(targetPlayer);
		
		if(!(target.isInAlliance()) || (target.getAlliance() != player.getAlliance())) {
			sender.sendMessage("The specified player is not in your Alliance.");
			return;
		}
		
		if(targetPlayer == (Player) sender) {
			sender.sendMessage("You are already owner!");
			return;
		}
		
		for(Player p : plugin.getAllianceManager().getOnlinePlayers(id)) {
			p.sendMessage(plugin.getAllianceManager().getAlliance(player.getAlliance()).getName() + " is now owned by " + targetPlayer.getName() + "!");
		}
		
		target.setRank(Rank.OWNER.ordinal());
		player.setRank(Rank.ADMIN.ordinal());
		sender.sendMessage("You are now Admin!");
		targetPlayer.sendMessage("You are now Owner!");
	}
	
	@BaseCommand(aliases={"desc", "description"}, desc="Set your Alliance description.", usage="<Description>", min=2, allowConsole=false)
	public void desc(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);
		int id = player.getAlliance();
		
		if(!(player.isInAlliance())) {
			sender.sendMessage("You are not in any Alliance!");
			return;
		}
		
		if(player.getRank() < Rank.ADMIN.ordinal()) {
			sender.sendMessage("Only admins+ can set the Alliance description!");
			return;
		}
		
		String desc = new String();
		for(int i = 1; i < args.length; i++) {
			desc += args[i] + " ";
		}
		desc = desc.trim();
		
		plugin.getAllianceManager().getAlliance(id).setDesc(desc);
		
		sender.sendMessage("Changed description to: \"" + desc + "\"");
	}
	
	@BaseCommand(aliases={"invite"}, desc="Invite a player to your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void invite(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"uninvite"}, desc="Uninvite a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void uninvite(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"kick"}, desc="Kick a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void kick(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"ban"}, desc="Ban a player from your Alliance.", usage="<Player>", min=2, allowConsole=false)
	public void ban(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {

	}
	
	@BaseCommand(aliases={"sethome"}, desc="Sets your Alliance home.", min=1, allowConsole=false)
	public void setHome(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		
	}
}
