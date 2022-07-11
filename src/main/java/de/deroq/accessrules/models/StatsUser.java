package de.deroq.accessrules.models;

/**
 * @author deroq
 * @since 01.05.2022
 */

public class StatsUser {

    private final String uuid;
    private final String name;
    private int points;
    private int kills;
    private int deaths;

    private StatsUser(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return (deaths == 0 ? 1 : deaths);
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public static StatsUser create(String uuid, String name) {
        return new StatsUser(uuid, name);
    }

    public enum AccessRule {

        ALL(""),
        KILLS("kills"),
        DEATHS("deaths"),
        POINTS("points");

        private final String key;

        AccessRule(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public static String[] toStringList(AccessRule... rules) {
            String[] list = new String[rules.length + 1];
            list[0] = "UUID";
            for (int i = 0; i < rules.length; i++)
                list[i + 1] = rules[i].getKey();

            return list;
        }

        public static boolean isAll(AccessRule... rules) {
            if (rules.length == 0)
                return true;
            for (AccessRule rule : rules) {
                if (rule == AccessRule.ALL)
                    return true;
            }

            return false;
        }
    }
}
