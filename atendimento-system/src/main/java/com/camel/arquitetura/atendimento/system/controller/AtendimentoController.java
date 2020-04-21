package com.camel.arquitetura.atendimento.system.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtendimentoController {
    @Autowired
    private ProducerTemplate template;
    
    @GetMapping
    public String helloWorld() {
        String response = template.requestBody("direct:hello-world", "", String.class);
        
        return response;
    }
}
