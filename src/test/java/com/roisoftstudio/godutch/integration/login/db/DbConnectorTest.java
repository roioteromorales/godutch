package com.roisoftstudio.godutch.integration.login.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.roisoftstudio.godutch.login.db.DbConnector;
import org.bson.Document;
import org.junit.Ignore;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.util.Arrays.asList;

@Ignore
public class DbConnectorTest {


    @Test
    public void DbConnector_shouldConnectToMongoContainer() throws Exception {
        DbConnector dbConnector = new DbConnector();

        MongoClient mongoClient = dbConnector.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        MongoCollection<Document> restaurants = db.getCollection("restaurants");
        restaurants.insertOne(createDocument(format));
    }


    private Document createDocument(DateFormat format) throws ParseException {
        return new Document("address",
                new Document()
                        .append("street", "2 Avenue")
                        .append("zipcode", "10075")
                        .append("building", "1480")
                        .append("coord", asList(-73.9557413, 40.7720266)))
                .append("borough", "Manhattan")
                .append("cuisine", "Italian")
                .append("grades", asList(
                        new Document()
                                .append("date", format.parse("2014-10-01T00:00:00Z"))
                                .append("grade", "A")
                                .append("score", 11),
                        new Document()
                                .append("date", format.parse("2014-01-16T00:00:00Z"))
                                .append("grade", "B")
                                .append("score", 17)))
                .append("name", "Vella")
                .append("restaurant_id", "41704620");
    }
}