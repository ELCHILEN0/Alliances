package com.JnaniDev.Alliances;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.JnaniDev.Alliances.Managers.AllianceManager;
import com.JnaniDev.Alliances.Managers.CommandManager;
import com.JnaniDev.Alliances.Managers.PlayerManager;
import com.JnaniDev.Commands.AllianceCommands;
import com.JnaniDev.Commands.HelpCommand;
import com.JnaniDev.Commands.PlayerCommands;
import com.JnaniDev.Commands.TestCmd;
import com.JnaniDev.Listeners.BlockListener;
import com.JnaniDev.Listeners.EntityListener;
import com.JnaniDev.Listeners.PlayerListener;
import com.JnaniDev.Util.Log;
import com.JnaniDev.Util.SQL;

public class Alliances extends JavaPlugin {
	private PluginDescriptionFile desc;
	private AllianceManager allianceManager;
	private PlayerManager playerManager;
	private CommandManager commandManager;
	private SQL database;

	public void onEnable() {
		long beginTime = System.currentTimeMillis();
		
		desc = getDescription();
		reloadConfiguration();
	    checkDatabase();
		
		// Setup Custom Managers
		allianceManager = new AllianceManager(this);
		playerManager = new PlayerManager(this);
		commandManager = new CommandManager(this);
		
		// Setup HashMaps from SQL
		// Players format is <PlayerName, PlayerObject>
		// Alliances format is <AllianceIndex, AllianceObject>
		playerManager.loadPlayers();
		allianceManager.loadAlliances();	
		
		// Setup save running every 1 minute
		getServer().getScheduler().cancelTasks(this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(this), 20 * 20L, 20 * 20L);
		
		// Setup Executors
		registerCommands();
		
	    long endTime = System.currentTimeMillis();
	    Log.info(desc.getName() + " version " + desc.getVersion() + " is enabled! Took " + (endTime - beginTime) + "ms.");
    }

	public void onDisable() {
    	playerManager.savePlayers();
    	allianceManager.saveAlliances();
        Log.info(desc.getName() + " version " + desc.getVersion() + " is disabled!");
    }
    
	/**
	 * Reload the configuration
	 * If the file isn't found the file is created
	 * Then the default configuration is written and reloaded
	 */
	public void reloadConfiguration() {		
		// Configuration and database loading code
	    if (!new File(getDataFolder(), "config.yml").exists())
	        saveDefaultConfig();
	    		
		reloadConfig();
	}
	
	
	
	/**
	 * Registers the EventHandlers
	 */
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new EntityListener(), this);
	}
	
	/**
	 * Registers the Commands
	 */
	public void registerCommands() {
		getCommand("alliance").setExecutor(commandManager);
		commandManager.registerClass(TestCmd.class);
		commandManager.registerClass(AllianceCommands.class);
		commandManager.registerClass(PlayerCommands.class);
		commandManager.registerClass(HelpCommand.class);
	}
	
	
	/**
	 * Gets the Database and its connection
	 * 
	 * @return the SQL database
	 */
	public SQL getDb() {
		return database;
	}
	
	/** 
	 * Checks the database preparing the connection
	 * Then creating any tables that need to be created
	 */
	public void checkDatabase() {
		database = null;
		if(getConfig().getString("storage.type").equalsIgnoreCase("mysql")) {
			database = new SQL(this,
					getConfig().getString("storage.host") + ":" + getConfig().getString("storage.port"),
					getConfig().getString("storage.database"),
					getConfig().getString("storage.username"),
					getConfig().getString("storage.password"));
		} else {
			database = new SQL(this, getDataFolder() + File.separator + "data.dat");
		}
		
		
		// Create players database
		database.query("CREATE TABLE IF NOT EXISTS "
				+ getConfig().getString("storage.tablePrefix") + "players" + " (" 
				+ "`player` varchar(16) NOT NULL ,"
				+ "`kills` INT NULL DEFAULT 0 ,"
				+ "`deaths` INT NULL DEFAULT 0 ,"
				+ "`friends` TEXT NULL ,"
				+ "`alliance` INT NULL ,"
				+ "`rank` INT NULL DEFAULT 0 ,"
				+ "`lastLogin` MEDIUMTEXT NULL"
				+ ")");
		
		// Create regions database
		database.query("CREATE TABLE IF NOT EXISTS "
				+ getConfig().getString("storage.tablePrefix") + "regions" + " ("
				+ "`owner` TEXT NOT NULL ,"
				+ "`x` INT NOT NULL ," 
				+ "`z` INT NOT NULL )");
		
		// Create alliances database
		database.query("CREATE TABLE IF NOT EXISTS "
				+ getConfig().getString("storage.tablePrefix") + "alliances" + " (" 
				+ "`id` INT NOT NULL ," 
				+ "`name` TEXT NOT NULL ," 
				+ "`desc` TEXT NULL ," 
				+ "`partners` TEXT NULL ," 
				+ "`brothers` TEXT NULL ," 
				+ "`enemies` TEXT NULL ," 
				+ "`rivals` TEXT NULL ," 
				+ "`invited` TEXT NULL ," 
				+ "`lastLogin` MEDIUMTEXT NULL"
				+ " )");
		
	}
	
	/**
	 * Gets the AllianceManager
	 * 
	 * @return AllianceManager
	 */
	public AllianceManager getAllianceManager() {
		return allianceManager;
	}
	
	/**
	 * Gets the PlayerManager
	 * 
	 * @return PlayerManager
	 */
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	/**
	 * Gets the CommandManager
	 * 
	 * @return CommandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}
}
