package com.JnaniDev.Alliances.Managers;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.JnaniDev.Alliances.Alliance;
import com.JnaniDev.Alliances.AlliancePlayer;
import com.JnaniDev.Alliances.Alliances;
import com.JnaniDev.Util.ArrayUtil;
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
	
	public void broadcastToAlliance(String message, String alliance) {
		for(String name : plugin.getPlayerManager().players.keySet()) {
			if(plugin.getPlayerManager().getPlayer(name).getAlliance() == getAllianceId(alliance)) {
				if((Bukkit.getPlayerExact(name) != null) && (Bukkit.getPlayerExact(name).isOnline()))
					Bukkit.getPlayerExact(name).sendMessage(message);
			}
		}
	}
	
	public void broadcastToAlliance(String message, int alliance) {
		for(String name : plugin.getPlayerManager().players.keySet()) {
			if(plugin.getPlayerManager().getPlayer(name).getAlliance() == alliance) {
				if((Bukkit.getPlayerExact(name) != null) && (Bukkit.getPlayerExact(name).isOnline()))
					Bukkit.getPlayerExact(name).sendMessage(message);
			}
		}
	}
	
	public Alliance getAlliance(String name) {
		for(Integer key : alliances.keySet()) {
			if(alliances.get(key).getName().equalsIgnoreCase(name))
				return alliances.get(key);
		}
		return null;
	}
	
	public Alliance getAlliance(Integer id) {
		return alliances.get(id);
	}
	
	public int getAllianceId(String name) {
		for(Integer key : alliances.keySet()) {
			if(alliances.get(key).getName().equalsIgnoreCase(name))
				return key;
		}
		return 0;
	}
	
	public Collection<AlliancePlayer> getPlayers(int id) {
		Collection<AlliancePlayer> players = Collections.emptySet();
		for(String name : plugin.getPlayerManager().players.keySet()) {
			if(plugin.getPlayerManager().getPlayer(name).getAlliance() == id) {
				players.add(plugin.getPlayerManager().getPlayer(name));
			}
		}
		return players;
	}
	
	public boolean allianceExists(String name) {
		return (getAlliance(name) != null) ? true : false;
	}
	
	public void createAlliance(String name) {
        Map<String, Object> alliance = new HashMap<String, Object>();
        alliance.put("name", name);
        alliance.put("desc", "");
        alliance.put("partners", "");
        alliance.put("brothers", "");
        alliance.put("enemies", "");
        alliance.put("rivals", "");
        alliance.put("invited", ArrayUtil.asList(""));
        alliance.put("lastLogin", System.currentTimeMillis());
        alliances.put(alliances.size() + 1, new Alliance(alliance));
	}
	
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
	
	public void saveAlliances() {
		for(Integer key : alliances.keySet()) {
			insertOrUpdate(key);
		}
	}
	
	public void insertOrUpdate(Integer id) {
		String count = "SELECT COUNT(*) FROM `?` WHERE `?` = '?'";
		count = count.replaceFirst("\\?", alliancesTable);
		count = count.replaceFirst("\\?", "id");
		count = count.replaceFirst("\\?", String.valueOf(id));
		if(database.resultInt(database.query(count), 1) == 0)
			insertAlliance(id);
		else
			updateAlliance(id);
	}
	
	public void insertAlliance(Integer id) {
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
	
	public void updateAlliance(Integer id) {
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

	public void cleanAlliance(Integer id) {
		alliances.remove(id);
		String sql = "DELETE FROM `?` WHERE `?` = '?'";
		sql = sql.replaceFirst("\\?", alliancesTable);
		sql = sql.replaceFirst("\\?", "id");
		sql = sql.replaceFirst("\\?", String.valueOf(id));
		database.query(sql);
	}
	
}
