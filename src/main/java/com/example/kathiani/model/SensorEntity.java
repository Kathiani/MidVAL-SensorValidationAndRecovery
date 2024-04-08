package com.example.kathiani.model;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

@Service
@RestController
public class SensorEntity {
	public static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	public static MongoDatabase database = mongoClient.getDatabase("dataresources");

	public static void createDatabase(){
		if(!collectionExists(database, "sensortest")) {
			// Se a coleção não existir, crie-a
			database.createCollection("sensortest");
			System.out.println("Coleção '" + "sensortest" + "' criada com sucesso.");
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
		MongoCollection<Document> collection = database.getCollection("sensortest");
		/*Document document = new Document("campo1", 2)
							.append("campo2", 2)
							.append("campo3", 3);*/
        Document document = Document.parse(data);
		collection.insertOne(document);

	}


	public static String readValidData(String uuid){
        MongoCollection<Document> collection = database.getCollection("sensortest");
		Document filtro = new Document("_id", new ObjectId("66133babd8499f6cc16db967"));
		Document documento = collection.find(filtro).first();
		if (documento != null) {
    		    String validData = documento.toString();
				System.out.println(validData);
                return validData;
		} else {
			    return null; // Retorna null se o documento não for encontrado
		}
				
	}

	public static void updateValidData(String uuid, String newData){
		MongoCollection<Document> collection = database.getCollection("sensortest");
	   
		// UUID específico do documento que você deseja atualizar
		uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";

		// Define o filtro para identificar o documento com o UUID especificado
		Document filtro = new Document("resources.uuid", uuid);

		// Define a atualização que será aplicada (neste exemplo, atualizaremos o campo "temperature" para 200)
		Document update = new Document("$set", new Document("resources.$[elem].capabilities.environment_monitoring.$[].temperature", 200));

		// Define as opções de array de filtragem
		List<Bson> filters = Arrays.asList(
			Filters.eq("elem.uuid", uuid)
		);

		// Executa a operação de atualização
		UpdateResult result = collection.updateOne(filtro, update, new UpdateOptions().arrayFilters(filters));

		// Verifica se a atualização foi bem-sucedida
		if (result.getModifiedCount() > 0) {
			System.out.println("Documento atualizado com sucesso.");
		} else {
			System.out.println("Nenhum documento foi atualizado.");
		}
    }

	public static void deleteValidData(String uuid){
		MongoCollection<Document> collection = database.getCollection("sensortest");
		Document filtro = new Document("campo1", 2);
        DeleteResult result = collection.deleteOne(filtro);
		System.out.println(result);
	}
	
}

 
