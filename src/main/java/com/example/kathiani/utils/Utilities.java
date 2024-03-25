package com.example.kathiani.utils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Utilities {

 public static String populateDatabase(){
      RestTemplate restTemplate = new RestTemplate();
		String endpointUrl = "http://10.10.10.104:8000/catalog/capabilities";  // Acessando InterSCity
     
        ResponseEntity<String> responseEntity = createCapability(restTemplate, endpointUrl);
        // createResources();
        //senddataResource();
        return responseEntity.toString();
        
    }

    public static ResponseEntity<String> createCapability(RestTemplate restTemplate, String endpointUrl){
      String jsonBody = "{ \"name\": \"environment_monitoring\", \"description\": \"Measure the temperature and humidity of the environment\", \"capability_type\": \"sensor\" }";
      HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
	   ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class);

        System.out.println("Olá, mundo!");
        
         if (responseEntity.getStatusCode().is2xxSuccessful()) {
	                System.out.println("Capacidade criada com sucesso!");
	            } else {
	                System.out.println("Erro ao criar a capacidade: " + responseEntity.getStatusCode());
	            }
				;

        return responseEntity;
    }
    
}
