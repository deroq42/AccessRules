package de.deroq.accessrules.commands;

import de.deroq.accessrules.StatsAccessRules;
import de.deroq.accessrules.models.StatsUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand extends Command {

    private final StatsAccessRules statsAccessRules;

    public StatsCommand(String name, StatsAccessRules statsAccessRules) {
        super(name);
        this.statsAccessRules = statsAccessRules;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if(args[0].equalsIgnoreCase("getKills")) {
            statsAccessRules.getStatsManager().getStatsUser(player.getName(), StatsUser.AccessRule.KILLS).thenAcceptAsync(statsUser -> {
                player.sendMessage("kills: " + statsUser.getKills());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getDeaths")) {
            statsAccessRules.getStatsManager().getStatsUser(player.getName(), StatsUser.AccessRule.DEATHS).thenAcceptAsync(statsUser -> {
                player.sendMessage("deaths: " + statsUser.getDeaths());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getPoints")) {
            statsAccessRules.getStatsManager().getStatsUser(player.getName(), StatsUser.AccessRule.POINTS).thenAcceptAsync(statsUser -> {
                player.sendMessage("points: " + statsUser.getPoints());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getAll")) {
            statsAccessRules.getStatsManager().getStatsUser(player.getName(), StatsUser.AccessRule.ALL).thenAcceptAsync(statsUser -> {
                player.sendMessage(statsUser.toString());
            });

            return false;
        }

        return false;
    }
}
