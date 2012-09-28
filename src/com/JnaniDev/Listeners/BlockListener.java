package com.JnaniDev.Listeners;

import org.bukkit.event.Listener;

public class BlockListener implements Listener{
	
//	@EventHandler(ignoreCancelled=true)
//	public void onBlockPlace(BlockPlaceEvent event) {
//		AlliancePlayer player = Alliances.getPlayerManager().getPlayer(event.getPlayer().getName());
//		APlayer player = new APlayer(event.getPlayer().getName());
//		if(!player.getName().equals(Alliances.getChunkManager().getOwner(event.getPlayer().getLocation().getChunk().getX(), event.getPlayer().getLocation().getChunk().getZ()))) {
//			event.getPlayer().sendMessage("This chunk is claimed by another player.");
//			event.setCancelled(true);
//		}
//	}
//	
//	@EventHandler(ignoreCancelled=true)
//	public void onBlockBreak(BlockBreakEvent event) {
//		AlliancePlayer player = Alliances.getPlayerManager().getPlayer(event.getPlayer().getName());
//		if(!player.getName().equals(Alliances.getChunkManager().getOwner(event.getPlayer().getLocation().getChunk().getX(), 
//																		 event.getPlayer().getLocation().getChunk().getZ()))) {
//			event.getPlayer().sendMessage("This chunk is claimed by another player.");
//			event.setCancelled(true);
//		}
//	}
}
