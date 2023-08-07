package com.example.kathiani.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.Model;

@RestController
public class ResourceDiscoveryController {

        @GetMapping("/midval")
	    public String controller(Model model) {
	    	RestTemplate restTemplate = new RestTemplate();
	    	String url = "http://10.10.10.104:8000/catalog/resources";
	    	String sensorValue = restTemplate.getForObject(url, String.class); //Obter dados da InterSCity
	    	
	    	try {// Iterar sobre cada item retornado no arquivo JSON
	            // Parse do JSON para um objeto JSONObject
	            JSONObject jsonObject = new JSONObject(sensorValue);

	            // Obter o array "resources" do JSON
	            JSONArray resourcesArray = jsonObject.getJSONArray("resources");

	            // Iterar sobre os objetos no array "resources"
	            for (int i = 0; i < resourcesArray.length(); i++) {
	                JSONObject resource = resourcesArray.getJSONObject(i);

	                //Obter os valores individuais do objeto "resource"
	                int id = resource.getInt("id");
	                String createdAt = resource.getString("created_at");
	                double lat = resource.getDouble("lat");
	                double lon = resource.getDouble("lon");
	                String status = resource.getString("status");
	                String description = resource.getString("description");
	                String uuid = resource.getString("uuid");
                    
	                //Enviando dados para o modelo
	                model.addAttribute("id", id);
	                model.addAttribute("createdAt", createdAt);
	                model.addAttribute("lat", lat);
	                model.addAttribute("lon", lon);
	                model.addAttribute("status", status);
	                model.addAttribute("description", description);
	                model.addAttribute("uuid", uuid);
                
	                //Exibir os valores no prompt
	                System.out.println("Resource " + (i + 1));
	                System.out.println("ID: " + id);
	                System.out.println("Created At: " + createdAt);
	                System.out.println("Latitude: " + lat);
	                System.out.println("Longitude: " + lon);
	                System.out.println("Status: " + status);
	                System.out.println("Description: " + description);
	                System.out.println("UUID: " + uuid);
	                System.out.println();
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    
	    	//System.out.println("Resposta InterSCity requisição GET: " + sensorValue); //Por enquanto mostrando dados no prompt
	    	errorInjectionFunction(sensorValue);   //Envia os dados do sensor para o injetor de erros LapesFi
	    	//validatedSensor = validaData(sensorValueError);   //Envia dado para a função de validação
	    	//sendtoApplication(validatedSensor);  //Envia dado para aplicação 
	    	//return "logmid";
	    	return sensorValue;			
	    }

		public void errorInjectionFunction(String sensorValue) {
			RestTemplate restTemplate = new RestTemplate();
			String storedResource = restTemplate.getForObject("http://localhost:4000/resources", String.class); 
			System.out.println("Recursos previamente cadastrados no LapesFi: " + storedResource); //Por enquanto mostrando dados no prompt
			String endpointUrl = "http://localhost:4000/resources";
			String jsonString = "{\n" +
			        "  \"description\": \"Environment Monitor\",\n" +
			        "  \"capabilities\": [\n" +
			        "    {\n" +
			        "      \"name\": \"temperature\",\n" +
			        "      \"description\": \"Measure the temperature\"\n" +
			        "    },\n" +
			        "    {\n" +
			        "      \"name\": \"humidity\",\n" +
			        "      \"description\": \"Measure the humidity\"\n" +
			        "    }\n" +
			        "  ],\n" +
			        "  \"location\": \"Prefeitura de São Carlos - SP\"\n" +
			        "}";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);

	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class);

	            if (responseEntity.getStatusCode().is2xxSuccessful()) {
	                System.out.println("Recurso cadastrado no lapesFI!");
	            } else {
	                System.out.println("Erro ao salvar os dados: " + responseEntity.getStatusCodeValue());
	            }
				;
	        }
}
        

