package de.deroq.accessrules.managers;

import com.google.gson.Gson;
import com.mongodb.CursorType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import de.deroq.accessrules.StatsAccessRules;
import de.deroq.accessrules.models.StatsUser;
import de.deroq.accessrules.utils.Constants;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;

public class StatsManager {
    private final MongoCollection<Document> collection;

    public StatsManager(StatsAccessRules statsAccessRules) {
        this.collection = statsAccessRules.getStatsAccessRulesDatabase().getStatsCollection();
    }

    /**
     * Inserts a StatsUser document into the database.
     *
     * @param uuid The uuid of the user.
     * @param name The name of the user.
     */
    public void createStatsUser(String uuid, String name) {
        CompletableFuture.runAsync(() -> {
            Document document = collection.find(Filters.eq("uuid", uuid)).first();
            if (document == null) {
                StatsUser statsUser = StatsUser.create(uuid, name);

                document = new Gson().fromJson(new Gson().toJson(statsUser), Document.class);
                collection.insertOne(document);
            }
        }, Constants.EXECUTOR_SERVICE);
    }

    /**
     * @param name The name of the user.
     * @param accessRules The AccessRules to select the necessary data.
     * @return a Future with a StatsUser.
     */
    public CompletableFuture<StatsUser> getStatsUser(String name, StatsUser.AccessRule... accessRules) {
        CompletableFuture<StatsUser> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            FindIterable<Document> cursor = collection.find(Filters.eq("name", name));

            cursor.cursorType(CursorType.NonTailable);
            /* If we do not want all the data. */
            if (!StatsUser.AccessRule.isAll(accessRules)) {
                /* Will only select the necessary data, in our case the accessRules. */
                cursor.projection(Projections.include(StatsUser.AccessRule.toStringList(accessRules)));
            }

            Document document = cursor.first();
            if (document == null) {
                future.complete(null);
                return;
            }

            future.complete(new Gson().fromJson(document.toJson(), StatsUser.class));
        }, Constants.EXECUTOR_SERVICE);

        return future;
    }
}
