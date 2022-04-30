package de.deroq.accessrules.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseManager {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> statsCollection;

    public DatabaseManager() {
        connect();
    }

    private void connect() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.mongoDatabase = mongoClient.getDatabase("localhost");
        this.statsCollection = mongoDatabase.getCollection("stats");
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
