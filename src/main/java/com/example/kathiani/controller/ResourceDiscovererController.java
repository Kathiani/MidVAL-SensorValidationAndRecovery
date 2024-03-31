package com.example.kathiani.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.kathiani.service.DataRecoveryUtil;
import com.example.kathiani.service.DataValidationUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;




public class ResourceDiscovererController{

    @PostMapping("/midval/search_validate")  // searching data
	public static String dataSearching(){   // *a definir info pela qual dado será buscado na plataforma
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
    	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class); 
            
		String ValidData = DataValidationUtil.dataValidate2(responseDataResource);
			
      

		return ValidData;
	}

}