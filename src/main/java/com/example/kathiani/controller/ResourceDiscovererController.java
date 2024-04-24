package com.example.kathiani.controller;
import com.example.kathiani.service.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceDiscovererController{
 
	public static String searchValidate(){ // busca dado e valida  // *a definir info pela qual dado será buscado na plataforma
        RestTemplate restTemplate = new RestTemplate();
    	String uuid = "9cf609af-3e7d-4bde-adad-f8b6f2dbe297";   // *determinado uuid por enquanto 
    	String endpointInterSCity = "http://10.10.10.104:8000/collector/resources/" + uuid + "/data";  
    	String responseDataResource = restTemplate.getForObject(endpointInterSCity, String.class);        
		String validDataMessage = DataValidationUtil.dataValidate(responseDataResource);
		
	    //SensorDatabase.createDatabase();
	    //SensorEntity.createValidDataSensor(validData, "validData");
	    // return validData; 
	    //SensorEntity.saveValidData(responseDataResource);
	
	    return validDataMessage;
	}

     

	public static String dataValidateviaRequest(@RequestBody String data) {   // dado enviado diretamente para validação (plataforma quanto app)       
		String validData = DataValidationUtil.dataValidate(data);
	    return validData;
	}

}