package com.JnaniDev.Alliances;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlliancePlayer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int kills;
	private int deaths;
	private List<String> friends;
	private int alliance;
	private int rank;
	private long lastLogin;
	
	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public List<String> getFriends() {
		return friends;
	}

	public int getAlliance() {
		return alliance;
	}

	public int getRank() {
		return rank;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void addKill() {
		this.kills++;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void addDeath() {
		this.deaths++;
	}
	
	public int getKD() {
		return kills/deaths;
	}

	public void setFriends(List<String> list) {
		this.friends = list;
	}
	
	public void addFriend(String friend) {
		friends.add(friend);
	}
	
	public void removeFriend(String friend) {
		friends.remove(friend);
	}
	
	public boolean isFriend(String player) {
		return friends.contains(player);
	}

	public void setAlliance(int alliance) {
		this.alliance = alliance;
	}
	
	public boolean isInAlliance() {
		return (getAlliance() != 0) ? true : false;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank.ordinal();
	}
	
	public boolean isMember() {
		return (getRank() == Rank.MEMBER.ordinal()) ? true : false;
	}
	
	public boolean isModerator() {
		return (getRank() == Rank.MODERATOR.ordinal()) ? true : false;
	}
	
	public boolean isAdmin() {
		return (getRank() == Rank.ADMIN.ordinal()) ? true : false;
	}
	
	public boolean isOwner() {
		return (getRank() == Rank.OWNER.ordinal()) ? true : false;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public void updateLogin() {
		this.lastLogin = System.currentTimeMillis();
	}

	public Map<String, Object> serialize() {
		Map<String, Object> player = new HashMap<String, Object>();
		player.put("kills", kills);
		player.put("deaths", deaths);
		player.put("friends", friends);
		player.put("alliance", alliance);
		player.put("rank", rank);
		player.put("lastLogin", lastLogin);
		return player;
	}
	
	@SuppressWarnings("unchecked")
	public AlliancePlayer(Map<String, Object> map) {
		this.kills = (Integer) map.get("kills");
		this.deaths = (Integer) map.get("deaths");
		this.friends = (List<String>) map.get("friends");
		this.alliance = (Integer) map.get("alliance");
		this.rank = (Integer) map.get("rank");
		this.lastLogin = (Long) map.get("lastLogin");
	}
	
	public AlliancePlayer() {
		this.kills = 0;
		this.deaths = 0;
		this.friends = Arrays.asList("");
		this.alliance = 0;
		this.rank = 0;
		this.lastLogin = System.currentTimeMillis();
	}
}