package com.jd.ssh.sshdemo.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Java MongoDB Example
 * 
 */
public class MongoDbApp {

    public static void main(String[] args) {

	try {
	    // connect to mongoDB
	    Mongo mongo = new Mongo("10.10.9.104", 27017);

	    // if database doesn't exists, mongoDB will create it
	    DB db = mongo.getDB("mydb");

	    // Get collection from MongoDB, database named "mydb"
	    // if collection doesn't exists, mongoDB will create it
	    DBCollection collection = db.getCollection("myCollection");

	    // create a document to store attributes
	    BasicDBObject document = new BasicDBObject();
	    document.put("id", 100);
	    document.put("name", "simple name");
	    document.put("message", "simple message");
	    

	    // save it into collection named "myCollection"
	    collection.insert(document);

	    // search query
	    BasicDBObject searchQuery = new BasicDBObject();
	    searchQuery.put("id", 100);

	    DBCursor cursor = collection.find(searchQuery);

	    // loop over the cursor and display the result
	    while (cursor.hasNext()) {
		System.out.println(cursor.next());
	    }

	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (MongoException e) {
	    e.printStackTrace();
	}
    }
}