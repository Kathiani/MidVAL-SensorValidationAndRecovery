package com.example.kathiani.config;
import org.springframework.web.bind.annotation.*;
import com.example.kathiani.controller.ResourceDiscovererController;
import com.example.kathiani.service.DataValidationUtil;

import org.springframework.ui.Model;



@RestController
public class Setup {
        
    @GetMapping("/midval")  // setup midval
	   public String controller(Model model) {
	        return "MidVAL up";
	}   

	@GetMapping("/midval/search-validate")  // buscar dado e validar
		public String dataSearching(){
            String responseDataResource =  ResourceDiscovererController.dataSearching();
			//String validData = DataValidationUtil.dataValidate(responseDataResource);

			return "teste";
		}
	
	@PostMapping("/midval/validate")  // apenas validar dado enviado diretamente para validação (plataforma quanto app)
		public String validateViaRequest(@RequestBody String data){
            String validData =  ResourceDiscovererController.dataValidateviaRequest(data);
			return validData;
		}
	
}
        

