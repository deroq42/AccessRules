package de.deroq.accessrules.managers;

import com.google.gson.Gson;
import com.mongodb.CursorType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import de.deroq.accessrules.StatsAccessRules;
import de.deroq.accessrules.models.StatsUser;
import de.deroq.accessrules.models.StatsUserBuilder;
import de.deroq.accessrules.utils.Constants;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class StatsManager {

    private final StatsAccessRules statsAccessRules;

    public StatsManager(StatsAccessRules statsAccessRules) {
        this.statsAccessRules = statsAccessRules;
    }

    public void createStatsUser(UUID uuid, String name) {
        CompletableFuture.runAsync(() -> {
            Document document = statsAccessRules.getDatabaseManager().getStatsCollection().find(Filters.eq("uuid", uuid.toString())).first();
            if (document != null) {
                return;
            }

            StatsUser statsUser = new StatsUserBuilder()
                    .setUuid(uuid.toString())
                    .setName(name)
                    .build();

            document = new Gson().fromJson(new Gson().toJson(statsUser), Document.class);
            statsAccessRules.getDatabaseManager().getStatsCollection().insertOne(document);
        }, Constants.EXECUTOR_SERVICE);
    }

    public CompletableFuture<StatsUser> getAsyncStatsUserByName(String name, StatsUser.AccessRule... accessRules) {
        CompletableFuture<StatsUser> future = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            FindIterable<Document> cursor = statsAccessRules.getDatabaseManager().getStatsCollection().find(Filters.eq("name", name));

            cursor.cursorType(CursorType.NonTailable);
            if (!StatsUser.AccessRule.isAll(accessRules)) {
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
