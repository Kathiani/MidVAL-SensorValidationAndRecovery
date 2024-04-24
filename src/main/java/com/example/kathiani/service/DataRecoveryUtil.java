package com.example.kathiani.service;
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
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
       	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);  
        return responseDataResource;
    }      

}
