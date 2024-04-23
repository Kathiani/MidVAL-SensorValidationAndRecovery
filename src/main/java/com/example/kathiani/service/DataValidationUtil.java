package com.example.kathiani.service;
import com.example.kathiani.utils.*;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Thread;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class DataValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataValidationUtil.class);

   	public static String dataValidate(String data) {
        logger.info("***Starting data validation***");
        try {
        long initialTime = System.nanoTime();
        int retry = 0; int nmaxretry = 2;    // tentativas de obtenção de novo dado
        boolean isvalid1, isvalid2 = false;

        isvalid1  = validationByDate(data);          
        while((retry < nmaxretry) && (isvalid1!=true)){
            logger.info("Retrying an up-to-date-value!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted");
            }
            data = DataRecoveryUtil.retry();
            isvalid1  = validationByDate(data);   
            retry = retry + 1;
        } 

        retry = 0;   // atualizando número de tentativas

        isvalid2 = validateByHistorical(data);
        while((retry < nmaxretry) && (isvalid2!=true)){
            logger.info("Retrying an up-to-date-value!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted");
            }
            data = DataRecoveryUtil.retry();
            isvalid1  = validateByHistorical(data);   
            retry = retry + 1;
        } 

        long finalTime = System.nanoTime();
        double totalTime = (double) ((initialTime - finalTime) / 1_000_000)/1000;  // tempo de processamento em milissegundos
        logger.info("Validation in: " + totalTime + "ms");
        if ((isvalid1==true)&&(isvalid2==true))      
            return data;
        else{ 
            return "Data is not ready!";
       }
           
    }


    public static boolean validationByDate(String data){ 
       logger.info("Validating availability and update\n");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LocalDateTime currentDate = Utilities.currentDate();
        LocalDateTime limitDate = currentDate.minusSeconds(3);
        if (recentDate.isBefore(limitDate) && (!recentDate.isAfter(currentDate)))
            return false;  //fora dos 3 segundos de tolerância, dado está desatualizado
        else  
            return true;
			
	}

    public static boolean validateByHistorical(String data){ 
        logger.info("Validating values by historical values \n");
        JSONObject jsonObject = new JSONObject(data);  
        int intervalAnalysis = 2;  // batches of analysis from historical
        double media = 0; double tolerance = 3;
        JSONArray resourcesArray = jsonObject.getJSONArray("resources");  // Obtém a matriz "resources"
	    try {
            int[] temperaturas = new int[intervalAnalysis];
            int cont = 0;
           
         
            // Obtém o objeto de recurso atual
            JSONObject resource = resourcesArray.getJSONObject(0);
                
            // Obtém o array "environment_monitoring" dentro do recurso atual
            JSONArray environmentMonitoringArray = resource
                    .getJSONObject("capabilities")
                    .getJSONArray("environment_monitoring");

            // Itera sobre os primeiros 'quantidade' elementos de "environment_monitoring" e obtém os valores de temperatura
            for (int j = 0; j < Math.min(environmentMonitoringArray.length(), intervalAnalysis); j++) {
                JSONObject elemento = environmentMonitoringArray.getJSONObject(j);
                int temperaturaAtual = elemento.getInt("temperature");
                temperaturas[cont] = temperaturaAtual;
                cont++;
            }
           
            int soma = 0;
            for (int temperatura : temperaturas) {
                soma += temperatura;
            }
            media = (double) soma / cont;  //calculate media
           
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        // Obtém o primeiro objeto dentro do array "resources"
        JSONObject firstResource = jsonObject.getJSONArray("resources").getJSONObject(0);
        JSONObject environmentMonitoring = firstResource.getJSONObject("capabilities").getJSONArray("environment_monitoring").getJSONObject(0);
        int temperature2 = environmentMonitoring.getInt("temperature");
        if (temperature2 < (media-tolerance) || temperature2 > (media-tolerance))
            return false;    //"out of the range of last temperatures";
        else
            return true;     //"regular values";
  
    }

        

       
}

   

			



