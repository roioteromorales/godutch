package com.roisoftstudio.godutch.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.DOCKER_IP;

@Singleton
public class DbClient {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public DbClient() {
        mongoClient = new MongoClient(DOCKER_IP, 27017);
        database = mongoClient.getDatabase("godutch-db");
//        createCollections();

    }

//    private void createCollections() {
//        if (database.getCollection("accounts") == null)
//            database.createCollection("accounts");
//        database.getCollection("accounts").createIndex(new Document("email", 1), new IndexOptions().unique(true));
//    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }
}
