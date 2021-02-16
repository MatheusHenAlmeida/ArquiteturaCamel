package com.camel.arquitetura.atendimento.system.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.camel.arquitetura.atendimento.system.exceptions.AtendenteNotFoundException;
import com.camel.arquitetura.atendimento.system.exceptions.OutsideClientException;
import com.camel.arquitetura.atendimento.system.exceptions.PrestadorNotFoundException;
import com.camel.arquitetura.atendimento.system.exceptions.UnknownException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendimento.system.model.OrdemServico;
import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreateOrdemServicoDTO;
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/ordem-servico")
public class AtendimentoController {
    @Autowired
    private ProducerTemplate template;

    @GetMapping("/hello-world")
    public String helloWorld() {
        return template.requestBody("direct:hello-world", "", String.class);
    }
    
    @PostMapping("/create")
    public OrdemServico createOrdemServico(@RequestBody CreateOrdemServicoDTO createOrdemServicoDTO) throws Throwable {
        ClienteResponseDTO cliente;
        AtendenteResponseDTO atendente;
        PrestadorResponseDTO prestador;

        try {
            atendente = template.requestBody("direct:get-atendente", createOrdemServicoDTO.getUserId().toString(), AtendenteResponseDTO.class);

            // Busca cliente por razao social e ja verifica se e da mesma base do atendente
            cliente = template.requestBodyAndHeader("direct:get-cliente-by-name",
                    createOrdemServicoDTO.getNomeCliente(), "base", atendente.getBase(),
                    ClienteResponseDTO.class);

            prestador = template.requestBody("direct:get-prestador-by-base", cliente.getBase(), PrestadorResponseDTO.class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("cliente", cliente);
        headers.put("prestador", prestador);
        headers.put("descricao", createOrdemServicoDTO.getDescricao());
        
        String os = template.requestBodyAndHeaders("direct:insert-ordem-servico", "", headers, String.class);
        
        return new Gson().fromJson(os, OrdemServico.class);
    }
    
    @GetMapping
    public List<OrdemServico> getAll() {
        String response = template.requestBody("direct:get-ordens-servico", "", String.class);
        Type listType = new TypeToken<ArrayList<OrdemServico>>(){}.getType();
        List<OrdemServico> list = new Gson().fromJson(response, listType);
        return list;
    }
    
    @GetMapping("/opened")
    public List<OrdemServico> getAllOpened() {
        return filterByStatus(false);
    }
    
    @GetMapping("/closed")
    public List<OrdemServico> getAllClosed() {
        return filterByStatus(true);
    }
    
    private List<OrdemServico> filterByStatus(boolean status) {
        String response = template.requestBodyAndHeader("direct:get-ordens-by-status", "", "os-status", status, String.class);
        Type listType = new TypeToken<ArrayList<OrdemServico>>(){}.getType();
        List<OrdemServico> list = new Gson().fromJson(response, listType);
        return list;
    }
    
    @GetMapping("/{id}")
    public OrdemServico getById(@PathVariable Long id) {
        String response = template.requestBodyAndHeader("direct:get-ordem-by-id", "", "id", id, String.class);
        OrdemServico os = new Gson().fromJson(response, OrdemServico.class);
        return os;
    }
    
    @GetMapping("{id}/close")
    public OrdemServico closeOrdemServico(@RequestHeader Long userId, @PathVariable Long id) throws Throwable {
        if (userId == null) throw new AtendenteNotFoundException("User-Id deve ser fornecido");
        AtendenteResponseDTO atendente = null;
        try {
            atendente = template.requestBody("direct:get-atendente", userId.toString(), AtendenteResponseDTO.class);
        } catch (CamelExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            throw new UnknownException("Ocorreu um erro inesperado");
        }
        
        Map<String, Object> headers = new HashMap();
        headers.put("user", atendente);
        headers.put("id", id);
        
        OrdemServico ordemServico;
        try {
            ordemServico = template.requestBodyAndHeaders("direct:update-ordem-by-id", "", headers, OrdemServico.class);
        } catch (Exception e) {
            throw new Exception("Não foi possível fechar a ordem de serviço");
        }
        
        return ordemServico;
    }
}
