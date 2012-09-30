package com.JnaniDev.Alliances.Managers;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Commands.AllianceCommand;
import com.JnaniDev.Locale.Locale;
import com.JnaniDev.Util.StringUtils;

public class CommandManager implements CommandExecutor {
	private Alliances plugin;
	private LinkedHashMap<String, AllianceCommand> commands = new LinkedHashMap<String, AllianceCommand>();
	
	public CommandManager(Alliances plugin) {
		this.plugin = plugin;
	}

	public void addCommand(AllianceCommand command) {
		for(String alias : command.aliases())
			commands.put(alias, command);
	}
	
	public void removeCommand(AllianceCommand command) {
		for(String alias : command.aliases())
			commands.remove(alias);
	}
	
	public void clearCommands() {
		commands.clear();
	}
	
	public Collection<AllianceCommand> getCommands() {
		return commands.values();
	}
	
	public void displayHelp(CommandSender sender, String commandLabel, int page, float maxCommands) {
		Collection<AllianceCommand> helpCommands = new LinkedList<AllianceCommand>();
		sender.sendMessage(StringUtils.parseColors(Locale.getString("Alliances.DefaultHeader", plugin.getDescription().getName())));
		sender.sendMessage(StringUtils.parseColors(Locale.getString("Alliances.DefaultArgumentUsage")));

		// Default to main page
		if(page > (int) Math.ceil(commands.size()/maxCommands) || page < 1)
			page = 1;
		
		// Add commands to command list
		int i = 0;
		for(AllianceCommand command : getCommands()) {
			if((helpCommands.size() < maxCommands) && (i >= page * maxCommands - maxCommands)) {
				if((sender.hasPermission(command.permission())) && !(helpCommands.contains(command)))
					helpCommands.add(command);
			}
			i++;
		}
		
		// Display commands and help
		for(AllianceCommand command : helpCommands)
			sender.sendMessage(Locale.getString("Alliances.DefaultUsage", commandLabel, command.usage(), command.description()));
		
		sender.sendMessage(Locale.getString("Alliances.ForMoreHelp", commandLabel, "[Page]"));
	}
	
	public int valueOf(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	public boolean commandExists(String cmd) {
		for(AllianceCommand command : commands.values()) {
			if(Arrays.asList(command.aliases()).contains(cmd))
				return true;
		}
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 0) {
			displayHelp(sender, commandLabel, 1, 5);
			return true;
		}
							
		if(((args.length > 1 ) && args[0].equalsIgnoreCase("help"))) {
			displayHelp(sender, commandLabel, valueOf(args[1]), 5);
			return true;
		}
				
		if(!(commandExists(args[0]))) {
			System.out.println("No cmd found");
			return false;
		}
				
		if((sender instanceof Player) && !(commands.get(args[0]).canRunAsPlayer())) {
			System.out.println("Cant be run as player");
			return false;
		}
		
		if((sender instanceof ConsoleCommandSender) && !(commands.get(args[0]).canRunAsConsole())) {
			System.out.println("Cant be run as console");
			return false;
		}
				
		if(!(sender.hasPermission(commands.get(args[0]).permission()))) {
			System.out.println("No permission");
			return false;
		}

		if(commands.get(args[0]).minLength() >= args.length ) {
			sender.sendMessage(Locale.getString("Alliances.DefaultUsage", commandLabel, commands.get(args[0]).usage(), commands.get(args[0]).description()));
			return false;
		}
		
		if((plugin.getConfig().getBoolean("economy.enabled")) && (plugin.getEconomy().getBalance(sender.getName()) < commands.get(args[0]).cost())) {
			sender.sendMessage(Locale.getString("Alliances.NotEnoughMoney", String.valueOf(commands.get(args[0]).cost())));
			return false;
		}
		
		// Executes sucessfully code:
		if(commands.get(args[0]).execute(sender, commandLabel, args)) {
			if(plugin.getConfig().getBoolean("economy.enabled")) {
				if(commands.get(args[0]).cost() > 0)
					plugin.getEconomy().withdrawPlayer(sender.getName(), commands.get(args[0]).cost());
				else
					plugin.getEconomy().depositPlayer(sender.getName(), -(commands.get(args[0]).cost()));
			}
			return true;
		}
		return false;
	}
}
