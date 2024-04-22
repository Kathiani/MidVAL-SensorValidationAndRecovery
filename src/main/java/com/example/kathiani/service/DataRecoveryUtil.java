package com.example.kathiani.service;
import org.springframework.web.client.RestTemplate;


public class DataRecoveryUtil {
    public static String recoverData(String data){
        return null;
    }


    public static String retry(){
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
       	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);  
        return responseDataResource;
    }      

}
