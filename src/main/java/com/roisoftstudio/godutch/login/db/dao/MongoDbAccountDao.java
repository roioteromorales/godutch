package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.roisoftstudio.godutch.db.DbClient;
import com.roisoftstudio.godutch.json.GsonSerializer;
import com.roisoftstudio.godutch.login.model.Account;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class MongoDbAccountDao implements AccountDao {

    public static final String ACCOUNTS = "accounts";
    private DbClient dbClient;

    @Inject
    public MongoDbAccountDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void addAccount(Account account) throws AccountAlreadyExistsException {
        MongoCollection<Document> accounts = dbClient.getCollection(ACCOUNTS);
        try{
            accounts.insertOne(toDocument(account));
        }catch(MongoWriteException e){
            throw new AccountAlreadyExistsException("Cannot add account.", e);
        }
    }

    @Override
    public boolean contains(Account account) {
        MongoCollection<Document> accounts = dbClient.getCollection(ACCOUNTS);
        Document aMatchingDocument = accounts.find(eq("email", account.getEmail())).first();
        return aMatchingDocument != null;
    }

    private Document toDocument(Account account) {
        return Document.parse(toJson(account));
    }

    private String toJson(Account account) {
        return new GsonSerializer().toJson(account);
    }
}
