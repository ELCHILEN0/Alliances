package com.JnaniDev.Listeners;

import org.bukkit.event.Listener;

public class EntityListener implements Listener {

//	@EventHandler(ignoreCancelled=true)
//	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
//		if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
//			APlayer attacker = new APlayer(((Player) event.getDamager()).getName());
//			APlayer defender = new APlayer(((Player) event.getEntity()).getName());
//			if(attacker.getAlliance() == defender.getAlliance()) {
//				attacker.sendMessage("You cannot hurt players that are in your Alliance!");
//				event.setCancelled(true);
//			}
//			
//			if(defender.isChunkOwned()) {
//				attacker.sendMessage("You cannot damage a player in claimed land!");
//				event.setCancelled(true);
//			}
//		}
//	}
	
}
