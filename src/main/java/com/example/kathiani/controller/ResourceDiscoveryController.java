package com.example.kathiani.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.kathiani.utils.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.ui.Model;



@RestController
public class ResourceDiscoveryController {
        
        @GetMapping("/midval")
	    public String controller(Model model) {
	    	//String url = "http://10.10.10.104:8000/catalog/resources";       
	    	//String returnErrorData = errorInjectionFunction("9cf609af-3e7d-4bde-adad-f8b6f2dbe297");   //Cadastra erros em determinado recurso
			
			//String validatedSensorResponse = validateData(returnErrorData);   //Envia dado para a função de validação
			
	    	//sendtoApplication(validatedSensor);  //Envia dado para aplicação	

		    //Adicionar dados na InterSCity
		    //ResponseEntity<String> response = Utilities.senddataResource("8520039d-9480-442d-887a-333f0b300bdc");
	        //return  "UUId do recurso cujo erro será injetado: " + uuid + "\n \n * E * Retorno do LapesFI com dados incorretos: \n" +
		    //formattedString.toString();		
	    
            //return validatedSensorResponse.toString();
            return "MidVAL up";
		}   
	

        @PostMapping("/midval/validate")
		public static void validateData(@RequestBody String data) {
           /* StringBuilder formattedString = new StringBuilder();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode[] jsonNodes = objectMapper.readValue(returnErrorData, JsonNode[].class);
                for (int i = 0; i < jsonNodes.length; i+=3) {
           		  JsonNode node = jsonNodes[i];
                  formattedString.append(node.get(i).toString()).append("\n"); // Adicione os dados JSON com uma nova linha	
				  formattedString.append(node.get(i+1).toString()).append("\n");
				  formattedString.append(node.get(i+2).toString()).append("\n");
				}
            

          	} catch (Exception e) {
          	    e.printStackTrace();
        	}

			return formattedString.toString();  */ 
            System.out.println(data);
			//return data;

		}
}
        

