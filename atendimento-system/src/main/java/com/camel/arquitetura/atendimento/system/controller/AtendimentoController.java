package com.camel.arquitetura.atendimento.system.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreateOrdemServicoDTO;
import com.google.gson.Gson;

@RestController
@RequestMapping("/ordem-servico")
public class AtendimentoController {
    @Autowired
    private ProducerTemplate template;
    
    @GetMapping
    public String helloWorld() {
        String response = template.requestBody("direct:hello-world", "", String.class);
        
        return response;
    }
    
    @PostMapping("/create")
    public String createOrdemServico(@RequestBody CreateOrdemServicoDTO createOrdemServicoDTO) {
        AtendenteResponseDTO atendente = template.requestBody("direct:get-atendente", createOrdemServicoDTO.getUserId().toString(), AtendenteResponseDTO.class);
        
        // Busca cliente por razao social e ja verifica se e da mesma base do atendente
        ClienteResponseDTO cliente = template.requestBodyAndHeader("direct:get-cliente-by-name", createOrdemServicoDTO.getNomeCliente(), 
                "base", atendente.getBase(), ClienteResponseDTO.class);
        
        
        return null;
    }
}
