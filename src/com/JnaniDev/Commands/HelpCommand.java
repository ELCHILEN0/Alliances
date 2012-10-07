package com.JnaniDev.Commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class HelpCommand {

	@BaseCommand(aliases={"help", "?"}, desc="Display help for Alliances.", usage="[Page]", hidden=true)
	public void help(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		ArrayList<String> content = new ArrayList<String>();
		ArrayList<String> header = new ArrayList<String>();
		ArrayList<String> footer = new ArrayList<String>();
		header.add(ChatColor.DARK_RED + "<>---------------[" + ChatColor.GOLD + "Alliances" + ChatColor.DARK_RED + "]--------------<>");
		header.add(ChatColor.GRAY + "Required: < > Optional: [ ]");
		footer.add(ChatColor.GRAY + "For more help to " + ChatColor.GOLD + "/am ? [Page]");
		footer.add(ChatColor.GRAY + "For more information on a command do " + ChatColor.GOLD + "/am info [Command]");

		for(BaseCommand command : plugin.getCommandManager().getBaseCommands()) {
			if(command.hidden()) continue;
			String commandUsage = ChatColor.DARK_RED + "- " + ChatColor.GOLD + "/" + commandLabel + " " + command.aliases()[0] + " " + command.usage();
			content.add(commandUsage);
		}

		if(args.length > 1 && isInteger(args[1]))
			sender.sendMessage(paginate(header, content, footer, 9, Integer.valueOf(args[1])));
		else
			sender.sendMessage(paginate(header, content, footer, 9, 0));
	}

	@BaseCommand(aliases={"info", "information"}, desc="Display additional information for a command.", usage="<Command>", min=2,hidden=true)
	public void info(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		ArrayList<String> message = new ArrayList<String>();
		message.add(ChatColor.DARK_RED + "<>---------------[" + ChatColor.GOLD + "Alliances" + ChatColor.DARK_RED + "]--------------<>");
		message.add(ChatColor.GRAY + "Required: < > Optional: [ ]");

		for(BaseCommand command : plugin.getCommandManager().getBaseCommands()) {
			boolean matches = false;
			if(command.hidden()) continue;
			for(String alias : command.aliases()) {
				if(alias.startsWith(args[1])) {
					matches = true;
				}
			}
			if(matches == true) {
				message.add(ChatColor.DARK_RED + "- " + ChatColor.GOLD + "/" + commandLabel + " " + command.aliases()[0] + " " + command.usage());
				message.add(ChatColor.DARK_RED + "   Aliases: " + ChatColor.GOLD + Arrays.asList(command.aliases()));
				message.add(ChatColor.DARK_RED + "   Description: " + ChatColor.GOLD + command.desc());
			}
		}

		sender.sendMessage(message.toArray(new String[message.size()]));
	}

	public boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String[] paginate(ArrayList<String> header, ArrayList<String> content, ArrayList<String> footer, int maxSize, int page) {
		ArrayList<String> message = new ArrayList<String>();
		maxSize = maxSize - footer.size();
		int startPage = page * maxSize;
		if(startPage > content.size()) startPage = 0;
		
		message.addAll(header);
		for (int i = startPage; i < content.size(); i++) {
			if(message.size() < maxSize)
				message.add(content.get(i));
		}
		message.addAll(footer);

		return message.toArray(new String[message.size()]);

	}

}
