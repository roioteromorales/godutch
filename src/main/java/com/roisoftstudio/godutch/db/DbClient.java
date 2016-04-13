package com.roisoftstudio.godutch.db;

import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@Singleton
public class DbClient {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public DbClient() {
        String docker_host = System.getenv("DOCKER_HOST").split(":")[1].substring(2);
        mongoClient = new MongoClient(docker_host, 27017);
        database = mongoClient.getDatabase("database");
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }
}
