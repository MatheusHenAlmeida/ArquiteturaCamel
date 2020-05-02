package com.camel.arquitetura.atendimento.system.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreateClienteRequestDTO;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    // Só supervisor pode apagar clientes e eles devem ser da mesma base
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
    
    @PostMapping("/create")
    public ClienteResponseDTO createCliente(@RequestBody CreateClienteRequestDTO dto, 
            @RequestHeader Long userId) throws Exception {
        ClienteResponseDTO cliente = null;
        
        try {
            // Registra o cliente na mesma região do atendente
            cliente = producerTemplate.requestBodyAndHeader("direct:create-cliente", dto, 
                    "userId", userId, ClienteResponseDTO.class);
        } catch (Exception e) {
            throw new Exception("Somente supervisores e analistas podem registrar clientes");
        }
        
        return cliente;
    }
}
