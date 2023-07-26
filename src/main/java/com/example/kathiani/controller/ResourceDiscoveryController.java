package com.example.kathiani.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ResourceDiscoveryController {

        //Apresenta histórico de dados validados pelo middleware , só pra ilustrar dados salvos;
	    @GetMapping("/midval")
	    public String starting() {
			//acessar a dados no banco;
	        return "logmid";
	    }
        
		
		@PostMapping("/midval/valida")
		public String validaData(@RequestBody String dados) {
        // Aqui você pode processar os dados recebidos e retornar uma resposta, se necessário.
        // Por exemplo, salvar os dados em um banco de dados, executar alguma lógica de negócio, etc.
        return "Dados recebidos com sucesso!";
        }

}
