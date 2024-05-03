package com.example.kathiani.service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


public class DataRecoveryUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataRecoveryUtil.class);

    public static String recoverData(String data){
        return null;
    }

    public static String retry(){
        logger.info("*Retrying an up-to-date-value!*");
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "0bda68c6-e7ff-4917-914a-86c9d6b97e35";   // *determinado uuid por enquanto 
       	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);  
        return responseDataResource;
    }      

    public static String recoverbyMovingAverage(String data) { 
        logger.info("Recovering by moving average \n");
             
        int intervalAnalysis = 3;  // Média móvel de uma janela específica leva em conta sazonalidade  buscando suavizar variações de curto prazo 
        double media = 0; 
        JSONObject jsonObject = new JSONObject(data);  
        JSONArray resourcesArray = jsonObject.getJSONArray("resources");  // Obtém a matriz "resources"
	  
        try {
            double[] temperaturas = new double[intervalAnalysis];
            int cont = 0;        
            // Obtém o objeto de recurso atual
            JSONObject resource = resourcesArray.getJSONObject(0);
                
            // Obtém o array "environment_monitoring" dentro do recurso atual
            JSONArray environmentMonitoringArray = resource
                    .getJSONObject("capabilities")
                    .getJSONArray("environment_monitor");

            // Itera sobre os primeiros 'quantidade' elementos de "environment_monitoring" e obtém os valores de temperatura
            for (int j = 1; j < Math.min(environmentMonitoringArray.length(), intervalAnalysis); j++) {
                JSONObject elemento = environmentMonitoringArray.getJSONObject(j);
                double temperaturaAtual = elemento.optDouble("temperature");
                temperaturas[cont] = temperaturaAtual;
                cont++;
            }
           
            double soma = 0;
            for (double temperatura : temperaturas) {
                soma += temperatura;
            }
            media = (double) soma / cont;  //calcula media

        
            JSONObject firstElement = environmentMonitoringArray.getJSONObject(0);
            firstElement.accumulate("temperature_valid:", media);          
            resource.getJSONObject("capabilities").put("environment_monitor", environmentMonitoringArray);

            // Atualiza o JSON original
            //resourcesArray.put(0, resource);
          
            //return resourcesArray.toString();
            return jsonObject.toString();

        } catch (Exception e){
            e.printStackTrace();
            return "error in recover data";
        }
           
        
    }
        

}

