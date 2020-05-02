package com.camel.arquitetura.atendimento.system.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    // Só supervisor pode apagar clientes e eles devem ser da mesma base
    // Atendentes podem registrar clientes de mesma região
    @Autowired
    private ProducerTemplate producerTemplate;
    
    @GetMapping()
    public List<ClienteResponseDTO> getAll(@RequestHeader Long userId) {
        ClienteResponseDTO[] clientes = producerTemplate.requestBodyAndHeader("direct:get-clientes", "", 
                "userId", userId, ClienteResponseDTO[].class);
        return Arrays.asList(clientes);
    }
    
    @GetMapping("/{id}")
    public ClienteResponseDTO getById(@RequestHeader Long userId, @PathVariable Long id) throws Exception {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("userId", userId);
        headers.put("id", id);
        ClienteResponseDTO cliente = null;
        
        try {
            cliente = producerTemplate.requestBodyAndHeaders("direct:get-cliente-by-id", "", headers, ClienteResponseDTO.class);
        } catch (Exception e) {
            throw new Exception("Cliente não pertence à base do atendente");
        }
        
        return cliente;
    }
}
