package com.example.kathiani.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Utilities {

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
            uuid,horaAtualFormatada, 
                horaAtualFormatada); // String formatada para requisição
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class); //requisição post criando erro no recurso com determinado uuid 
	        if (responseEntity.getStatusCode().is2xxSuccessful()) {
	                System.out.println("Erro injetado no recurso!");
	            } else {
	                System.out.println("Erro ao injetar o erro no recurso: " + responseEntity.getStatusCodeValue());
	        }
			;

            String responseErrorInjection = restTemplate.getForObject(endpointUrl, String.class); //Obter resposta com dados injetados
			return responseErrorInjection;

	    }

		public static void computeMetrics(){

		}


		public static LocalDateTime currentDate(){
		    LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String currentTime = now.format(formatter);
			LocalDateTime currentdate = LocalDateTime.parse(currentTime, formatter);
            return currentdate;

		}
}

		

   
    

