package com.example.kathiani.config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;



@RestController
public class Setup {
        
        @GetMapping("/midval")
	    public String controller(Model model) {
	         return "MidVAL up";
		}   
        
	
}
        

