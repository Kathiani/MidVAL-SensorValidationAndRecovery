package com.example.kathiani.model;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Service
@RestController
public class SensorEntity {
	public static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	public static MongoDatabase database = mongoClient.getDatabase("dataresources");
	public static String collectionname = "sensortest";
	public static MongoCollection<Document> collection = database.getCollection(collectionname);

	public static void createDatabase(){
		if(!collectionExists(database, "sensortest")) {
			// Se a coleção não existir, crie-a
			database.createCollection("sensortest");
			System.out.println("Collection '" + collectionname  + " was created successfully!");
		}
	
	}
	
	private static boolean collectionExists(MongoDatabase database, String collectionName) {
        MongoIterable<String> collections = database.listCollectionNames();
        for (String name : collections) {
            if (name.equals(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public static void saveValidData(String data){
		MongoCollection<Document> collection = database.getCollection(collectionname);
        Document document = Document.parse(data);
		document.append("validationDate:", new Date());
		collection.insertOne(document);

	}

	public static String readValidDataById(String id){
        MongoCollection<Document> collection = database.getCollection(collectionname);
		Document filtro = new Document("_id", new ObjectId(id)); //each document has an id
		Document documento = collection.find(filtro).first();
		if (documento != null) {
    		    String validData = documento.toString();
				System.out.println(validData);
                return validData;
		} else {
			    return null; 
		}

	}

	public static String readValidDataByUuid(String uuid){
        MongoCollection<Document> collection = database.getCollection(collectionname);
        Document query = new Document();
    
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
           while (cursor.hasNext()) {
               Document document = cursor.next();
               List<Document> resources = document.getList("resources", Document.class);
                	for (Document resource : resources) {
                	if (uuid.equals(resource.getString("uuid"))) {
                    	return uuid;
                }
            }
      	  }
    	}
   			
	    return null;
	}
			
	public static void updateValidData(String uuid, String newData){
		MongoCollection<Document> collection = database.getCollection(collectionname);
	   
		uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";

		// Define o filtro para identificar o documento com o UUID especificado
		Document filtro = new Document("resources.uuid", uuid);

		// Define a atualização que será aplicada (neste exemplo, atualizaremos o campo "temperature" para 200)
		Document update = new Document("$set", new Document("resources.$[elem].capabilities.environment_monitor.$[].temperature", 200));

		// Define as opções de array de filtragem
		List<Bson> filters = Arrays.asList(
			Filters.eq("elem.uuid", uuid)
		);

		// Executa a operação de atualização
		UpdateResult result = collection.updateOne(filtro, update, new UpdateOptions().arrayFilters(filters));

		// Verifica se a atualização foi bem-sucedida
		if (result.getModifiedCount() > 0) {
			System.out.println("Document was updated.");
		} else {
			System.out.println("None document was updated.");
		}
    }

	public static void deleteValidData(String uuid){
		MongoCollection<Document> collection = database.getCollection(collectionname);
		Document filtro = new Document("campo1", 2);
        DeleteResult result = collection.deleteOne(filtro);
		System.out.println(result);
	}
	
}

 
