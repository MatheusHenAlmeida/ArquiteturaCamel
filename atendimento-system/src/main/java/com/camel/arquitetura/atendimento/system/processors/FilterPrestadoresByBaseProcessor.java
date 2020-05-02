package com.camel.arquitetura.atendimento.system.processors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;

public class FilterPrestadoresByBaseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        PrestadorResponseDTO[] prestadores = exchange.getIn().getBody(PrestadorResponseDTO[].class);
        AtendenteResponseDTO atendente = exchange.getProperty("atendente", AtendenteResponseDTO.class);
        List<PrestadorResponseDTO> lista = Arrays.asList(prestadores);
        
        lista = lista.stream().filter(prestador -> prestador.getBase().equals(atendente.getBase()))
            .collect(Collectors.toList());
        
        exchange.getIn().setBody(lista);
    }

}
