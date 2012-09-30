package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Alliances.Rank;
import com.JnaniDev.Locale.Locale;
import com.JnaniDev.Util.StringUtils;

public class DescCmd implements AllianceCommand {
	private Alliances plugin;
	
	public DescCmd(Alliances plugin) {
		this.plugin = plugin;
	}
	
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {		
		AlliancePlayer player = plugin.getPlayerManager().getPlayer(sender);		
		if(!(player.isInAlliance())) {
			sender.sendMessage(Locale.getString("Alliances.Commands.NotInAlliance"));
			return false;
		}
		
		if(player.getRank() < Rank.ADMIN.ordinal()) {
			sender.sendMessage(Locale.getString("Alliances.NoPermission"));
			return false;
		}
		
		plugin.getAllianceManager().getAlliance(player.getAlliance()).setDesc(StringUtils.stringFromArray(args, 1));
		sender.sendMessage(Locale.getString("Alliances.Events.DescriptionChangedTo", StringUtils.stringFromArray(args, 1)));
		return true;
	}
	
	@Override
	public String[] aliases() {
		return new String[] {"desc", "description"};
	}

	@Override
	public String permission() {
		return "alliances.commands.alliance.description";
	}

	@Override
	public String usage() {
		return "desc <Description>";
	}

	@Override
	public String description() {
		return "Set your Alliance description.";
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public double cost() {
		return plugin.getConfig().getDouble("economy.setAllianceDescCost");
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
