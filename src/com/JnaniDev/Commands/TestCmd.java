package com.JnaniDev.Commands;

import org.bukkit.command.CommandSender;

public class TestCmd {
	@BaseCommand(aliases={"test"}, desc="A basic command!")
	public void executeTest(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage("TEST IS BEING RUN!");
	}
}