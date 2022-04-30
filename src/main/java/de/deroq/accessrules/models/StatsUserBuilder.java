package de.deroq.accessrules.models;

public class StatsUserBuilder {

    private final StatsUser statsUser;

    public StatsUserBuilder() {
        this.statsUser = new StatsUser();
    }

    public StatsUserBuilder setUuid(String uuid) {
        statsUser.setUuid(uuid);
        return this;
    }

    public StatsUserBuilder setName(String name) {
        statsUser.setName(name);
        return this;
    }

    public StatsUserBuilder setPoints(int points) {
        statsUser.setPoints(points);
        return this;
    }

    public StatsUserBuilder setKills(int kills) {
        statsUser.setKills(kills);
        return this;
    }

    public StatsUserBuilder setDeaths(int deaths) {
        statsUser.setDeaths(deaths);
        return this;
    }

    public StatsUser build() {
        return statsUser;
    }
}
