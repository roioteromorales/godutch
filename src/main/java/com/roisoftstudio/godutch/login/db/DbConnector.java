package com.roisoftstudio.godutch.login.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DbConnector {

    private MongoClient mongo;

    public DbConnector() {
    }

    public void connect(){
        mongo = new MongoClient( "localhost" , 27017 );

        MongoDatabase db = mongo.getDatabase("database name");




    }
}
