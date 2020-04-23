package com.camel.arquitetura.atendimento.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/hello-world")
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
        
        PrestadorResponseDTO prestador;
        
        try {
            prestador = template.requestBody("direct:get-prestador-by-base", cliente.getBase(), PrestadorResponseDTO.class);
            System.out.println(prestador.getRazaoSocial());
        } catch (CamelExecutionException e) {
            return "Não há prestador na região";
        }
        
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("cliente", cliente);
        headers.put("prestador", prestador);
        headers.put("descricao", createOrdemServicoDTO.getDescricao());
        
        template.sendBodyAndHeaders("direct:insert-ordem-servico", "", headers);
        
        return null;
    }
    
    @GetMapping
    public String getAll() {
        String response = template.requestBody("direct:get-ordens-servico", "", String.class);
        return response;
    }
    
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id) {
    String response = template.requestBodyAndHeader("direct:get-ordem-by-id", "", "id", id, String.class);
        return response;
    }
}
