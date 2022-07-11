package de.deroq.accessrules.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.deroq.accessrules.StatsAccessRules;
import org.bson.Document;

/**
 * @author deroq
 * @since 01.05.2022
 */

public class StatsAccessRulesDatabase {

    private final StatsAccessRules statsAccessRules;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> statsCollection;

    public StatsAccessRulesDatabase(StatsAccessRules statsAccessRules) {
        this.statsAccessRules = statsAccessRules;
        connect();
    }

    private void connect() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.mongoDatabase = mongoClient.getDatabase("localhost");
        this.statsCollection = mongoDatabase.getCollection("stats");

        statsAccessRules.getLogger().info("Connected to MongoClient.");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<Document> getStatsCollection() {
        return statsCollection;
    }
}
