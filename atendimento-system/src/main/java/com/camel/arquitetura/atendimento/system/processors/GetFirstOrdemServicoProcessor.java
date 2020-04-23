package com.camel.arquitetura.atendimento.system.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.OrdemServico;

public class GetFirstOrdemServicoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        OrdemServico[] ordens = exchange.getIn().getBody(OrdemServico[].class);
        
        exchange.getOut().setBody(ordens[0]);
    }
    
}
