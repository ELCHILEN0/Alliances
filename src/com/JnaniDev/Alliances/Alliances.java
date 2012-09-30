package com.JnaniDev.Alliances;

import java.io.File;
import java.util.logging.Logger;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.JnaniDev.Alliances.Managers.AllianceManager;
import com.JnaniDev.Alliances.Managers.CommandManager;
import com.JnaniDev.Alliances.Managers.PlayerManager;
import com.JnaniDev.Commands.AdminRankCmd;
import com.JnaniDev.Commands.CreateCmd;
import com.JnaniDev.Commands.DescCmd;
import com.JnaniDev.Commands.DisbandCmd;
import com.JnaniDev.Commands.InviteCmd;
import com.JnaniDev.Commands.JoinCmd;
import com.JnaniDev.Commands.KickCmd;
import com.JnaniDev.Commands.LeaveCmd;
import com.JnaniDev.Commands.ModRankCmd;
import com.JnaniDev.Commands.UninviteCmd;
import com.JnaniDev.Listeners.BlockListener;
import com.JnaniDev.Listeners.EntityListener;
import com.JnaniDev.Listeners.PlayerListener;
import com.JnaniDev.Util.SQL;

public class Alliances extends JavaPlugin {
	public final Logger log = Bukkit.getLogger();
	public PluginDescriptionFile desc;
	private AllianceManager allianceManager;
	private PlayerManager playerManager;
	private CommandManager commandManager;
	private Economy economy;
	public static Vault vault = null;
	public SQL database;

	public void onEnable() {
		long beginTime = System.currentTimeMillis();
		
		desc = getDescription();
		reloadConfiguration();
		
		// Setup Custom Managers
		PluginManager pm = getServer().getPluginManager();
		CheckVault(pm);
		allianceManager = new AllianceManager(this);
		playerManager = new PlayerManager(this);
		commandManager = new CommandManager(this);
		if (vault != null) {
		setupEconomy();
		}		
		// Setup HashMaps from SQL
		// Players format is <PlayerName, PlayerObject>
		// Alliances format is <AllianceIndex, AllianceObject>
		playerManager.loadPlayers();
		allianceManager.loadAlliances();
		registerCmd();
		
		// Setup Event Listeners
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new EntityListener(), this);
		
		// Setup save running every 1 minute
		getServer().getScheduler().cancelTasks(this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(this), 20 * 20L, 20 * 20L);
		
		// Setup Executors
		getCommand("alliance").setExecutor(commandManager);
		
	    long endTime = System.currentTimeMillis();
	    log.info(desc.getName() + " version " + desc.getVersion() + " is enabled! Took " + (endTime - beginTime) + "ms.");
    }

	public void onDisable() {
    	playerManager.savePlayers();
    	allianceManager.saveAlliances();
    	commandManager.clearCommands();
        log.info( desc.getName() + " version " + desc.getVersion() + " is disabled!" );
    }
    
	public void reloadConfiguration() {		
		// Configuration and database loading code
	    if (!new File(getDataFolder(), "config.yml").exists())
	        saveDefaultConfig();
	    		
		reloadConfig();
	    checkDatabase();
	}
	
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

	
	public SQL getDb() {
		return database;
	}
	
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
	public void registerCmd() {
		commandManager.addCommand(new JoinCmd(this));
		commandManager.addCommand(new AdminRankCmd(this));
		commandManager.addCommand(new ModRankCmd(this));
		commandManager.addCommand(new LeaveCmd(this));
		commandManager.addCommand(new CreateCmd(this));
		commandManager.addCommand(new DescCmd(this));
		commandManager.addCommand(new InviteCmd(this));
		commandManager.addCommand(new UninviteCmd(this));
		commandManager.addCommand(new KickCmd(this));
		commandManager.addCommand(new DisbandCmd(this));		
	}
	
	private void CheckVault(PluginManager pm) {
        Plugin hc = pm.getPlugin("Vault");
        if (hc != null) {
        	vault = (Vault)hc;
        	log.info("Vault found, enabling Economy!");
        }
        else if (hc == null) {
        log.info("Vault not found, disabling Economy!");
        }
	}
	
	public AllianceManager getAllianceManager() {
		return allianceManager;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public Economy getEconomy() {
		return economy;
	}
}
