package com.camel.arquitetura.atendimento.system.controller;

import org.apache.camel.CamelExecutionException;
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
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;
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
    public String createOrdemServico(@RequestBody CreateOrdemServicoDTO createOrdemServicoDTO) throws Exception {
        AtendenteResponseDTO atendente = template.requestBody("direct:get-atendente", createOrdemServicoDTO.getUserId().toString(), AtendenteResponseDTO.class);
        ClienteResponseDTO cliente = new ClienteResponseDTO();        
        try {
            // Busca cliente por razao social e ja verifica se e da mesma base do atendente
            cliente = template.requestBodyAndHeader("direct:get-cliente-by-name", createOrdemServicoDTO.getNomeCliente(), 
                    "base", atendente.getBase(), ClienteResponseDTO.class);
        } catch (CamelExecutionException e) {
            return "Só um atendente da mesma base do cliente pode abrir uma OS";
        }
        
        try {
            PrestadorResponseDTO prestador = template.requestBody("direct:get-prestador-by-base", cliente.getBase(), PrestadorResponseDTO.class);
            System.out.println(prestador.getRazaoSocial());
        } catch (CamelExecutionException e) {
            return "Não há prestador na região";
        }
        
        return null;
    }
}
