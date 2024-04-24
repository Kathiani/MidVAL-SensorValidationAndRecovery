package com.example.kathiani.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;


public class DataRecoveryUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataRecoveryUtil.class);

    public static String recoverData(String data){
        return null;
    }


    public static String retry(){
        logger.info("*Retrying an up-to-date-value!*");
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
       	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);  
        return responseDataResource;
    }      


    public static String recoverbyMovingAvarage(String data) { 
        logger.info("recovering by moving average \n");
        JSONObject jsonObject = new JSONObject(data);       
        int intervalAnalysis = 5;  // Média móvel de uma janela específica leva em conta sazonalidade 
        double media = 0; 

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
            media = (double) soma / cont;  //calcula media

            JSONObject firstElement = environmentMonitoringArray.getJSONObject(0);
            firstElement.put("temperature", media);
           
            return firstElement.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "error in recover data";
        }
      

        
        
    }
        
  
    


}

