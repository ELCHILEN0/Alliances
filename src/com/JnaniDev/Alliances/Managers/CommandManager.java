package com.JnaniDev.Alliances.Managers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Commands.BaseCommand;

public class CommandManager implements CommandExecutor {
	private Alliances plugin;
	private Map<String, Method> commands = new HashMap<String, Method>();
	
	public CommandManager(Alliances plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Register all commands in a class
	 * 
	 * @param commandClass
	 */
	public void registerClass(Class<?> commandClass) {
		// Loop through all the classes methods
        Method[] methods = commandClass.getMethods();
        for (Method method : methods) {
        	// Check if BaseCommand is part of the method
            if (method.isAnnotationPresent(BaseCommand.class)) {
                BaseCommand base = method.getAnnotation(BaseCommand.class);

                // Put the commands into the map
        		for(String alias : base.aliases()) {
        			commands.put(alias, method);
        		}
            }
        }
	}
	
	/**
	 * Unregister all commands
	 */
	public void unregisterCommands() {
		commands.clear();
	}
	
	/**
	 * Invoke a command
	 * 
	 * @param command
	 * @param object
	 */
	public void dispatchCommand(String command, Object... object) {
		try {
			commands.get(command).invoke(commands.get(command).getDeclaringClass().newInstance(), object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get BaseCommand from a specified string
	 * 
	 * @return BaseCommand or NULL
	 */
	public BaseCommand getBaseCommand(String command) {
        if (commands.get(command).isAnnotationPresent(BaseCommand.class))
            return commands.get(command).getAnnotation(BaseCommand.class);
        return null;
	}
	
		
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 0) {
			sender.sendMessage("Display info for plugin here!");
			return false;
		}
		
		if(!(commands.containsKey(args[0]))) {
			sender.sendMessage("The specified command was not found!");
			return false;
		}
		
		BaseCommand command = this.getBaseCommand(args[0]);
		
		if((sender instanceof Player) && !(command.allowPlayer())) {
			sender.sendMessage("This command cannot be run as a player!");
			return false;
		}
		
		if((sender instanceof ConsoleCommandSender) && !(command.allowConsole())) {
			sender.sendMessage("This command cannot be run from the console!");
			return false;
		}

		if((args.length < command.min()) || (args.length > command.max() && command.max() != -1)) {
			sender.sendMessage(command.desc());
			sender.sendMessage("/" + commandLabel + " " + command.aliases()[0] + " " + command.usage());
			return false;
		}
		
		dispatchCommand(args[0], sender, commandLabel, args);
		return false;
	}
}
