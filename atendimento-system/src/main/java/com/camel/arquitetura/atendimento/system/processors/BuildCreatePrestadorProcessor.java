package com.camel.arquitetura.atendimento.system.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreateClienteRequestDTO;
import com.camel.arquitetura.atendimento.system.model.dto.CreatePrestadorRequestDTO;
import com.google.gson.JsonObject;

public class BuildCreatePrestadorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        AtendenteResponseDTO atendente = exchange.getIn().getBody(AtendenteResponseDTO.class);
        CreatePrestadorRequestDTO dto = exchange.getProperty("dto", CreatePrestadorRequestDTO.class);
        
        JsonObject object = new JsonObject();
        object.addProperty("razaoSocial", dto.getRazaoSocial());
        object.addProperty("base", atendente.getBase().toString());
        object.addProperty("endereco", dto.getEndereco());
        object.addProperty("email", dto.getEmail());
        object.addProperty("telefone", dto.getTelefone());
        
        exchange.getIn().setBody(object.toString());        
    }

}
