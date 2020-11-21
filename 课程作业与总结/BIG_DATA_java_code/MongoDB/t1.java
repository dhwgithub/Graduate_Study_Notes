import java.util.ArrayList;
import java.util.List;

import org.bson.Document;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class t1 {

	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		
		MongoDatabase mongoDatabase = mongoClient.getDatabase("student");
		MongoCollection<Document> collection = mongoDatabase.getCollection("student");
		
		Document document = new Document("name", "scofield").append("scores", 
				new Document("English", 45).append("Math", 89).append("Computer", 100));
		
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
		
		System.out.println("insert success");
	}

}
