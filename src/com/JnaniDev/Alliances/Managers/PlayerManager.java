package com.JnaniDev.Alliances.Managers;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jooq.Result;

import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Util.ArrayUtil;
import com.JnaniDev.Util.SQL;

public class PlayerManager {
	private String playersTable;
	private SQL database;
	public Map<String, AlliancePlayer> players;

	public PlayerManager(Alliances plugin) {
		this.database = plugin.getDb();
		this.playersTable = plugin.getConfig().getString("storage.tablePrefix") + "players";
	}
	
	/**
	 * Get all the player names
	 */
	public Collection<String> getPlayerNames() {
		return players.keySet();
	}
	
	/**
	 * Get all the AlliancePlayers
	 */
	public Collection<AlliancePlayer> getPlayers() {
		return players.values();
	}
	
	/**
	 * Get a specific AlliancePlayer
	 * @param name
	 */
	public AlliancePlayer getPlayer(String name) {
		return players.get(name);
	}
	
	/**
	 * Get a specific AlliancePlayer
	 * @param player
	 */
	public AlliancePlayer getPlayer(Player player) {
		return players.get(player.getName());
	}
	
	/**
	 * Get a specific AlliancePlayer
	 * @param sender
	 */
	public AlliancePlayer getPlayer(CommandSender sender) {
		return players.get(sender.getName());
	}
	
	/**
	 * Load the players from the database
	 */
	public void loadPlayers() {
        Map<String, AlliancePlayer> players = new HashMap<String, AlliancePlayer>();
		String sql = "SELECT * FROM `?`";
		sql = sql.replaceFirst("\\?", playersTable);
		ResultSet result = database.query(sql);
		
		try {
			while (result.next()) {
		        Map<String, Object> player = new HashMap<String, Object>();
		        player.put("kills", result.getInt(2));
		        player.put("deaths", result.getInt(3));
		        player.put("friends", ArrayUtil.asList(result.getString(4)));
		        player.put("alliance", result.getInt(5));
		        player.put("rank", result.getInt(6));
		        player.put("lastLogin", result.getLong(7));
				players.put(result.getString(1), new AlliancePlayer(player));
			}
			result.close();
		} catch (Exception e) { 
			System.out.println("Failed to load players.  Verify that your database settings are setup correctly.");
		}
		
		this.players = players;
	}
	
	/**
	 * Save the players to the database
	 */
	public void savePlayers() {
		for(String key : players.keySet()) {
			insertOrUpdate(key);
		}
	}
	
	/**
	 * Save a player to the database
	 * @param name
	 */
	public void savePlayer(String name) {
		if(players.containsKey(name))
			insertOrUpdate(name);
	}
	
	public void insertOrUpdate(String name) {
		String count = "SELECT COUNT(*) FROM `?` WHERE `?` = '?'";
		count = count.replaceFirst("\\?", playersTable);
		count = count.replaceFirst("\\?", "player");
		count = count.replaceFirst("\\?", name);
		if(database.resultInt(database.query(count), 1) == 0)
			insertPlayer(name);
		else
			updatePlayer(name);
	}
	
	public void insertPlayer(String name) {
		AlliancePlayer player = players.get(name);
		String sql = "INSERT INTO `?` ( `?`, `?`, `?`, `?`, `?`, `?`, `?` ) VALUES ( '?', '?', '?', '?', '?', '?', '?' )";
		sql = sql.replaceFirst("\\?", playersTable);
		sql = sql.replaceFirst("\\?", "player");
		sql = sql.replaceFirst("\\?", "kills");
		sql = sql.replaceFirst("\\?", "deaths");
		sql = sql.replaceFirst("\\?", "friends");
		sql = sql.replaceFirst("\\?", "alliance");
		sql = sql.replaceFirst("\\?", "rank");
		sql = sql.replaceFirst("\\?", "lastLogin");
		sql = sql.replaceFirst("\\?", name);
		sql = sql.replaceFirst("\\?", String.valueOf(player.getKills()));
		sql = sql.replaceFirst("\\?", String.valueOf(player.getDeaths()));
		sql = sql.replaceFirst("\\?", ArrayUtil.toString(player.getFriends()));
		sql = sql.replaceFirst("\\?", String.valueOf(player.getAlliance()));
		sql = sql.replaceFirst("\\?", String.valueOf(player.getRank()));
		sql = sql.replaceFirst("\\?", String.valueOf(player.getLastLogin()));
		database.query(sql);
	}
	
	public void updatePlayer(String name) {
		AlliancePlayer player = players.get(name);
		String sql = "UPDATE `?` SET `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?' WHERE `?` = '?'";
		sql = sql.replaceFirst("\\?", playersTable);
		sql = sql.replaceFirst("\\?", "kills");
		sql = sql.replaceFirst("\\?", String.valueOf(player.getKills()));
		sql = sql.replaceFirst("\\?", "deaths");
		sql = sql.replaceFirst("\\?", String.valueOf(player.getDeaths()));
		sql = sql.replaceFirst("\\?", "friends");
		sql = sql.replaceFirst("\\?", ArrayUtil.toString(player.getFriends()));
		sql = sql.replaceFirst("\\?", "alliance");
		sql = sql.replaceFirst("\\?", String.valueOf(player.getAlliance()));
		sql = sql.replaceFirst("\\?", "rank");
		sql = sql.replaceFirst("\\?", String.valueOf(player.getRank()));
		sql = sql.replaceFirst("\\?", "lastLogin");
		sql = sql.replaceFirst("\\?", String.valueOf(player.getLastLogin()));
		sql = sql.replaceFirst("\\?", "player");
		sql = sql.replaceFirst("\\?", name);
		database.query(sql);
	}

	/**
	 * Delete a player
	 */
	public void cleanPlayer(String name) {
		String sql = "DELETE FROM `?` WHERE `?` = '?'";
		sql = sql.replaceFirst("\\?", playersTable);
		sql = sql.replaceFirst("\\?", "player");
		sql = sql.replaceFirst("\\?", name);
		database.query(sql);
	}

}
