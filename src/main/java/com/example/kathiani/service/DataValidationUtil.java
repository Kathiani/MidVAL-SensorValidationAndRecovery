package com.example.kathiani.service;
import com.example.kathiani.utils.*;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;

@RestController
public class DataValidationUtil {
   	public static String dataValidate(String data) {
        boolean isValid1  = initialValidation(data);
        //boolean isvalid2 = validateByHistorical(data);

        if (isValid1==true){
            return "Dado desatualizado";
            //return data;
        }else{
            return "Dado atualizado";
            //String dataRetrieved =  DataRecoveryUtil.recoverData(data);
            //return dataRetrieved;
       } 
    }


    public static boolean initialValidation(String data){ 
         LocalDateTime recentDate = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray resourcesArray = jsonObject.getJSONArray("resources");         
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            
            for (int i = 0; i < resourcesArray.length(); i++) {
                JSONObject resourceObject = resourcesArray.getJSONObject(i);
                JSONObject capabilitiesObject = resourceObject.getJSONObject("capabilities");
                JSONArray environmentMonitoringArray = capabilitiesObject.getJSONArray("environment_monitoring");
                for (int j = 0; j < environmentMonitoringArray.length(); j++) {
                    JSONObject monitoringDataObject = environmentMonitoringArray.getJSONObject(j);
                    String dateString = monitoringDataObject.getString("date");
                    LocalDateTime dateValue = LocalDateTime.parse(dateString, formatter);
                    if (j==0)
                        recentDate = dateValue;                   
                    if (dateValue.isAfter(recentDate)) {
                        recentDate = dateValue;
                    }
                }
            }
            System.out.println("Recent date: " + recentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LocalDateTime currentDate = Utilities.currentDate();
        LocalDateTime limitDate = currentDate.plusSeconds(5);
        if (recentDate.isBefore(currentDate) && recentDate.isAfter(limitDate))
            return false;  //fora dos 3 segundos de tolerância, dado está desatualizado;
        else  
            return true;
			
	}

    public static boolean validateByHistorical(String data){ 
        StringBuilder formattedString = new StringBuilder();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode[] jsonNode = objectMapper.readValue(data, JsonNode[].class);
            for (int i = 0; i < jsonNode.length; i+=3) {
           		JsonNode node = jsonNode[i];
                formattedString.append(node.get(i).toString()).append("\n"); // Adicione os dados JSON com uma nova linha	
				formattedString.append(node.get(i+1).toString()).append("\n");
				formattedString.append(node.get(i+2).toString()).append("\n");
			}
            

        } catch (Exception e) {
          	    e.printStackTrace();
        }

        return true;   //alterar
    }

   

			
}


