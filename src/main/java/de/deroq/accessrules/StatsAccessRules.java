package de.deroq.accessrules;

import de.deroq.accessrules.commands.StatsCommand;
import de.deroq.accessrules.database.StatsAccessRulesDatabase;
import de.deroq.accessrules.listeners.PlayerJoinListener;
import de.deroq.accessrules.managers.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author deroq
 * @since 01.05.2022
 */

public class StatsAccessRules extends JavaPlugin  {

    private StatsAccessRulesDatabase statsAccessRulesDatabase;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        initDatabase();
        initManagers();
        registerListeners();
        registerCommands();

        getLogger().info("StatsAccessRules has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("StatsAccessRules has been disabled.");
    }

    private void initDatabase() {
        this.statsAccessRulesDatabase = new StatsAccessRulesDatabase(this);
    }

    private void initManagers() {
        this.statsManager = new StatsManager(this);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void registerCommands() {
        ((CraftServer) Bukkit.getServer()).getCommandMap().register("stats", new StatsCommand("stats", this));
    }

    public StatsAccessRulesDatabase getStatsAccessRulesDatabase() {
        return statsAccessRulesDatabase;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
