package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

import com.JnaniDev.Alliances.Alliances;

public class TestCmd {
	@BaseCommand(aliases={"test"}, desc="A basic command!")
	public void executeTest(CommandSender sender, String commandLabel, String[] args, Alliances plugin) {
		sender.sendMessage("TEST IS BEING RUN!");
	}
}
