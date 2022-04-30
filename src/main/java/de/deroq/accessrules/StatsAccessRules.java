package de.deroq.accessrules;

import de.deroq.accessrules.commands.StatsCommand;
import de.deroq.accessrules.database.DatabaseManager;
import de.deroq.accessrules.listeners.PlayerJoinListener;
import de.deroq.accessrules.managers.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class StatsAccessRules extends JavaPlugin  {

    private DatabaseManager databaseManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        makeInstances();
        registerEvents();
        registerCommands();

        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled.");
    }

    private void makeInstances() {
        this.databaseManager = new DatabaseManager();
        this.statsManager = new StatsManager(this);
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void registerCommands() {
        ((CraftServer) Bukkit.getServer()).getCommandMap().register("stats", new StatsCommand("stats", this));
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
