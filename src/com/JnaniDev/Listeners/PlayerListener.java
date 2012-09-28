package com.JnaniDev.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;

public class PlayerListener implements Listener {
	private Alliances plugin;
	
	
	public PlayerListener(Alliances plugin) {
		this.plugin = plugin;
	}

	// Sets and updates player tables when they join.
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String player = event.getPlayer().getName();
		if (!(plugin.getPlayerManager().players.containsKey(player))) {
			plugin.getPlayerManager().players.put(player, new AlliancePlayer());
		}
		plugin.getPlayerManager().players.get(player).updateLogin();
		plugin.getPlayerManager().savePlayer(player);
	}
	
	// Sets and updates player tables when they leave.
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		String player = event.getPlayer().getName();
		if (!(plugin.getPlayerManager().players.containsKey(player))) {
			plugin.getPlayerManager().players.put(player, new AlliancePlayer());
		}
		plugin.getPlayerManager().players.get(player).updateLogin();
		plugin.getPlayerManager().savePlayer(player);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
//		int playerId = APlayer.getId(event.getPlayer().getName());

	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
//		int playerId = APlayer.getId(event.getPlayer().getName());

	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent event) {
//		Chunk chunk = event.getPlayer().getLocation().getChunk();
//		System.out.println(APlayer.getPlayer(ARegion.getOwner(ARegion.getId(chunk.getX(), chunk.getZ()))));
	}
	
}
