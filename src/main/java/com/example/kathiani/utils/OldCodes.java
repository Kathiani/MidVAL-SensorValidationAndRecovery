package com.example.kathiani.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class OldCodes {

    //Calculando a média com base nas última x leituras
     public static boolean validateByMediaHistorical(String data){ 
        
        JSONObject jsonObject = new JSONObject(data);  
        int intervalAnalysis = 2;  // batches of analysis from historical (média móvel)
        double media = 0; double tolerance = 2;
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
            return false;    //"out of the range of temperatures";
        else
            return true;     //"regular values";
  
    }
    
}
