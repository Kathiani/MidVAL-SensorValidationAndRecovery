package com.example.kathiani.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.example.kathiani.utils.Utilities;

@RestController
public class DataValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataValidationUtil.class);

   	public static String dataValidate(String data) {
        logger.info("\n***Starting data validation***");
        
        String newData = null;
        long initialTime = System.nanoTime();
        int retry = 0, nmaxretry = 2;    // tentativas de obtenção de novo dado
        boolean isvalid1, isvalid2 = false;

        isvalid1  = validationByDate(data);          
        while((retry < nmaxretry) && (isvalid1!=true)){
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

        //isvalid2 = validateByHistoricalAverage(data);
        while((retry < nmaxretry) && (isvalid2!=true)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted");
            }
            data = DataRecoveryUtil.retry();
            //isvalid1  = validateByHistoricalAverage(data);   
            retry = retry + 1;
        } 

        long finalTime = System.nanoTime();
        double totalTime = (double) ((initialTime - finalTime) / 1_000_000)/1000;  // tempo de processamento em milissegundos
        logger.info("Validation in: " + totalTime + "ms");
        if ((isvalid1==true)&&(isvalid2==true))      
            return data;
        else{ 
            newData =  DataRecoveryUtil.recoverbyMovingAverage(data);
            return newData;
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
                JSONArray environmentMonitoringArray = capabilitiesObject.getJSONArray("environment_monitor");
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

    public static boolean validateByHistoricalAverage(String data) { 
        logger.info("Validating values by average \n");
        JSONObject jsonObject = new JSONObject(data);  
        double tolerance = 2;  // Tolerância para validação
        JSONArray resourcesArray = jsonObject.getJSONArray("resources");  // Obtém a matriz "resources"

        try {
            double[] temperatures = new double[resourcesArray.length()];
            JSONObject resource = resourcesArray.getJSONObject(0);
                
            // Obtém o array "environment_monitoring" dentro do recurso atual
            JSONArray environmentMonitoringArray = resource
                    .getJSONObject("capabilities")
                    .getJSONArray("environment_monitor");

            // Extrai os valores de temperatura de "environment_monitoring" e armazena em um array
            for (int j = 1; j < environmentMonitoringArray.length(); j++) {
                JSONObject element = environmentMonitoringArray.getJSONObject(j);
                temperatures[j] = element.getDouble("temperature");
            }
        
            // Calcula a média móvel de 3 períodos e armazena em um novo array
            double[] movingAverages = PreProcessing.movingAverage(temperatures);
            double sum = 0;
            for (int j = 0; j < movingAverages.length; j++) {
                sum += movingAverages[j];
            }
            
            double meanTemp = sum / movingAverages.length;
            
            // Comparar a temperatura atual com a media
            // Se estiver dentro da tolerância, considera-se válido
            double currentTemperature =  resource.getInt("temperature");
            if (Math.abs(currentTemperature - meanTemp) <= tolerance) {
                return true;
            }
           
            return false;
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

  
}


        

       


   

			



