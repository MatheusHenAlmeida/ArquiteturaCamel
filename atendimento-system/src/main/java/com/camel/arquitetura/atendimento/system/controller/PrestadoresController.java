package com.camel.arquitetura.atendimento.system.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.camel.arquitetura.atendimento.system.exceptions.UnknownException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreateClienteRequestDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreatePrestadorRequestDTO;
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;

@RestController
@RequestMapping("/prestadores")
public class PrestadoresController {
    @Autowired
    private ProducerTemplate producerTemplate;
    
    @GetMapping()
    public List<PrestadorResponseDTO> getAll(@RequestHeader Long userId) throws Throwable {
        PrestadorResponseDTO[] prestadores;
        try {
            prestadores = producerTemplate.requestBodyAndHeader("direct:get-prestadores", "",
                    "userId", userId, PrestadorResponseDTO[].class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }

        return Arrays.asList(prestadores);
    }
    
    @GetMapping("/{id}")
    public PrestadorResponseDTO getById(@RequestHeader Long userId, @PathVariable Long id) throws Throwable {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("userId", userId);
        headers.put("id", id);
        PrestadorResponseDTO prestador = null;
        
        try {
            prestador = producerTemplate.requestBodyAndHeaders("direct:get-prestador-by-id", "", headers, PrestadorResponseDTO.class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }

        return prestador;
    }
    
    @PostMapping("/create")
    public PrestadorResponseDTO createCliente(@RequestBody CreatePrestadorRequestDTO dto, 
            @RequestHeader Long userId) throws Throwable {
        PrestadorResponseDTO prestador = null;
        
        try {
            // Registra o prestador na mesma região do atendente
            prestador = producerTemplate.requestBodyAndHeader("direct:create-prestador", dto, 
                    "userId", userId, PrestadorResponseDTO.class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }
        
        return prestador;
    }
    
    @DeleteMapping("/delete/{id}")
    public PrestadorResponseDTO removeCliente(@PathVariable Long id, @RequestHeader Long userId) throws Throwable {
        PrestadorResponseDTO prestador = null;
        
        try {
            // Apaga o cliente se o atendente for um supervisor da mesma região
            prestador = producerTemplate.requestBodyAndHeader("direct:remove-prestador", id, 
                    "userId", userId, PrestadorResponseDTO.class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }
        
        return prestador;
    }
}
