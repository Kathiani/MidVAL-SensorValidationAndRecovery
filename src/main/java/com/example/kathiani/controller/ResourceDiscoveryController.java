package com.example.kathiani.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.ui.Model;

import com.example.kathiani.model.SensorData;

@RestController
public class ResourceDiscoveryController {

        @GetMapping("/midval")
	    public String controller(Model model) {
			String jsonResponse = null;
			String uuid = null;
			StringBuilder formattedString = null;
			RestTemplate restTemplate = new RestTemplate();
	    	String url = "http://10.10.10.104:8000/catalog/resources";
	        try {
				// Fazer a solicitação HTTP GET e receber a resposta como uma string JSON
				jsonResponse = restTemplate.getForObject(url, String.class);

				// Usar Jackson para desserializar o JSON em um objeto JsonNode
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(jsonResponse);

				// Obter o valor do campo "uuid"
				uuid = rootNode.get("resources").get(0).get("uuid").asText();

				// Imprimir o UUID
				System.out.println("UUID: " + uuid);
				
        	} catch (Exception e) {
            	e.printStackTrace();
        	}
    	    
	    	String returnErrorData = errorInjectionFunction(uuid);   //Envia os dados do sensor para o injetor de erros LapesFi
	    	//validatedSensor = validaData(sensorValueError);   //Envia dado para a função de validação
	    	//sendtoApplication(validatedSensor);  //Envia dado para aplicação 
	    	//return "logmid";
			


			try {
            // Usar Jackson para analisar a string JSON em um array de objetos JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode[] jsonNodes = objectMapper.readValue(returnErrorData, JsonNode[].class);

            // Iterar sobre os elementos da lista
			 formattedString = new StringBuilder();
            for (JsonNode node : jsonNodes) {
                // Verificar se o campo "resource_uuid" contém o valor desejado
                if ("9cf609af-3e7d-4bde-adad-f8b6f2dbe297".equals(node.get("resource_uuid").asText())) {
                  formattedString.append(node.toString()).append("\n"); // Adicione os dados JSON com uma nova linha
                }
            }

        	} catch (Exception e) {
          	    e.printStackTrace();
        	}
			
	    	return  "UUId do recurso cujo erro será injetado: " + uuid + "\n \n * E * Retorno do LapesFI com dados incorretos: \n" + formattedString.toString();		
	    }

		
		public String errorInjectionFunction(String uuid) {
			RestTemplate restTemplate = new RestTemplate();
			String endpointUrl = "http://localhost:4000/interscity";  // Acessando LapesFI

			// Obter a hora atual do sistema
     		LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String horaAtualFormatada = now.format(formatter);
			
			String jsonString = String.format(
            "{" +
                "\"uuid\": \"%s\"," +
                "\"type_of_error\": \"loss accuracy\"," +
                "\"initial_date\": \"%s\"," +
                "\"final_date\": \"%s\"," +
                "\"intensity\": 129" +
            "}",
            uuid,horaAtualFormatada, // Adicionando hora atual formatada
                horaAtualFormatada);
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class);

	            if (responseEntity.getStatusCode().is2xxSuccessful()) {
	                System.out.println("Erro injetado na capability!");
	            } else {
	                System.out.println("Erro ao injetar o erro nos dados: " + responseEntity.getStatusCodeValue());
	            }
				;
            String responseErrorInjection = restTemplate.getForObject(endpointUrl, String.class);
			return responseErrorInjection;
	        }
}
        

