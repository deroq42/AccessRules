package de.deroq.accessrules.listeners;

import de.deroq.accessrules.StatsAccessRules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author deroq
 * @since 01.05.2022
 */

public class PlayerJoinListener implements Listener {

    private final StatsAccessRules statsAccessRules;

    public PlayerJoinListener(StatsAccessRules statsAccessRules) {
        this.statsAccessRules = statsAccessRules;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        statsAccessRules.getStatsManager().createStatsUser(player.getUniqueId().toString(), player.getName());
    }
}
