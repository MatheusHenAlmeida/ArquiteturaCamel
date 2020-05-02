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
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;

@RestController
@RequestMapping("/prestadores")
public class PrestadoresController {
    // Só supervisor pode apagar prestadores e eles devem ser da mesma base
    // Atendentes podem registrar prestadores de mesma região
    @Autowired
    private ProducerTemplate producerTemplate;
    
    @GetMapping()
    public List<PrestadorResponseDTO> getAll(@RequestHeader Long userId) {
        PrestadorResponseDTO[] prestadores = producerTemplate.requestBodyAndHeader("direct:get-prestadores", "", 
                "userId", userId, PrestadorResponseDTO[].class);
        return Arrays.asList(prestadores);
    }
    
    @GetMapping("/{id}")
    public PrestadorResponseDTO getById(@RequestHeader Long userId, @PathVariable Long id) throws Exception {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("userId", userId);
        headers.put("id", id);
        PrestadorResponseDTO prestador = null;
        
        try {
            prestador = producerTemplate.requestBodyAndHeaders("direct:get-prestador-by-id", "", headers, PrestadorResponseDTO.class);
        } catch (Exception e) {
            throw new Exception("Prestador não pertence à base do atendente");
        }
        
        return prestador;
    }
}
