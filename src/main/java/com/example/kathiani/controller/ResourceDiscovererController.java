package com.example.kathiani.controller;
import com.example.kathiani.model.SensorEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.kathiani.service.DataValidationUtil;


@RestController
public class ResourceDiscovererController{
 
	public static String searchValidate(){ // busca dado e valida  // *a definir info pela qual dado será buscado na plataforma      
    	String responseDataResource = searchData();       //recuperando dado da plataforma
		if (SensorEntity.readValidDataByUuid("0bda68c6-e7ff-4917-914a-86c9d6b97e35")==null){
			System.err.println("testando");
			String validData = 	DataValidationUtil.dataValidate(responseDataResource);
			return "O dado original é: " + responseDataResource + "o dado validado é:" + validData;
		} else{
			return "O dado original já foi validado: " + responseDataResource;
		}
	    
	}

	 public static String searchData(){
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "0bda68c6-e7ff-4917-914a-86c9d6b97e35";   // *determinado uuid por enquanto 
       	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);  
        return responseDataResource;
    }    
   
 
	public static String dataValidateviaRequest(@RequestBody String data) {   // dado enviado diretamente para validação (plataforma quanto app)       
		String validData = DataValidationUtil.dataValidate(data);
	    return validData;
	}

}