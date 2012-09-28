package com.JnaniDev.Alliances;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Alliance implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	private String partners;
	private String brothers;
	private String enemies;
	private String rivals;
	private List<String> invited;
	private Long lastLogin;
	
	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public List<String> getInvited() {
		return invited;
	}
	
	public Long getLastLogin() {
		return lastLogin;
	}
	
	// TODO: Partners
	public String getPartners() {
		return partners;
	}
	
	// TODO: Brothers
	public String getBrothers() {
		return brothers;
	}
	
	// TODO: Enemies
	public String getEnemies() {
		return enemies;
	}
	
	// TODO: Rivals
	public String getRivals() {
		return rivals;
	}
	
	public boolean isInvited(String player) {
		return invited.contains(player);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	// TODO: Partners
	public void setPartners(String partners) {
		this.partners = partners;
	}
	
	// TODO: Brothers
	public void setBrothers(String brothers) {
		this.brothers = brothers;
	}
	
	// TODO: Enemies
	public void setEnemies(String enemies) {
		this.enemies = enemies;
	}
	
	// TODO: Rivals
	public void setRivals(String rivals) {
		this.rivals = rivals;
	}
	
	public void setInvited(List<String> invited) {
		this.invited = invited;
	}
	
	public void invite(String player) {
		invited.add(player);
	}
	
	public void uninvite(String player) {
		invited.remove(player);
	}
	
	public void setLastLogin(Long lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	@SuppressWarnings("unchecked")
	public Alliance(Map<String, Object> map) {
		this.name = (String) map.get("name");
		this.desc = (String) map.get("desc");
		this.partners = (String) map.get("partners");
		this.brothers = (String) map.get("brothers");
		this.enemies = (String) map.get("enemies");
		this.rivals = (String) map.get("rivals");
		this.invited = (List<String>) map.get("invited");
		this.lastLogin = (Long) map.get("lastLogin");
	}
	
	public Alliance() {
		this.name = "";
		this.desc = "";
		this.partners = "";
		this.brothers = "";
		this.enemies = "";
		this.rivals = "";
		this.invited = Arrays.asList("");
		this.lastLogin = System.currentTimeMillis();
	}
	
}
