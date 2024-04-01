package com.example.kathiani.controller;
import com.example.kathiani.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceDiscovererController{

   
	public static String dataSearching(){ // busca dado e valida  // *a definir info pela qual dado será buscado na plataforma
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
    	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);        
		String validData = DataValidationUtil.dataValidate(responseDataResource);
	    return validData;
	}


	public static String dataValidateviaRequest(@RequestBody String data) {   // dado enviado diretamente para validação (plataforma quanto app)
        StringBuilder formattedString = new StringBuilder();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode[] jsonNodes = objectMapper.readValue(data, JsonNode[].class);
        	for (int i = 0; i < jsonNodes.length; i+=3) {
       	    JsonNode node = jsonNodes[i];
        	formattedString.append(node.get(i).toString()).append("\n"); // Adicione os dados JSON com uma nova linha	
			formattedString.append(node.get(i+1).toString()).append("\n");
			formattedString.append(node.get(i+2).toString()).append("\n");
		}
       	} catch (Exception e) {
       	   	e.printStackTrace();
       	}
        
		String validData = DataValidationUtil.dataValidate(data);
	    return validData;
	}

}