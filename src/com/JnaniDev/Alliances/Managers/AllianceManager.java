package com.JnaniDev.Alliances.Managers;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.JnaniDev.Alliances.Alliance;
import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Util.ArrayUtil;
import com.JnaniDev.Util.Log;
import com.JnaniDev.Util.SQL;

public class AllianceManager {
	private Alliances plugin;
	private SQL database;
	private String alliancesTable;
	public Map<Integer, Alliance> alliances;
	
	public AllianceManager(Alliances plugin) {
		this.plugin = plugin;
		this.database = plugin.getDb();
		this.alliancesTable = plugin.getConfig().getString("storage.tablePrefix") + "alliances";
		alliances = new HashMap<Integer, Alliance>();
	}
	
	/**
	 * Get all the Alliances
	 */
	public Collection<Alliance> getAlliances() {
		return alliances.values();
	}
	
	/**
	 * Get all the Alliance ID's
	 */
	public Collection<Integer> getAllianceIDs() {
		return alliances.keySet();
	}
	
	/**
	 * Check if an Alliance exists
	 * @param id
	 * @param name
	 */
	public boolean allianceExists(int id) {
		return getAlliance(id) != null;
	}
	
	public boolean allianceExists(String name) {
		return getAlliance(name) != null;
	}
	
	/**
	 * Get an Alliance ID
	 * 
	 * @param name
	 */
	public int getAllianceId(String name) {
		for(int i : alliances.keySet()) {
			if(alliances.get(i).getName().equals(name)) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Get an Alliance by its ID
	 * @param id
	 */
	public Alliance getAlliance(int id) {
		return alliances.get(id);
	}
	
	/**
	 * Get an Alliance by its name
	 * @param name
	 */
	public Alliance getAlliance(String name) {
		for(Integer key : getAllianceIDs()) {
			if(getAlliance(key).getName().equals(name)) {
				return getAlliance(key);
			}
		}
		return null;
	}
	
	/**
	 * Gets player names in a specific Alliance
	 * @param id
	 */
	public Collection<String> getPlayerNames(int id) {
		Collection<String> names = Collections.emptySet();

		for(String name : plugin.getPlayerManager().getPlayerNames()) {
			if(plugin.getPlayerManager().getPlayer(name).getAlliance() == id) {
				names.add(name);
			}
		}
		return names;	
	}
	
	/**
	 * Gets AlliancePlayers in a specific Alliance
	 * @param id
	 */
	public Collection<AlliancePlayer> getPlayers(int id) {
		Collection<AlliancePlayer> players = Collections.emptySet();

		for(AlliancePlayer player : plugin.getPlayerManager().getPlayers()) {
			if(player.getAlliance() == id) {
				players.add(player);
			}
		}
		return players;	
	}
	
	/**
	 * Gets Players in a specific Alliance
	 * @param id
	 */
	public Collection<Player> getOnlinePlayers(int id) {
		Collection<Player> players = Collections.emptySet();
		
		for(Player player : plugin.getServer().getOnlinePlayers()) {
			if(getPlayerNames(id).contains(player.getName())) {
				players.add(player);
			}
		}
		
		return players;
	}
	
	/**
	 * Create an empty Alliance object
	 * 
	 * @return int
	 */
 	public int createAlliance(Alliance alliance) {
        alliances.put(alliances.size() + 1, alliance);
        return alliances.size() + 1;
	}
	
	/**
	 * Load all the Alliances
	 */
	public void loadAlliances() {
        Map<Integer, Alliance> alliances = new HashMap<Integer, Alliance>();
		String sql = "SELECT * FROM `?`";
		sql = sql.replaceFirst("\\?", alliancesTable);
		ResultSet result = database.query(sql);
		
		try {
			while (result.next()) {
		        Map<String, Object> alliance = new HashMap<String, Object>();
		        alliance.put("name", result.getString(2));
		        alliance.put("desc", result.getString(3));
		        alliance.put("partners", result.getString(4));
		        alliance.put("brothers", result.getString(5));
		        alliance.put("enemies", result.getString(6));
		        alliance.put("rivals", result.getString(7));
		        alliance.put("invited", ArrayUtil.asList(result.getString(8)));
		        alliance.put("lastLogin", result.getLong(9));
				alliances.put(result.getInt(1), new Alliance(alliance));
			}
			result.close();
		} catch (Exception e) { 
			System.out.println("Failed to load alliances.  Verify that your database settings are setup correctly.");
		}
		this.alliances = alliances;
	}
	
	/**
	 * Save all the Alliances
	 */
	public void saveAlliances() {
		for(int key : alliances.keySet()) {
			insertOrUpdate(key);
		}
	}
	
	/**
	 * Save a specific Alliance
	 * @param id
	 */
	public void saveAlliance(int id) {
		if(alliances.containsKey(id)) {
			insertOrUpdate(id);
		}
	}
	
	public void insertOrUpdate(int id) {
		String count = "SELECT COUNT(*) FROM `?` WHERE `?` = '?'";
		count = count.replaceFirst("\\?", alliancesTable);
		count = count.replaceFirst("\\?", "id");
		count = count.replaceFirst("\\?", String.valueOf(id));
		if(database.resultInt(database.query(count), 1) == 0)
			insertAlliance(id);
		else
			updateAlliance(id);
	}
	
	public void insertAlliance(int id) {
		Alliance alliance = alliances.get(id);
		String sql = "INSERT INTO `?` ( `?`, `?`, `?`, `?`, `?`, `?`, `?`, `?`, `?` ) VALUES ( '?', '?', '?', '?', '?', '?', '?', '?', '?' )";
		sql = sql.replaceFirst("\\?", alliancesTable);
		sql = sql.replaceFirst("\\?", "id");
		sql = sql.replaceFirst("\\?", "name");
		sql = sql.replaceFirst("\\?", "desc");
		sql = sql.replaceFirst("\\?", "partners");
		sql = sql.replaceFirst("\\?", "brothers");
		sql = sql.replaceFirst("\\?", "enemies");
		sql = sql.replaceFirst("\\?", "rivals");
		sql = sql.replaceFirst("\\?", "invited");
		sql = sql.replaceFirst("\\?", "lastLogin");
		sql = sql.replaceFirst("\\?", String.valueOf(id));
		sql = sql.replaceFirst("\\?", alliance.getName());
		sql = sql.replaceFirst("\\?", alliance.getDesc());
		sql = sql.replaceFirst("\\?", alliance.getPartners());
		sql = sql.replaceFirst("\\?", alliance.getBrothers());
		sql = sql.replaceFirst("\\?", alliance.getEnemies());
		sql = sql.replaceFirst("\\?", alliance.getRivals());
		sql = sql.replaceFirst("\\?", ArrayUtil.toString(alliance.getInvited()));
		sql = sql.replaceFirst("\\?", String.valueOf(alliance.getLastLogin()));
		database.query(sql);
	}
	
	public void updateAlliance(int id) {
		Alliance alliance = alliances.get(id);
		String sql = "UPDATE `?` SET `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?', `?` = '?' WHERE `?` = '?'";
		sql = sql.replaceFirst("\\?", alliancesTable);
		sql = sql.replaceFirst("\\?", "name");
		sql = sql.replaceFirst("\\?", alliance.getName());
		sql = sql.replaceFirst("\\?", "desc");
		sql = sql.replaceFirst("\\?", alliance.getDesc());
		sql = sql.replaceFirst("\\?", "partners");
		sql = sql.replaceFirst("\\?", "");
		sql = sql.replaceFirst("\\?", "brothers");
		sql = sql.replaceFirst("\\?", "");
		sql = sql.replaceFirst("\\?", "enemies");
		sql = sql.replaceFirst("\\?", "");
		sql = sql.replaceFirst("\\?", "rivals");
		sql = sql.replaceFirst("\\?", "");
		sql = sql.replaceFirst("\\?", "invited");
		sql = sql.replaceFirst("\\?", ArrayUtil.toString(alliance.getInvited()));
		sql = sql.replaceFirst("\\?", "lastLogin");
		sql = sql.replaceFirst("\\?", String.valueOf(alliance.getLastLogin()));
		sql = sql.replaceFirst("\\?", "id");
		sql = sql.replaceFirst("\\?", String.valueOf(id));
		database.query(sql);
	}

	/**
	 * Delete an Alliance
	 * @param id
	 */
	public void cleanAlliance(int id) {
		alliances.remove(id);
		String sql = "DELETE FROM `?` WHERE `?` = '?'";
		sql = sql.replaceFirst("\\?", alliancesTable);
		sql = sql.replaceFirst("\\?", "id");
		sql = sql.replaceFirst("\\?", String.valueOf(id));
		database.query(sql);
	}
	
}
