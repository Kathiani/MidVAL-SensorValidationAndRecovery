package com.example.kathiani.config;
import org.springframework.web.bind.annotation.*;
import com.example.kathiani.controller.ResourceDiscovererController;
import org.springframework.ui.Model;



@RestController
public class Setup {
        
    @GetMapping("/midval")  // setup midval
	   public String controller(Model model) {
	        return "MidVAL up";
	}   

	@GetMapping("/midval/search-validate")  // buscar dado e validar
		public String dataSearching(){
            String responseValidData =  ResourceDiscovererController.searchValidate();
			return responseValidData;
		}
	
	@PostMapping("/midval/validate")  // apenas validar dado enviado diretamente para validação (plataforma quanto app)
		public String validateViaRequest(@RequestBody String data){
            String responseValidData =  ResourceDiscovererController.dataValidateviaRequest(data);
			return responseValidData;
		}
	
}
        

