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
            statsAccessRules.getStatsManager().getAsyncStatsUserByName(player.getName(), StatsUser.AccessRule.KILLS).thenAcceptAsync(statsUserObject -> {
                player.sendMessage("kills: " + statsUserObject.getKills());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getDeaths")) {
            statsAccessRules.getStatsManager().getAsyncStatsUserByName(player.getName(), StatsUser.AccessRule.DEATHS).thenAcceptAsync(statsUserObject -> {
                player.sendMessage("deaths: " + statsUserObject.getDeaths());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getPoints")) {
            statsAccessRules.getStatsManager().getAsyncStatsUserByName(player.getName(), StatsUser.AccessRule.POINTS).thenAcceptAsync(statsUserObject -> {
                player.sendMessage("points: " + statsUserObject.getPoints());
            });

            return false;
        }

        if(args[0].equalsIgnoreCase("getAll")) {
            statsAccessRules.getStatsManager().getAsyncStatsUserByName(player.getName(), StatsUser.AccessRule.ALL).thenAcceptAsync(statsUserObject -> {
                player.sendMessage(statsUserObject.toString());
            });

            return false;
        }

        return false;
    }
}
