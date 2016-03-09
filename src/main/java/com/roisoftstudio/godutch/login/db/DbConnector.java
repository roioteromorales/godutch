package com.roisoftstudio.godutch.login.db;

import com.mongodb.MongoClient;

public class DbConnector {

    private MongoClient mongoClient;

    public DbConnector() {
    }

    public void connect(){
        String docker_host = System.getenv("DOCKER_HOST").split(":")[1].substring(2);
        mongoClient = new MongoClient(docker_host, 27017);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
