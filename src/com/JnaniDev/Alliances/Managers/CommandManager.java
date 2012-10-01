package com.JnaniDev.Alliances.Managers;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Commands.BaseCommand;

public class CommandManager implements CommandExecutor {
	private Alliances plugin;
	private LinkedHashMap<String, BaseCommand> commands = new LinkedHashMap<String, BaseCommand>();
	
	public CommandManager(Alliances plugin) {
		this.plugin = plugin;
	}

	public void addCommand(String name, BaseCommand command) {
		commands.put(name, command);
	}
	
	public void removeCommand(String name) {
		commands.remove(name);
	}
	
	public void clearCommands() {
		commands.clear();
	}
	
	public Collection<BaseCommand> getCommands() {
		return commands.values();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 0) {
			// Display help
			return false;
		}
		
		if(!(commands.containsKey(args[0]))) {
			// No command found
			return false;
		}
		
		// The command can now be safely grabbed from the arguments
		BaseCommand command = commands.get(args[0]);
		
		// Player Execution Check
		if((sender instanceof Player) && !(command.allowPlayerExecution())) {
			// Can't be run as player
			return false;
		}
		
		// Console Execution Check
		if((sender instanceof ConsoleCommandSender) && !(command.allowConsoleExecution())) {
			// Can't be run as console
			return false;
		}

		// Argument Length Check
		if((command.minArgs() >= args.length)) {
			// Display usage
			return false;
		}
		
		// TODO: Update for any economy changes.  Convert config check to a solid variable check.
		if((plugin.getConfig().getBoolean("economy.enabled")) && (plugin.getEconomy().getBalance(sender.getName()) < command.cost())) {
			// Not enough money
			return false;
		}
		
		// This runs custom validation and checks that you provide.
		if(!(command.validate(sender, commandLabel, args))) return false;
		
		// This runs the economy code.
		if(plugin.getConfig().getBoolean("economy.enabled")) {
			if(commands.get(args[0]).cost() > 0)
				plugin.getEconomy().withdrawPlayer(sender.getName(), commands.get(args[0]).cost());
			else
				plugin.getEconomy().depositPlayer(sender.getName(), -(commands.get(args[0]).cost()));
		}

		command.execute(sender, commandLabel, args);
		return true;	
	}
}
