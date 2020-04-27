package com.camel.arquitetura.atendimento.system.processors;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.OrdemServico;

public class FilterByStatusProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        boolean status = exchange.getIn().getHeader("os-status", boolean.class);
        OrdemServico[] ordens = exchange.getIn().getBody(OrdemServico[].class);
        List<OrdemServico> response = new ArrayList<>();
        
        for (OrdemServico ordem : ordens) {
            if (ordem.isAtendida() == status)
                response.add(ordem);
        }
        
        exchange.getIn().setBody(response);        
    }

}
