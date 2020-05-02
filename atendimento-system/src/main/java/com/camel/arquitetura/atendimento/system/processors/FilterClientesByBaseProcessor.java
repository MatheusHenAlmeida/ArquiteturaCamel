package com.camel.arquitetura.atendimento.system.processors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;

public class FilterClientesByBaseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ClienteResponseDTO[] clientes = exchange.getIn().getBody(ClienteResponseDTO[].class);
        AtendenteResponseDTO atendente = exchange.getProperty("atendente", AtendenteResponseDTO.class);
        List<ClienteResponseDTO> lista = Arrays.asList(clientes);
        
        lista = lista.stream().filter(cliente -> cliente.getBase().equals(atendente.getBase()))
            .collect(Collectors.toList());
        
        exchange.getIn().setBody(lista);
    }

}
