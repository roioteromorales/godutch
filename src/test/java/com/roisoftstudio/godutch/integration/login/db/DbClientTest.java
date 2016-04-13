package com.roisoftstudio.godutch.integration.login.db;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.roisoftstudio.godutch.db.DbClient;
import org.bson.Document;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DbClientTest {

    @Test
    public void canCreateOneDocumentAndReadIt() throws Exception {
        DbClient dbClient = new DbClient();

        MongoCollection<Document> test = dbClient.getCollection("test");

        test.insertOne(Document.parse("{'name':'roi', 'age':28}"));
        FindIterable<Document> documents = test.find();
        System.out.println(documents.first().toJson());
        assertThat(documents.first().toJson()).contains("roi");
    }

}