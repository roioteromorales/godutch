package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import com.roisoftstudio.godutch.db.DbClient;
import com.roisoftstudio.godutch.json.GsonSerializer;
import com.roisoftstudio.godutch.login.model.User;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class MongoDbUserDao implements UserDao {

    private DbClient dbClient;

    @Inject
    public MongoDbUserDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void addUser(User user) throws UserAlreadyExistsException {
        MongoCollection<Document> users = dbClient.getCollection("users");
        users.insertOne(toDocument(user));
    }

    @Override
    public boolean contains(User user) {
        MongoCollection<Document> users = dbClient.getCollection("users");
        return !users.find(eq("email", user.getEmail())).first().isEmpty();
    }

    @Override
    public User findByEmail(String userEmail) {
        return null;
    }

    private Document toDocument(User user) {
        return Document.parse(toJson(user));
    }

    private String toJson(User user) {
        return new GsonSerializer().toJson(user);
    }
}
