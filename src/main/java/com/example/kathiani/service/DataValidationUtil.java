package com.example.kathiani.service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataValidationUtil {
   	public static String dataValidate2(String data) {
        boolean isValid1  = initialValidation(data);
        if (isValid1==true){
            return data;
        }else{
            String dataRetrieved =  DataRecoveryUtil.recoverData(data);
            return dataRetrieved;
        } 
    }

    public static boolean initialValidation(String data){ 
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

        return true;
			
	}







    @PostMapping("/midval/validate")  //dado enviado diretamente para validação
	public static void dataValidate1(@RequestBody String data) {
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

			
	}

}
