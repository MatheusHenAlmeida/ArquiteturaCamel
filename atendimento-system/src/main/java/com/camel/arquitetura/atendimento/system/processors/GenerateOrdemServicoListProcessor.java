package com.camel.arquitetura.atendimento.system.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.arquitetura.atendimento.system.model.OrdemServico;

public class GenerateOrdemServicoListProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Map<String, Object>> itens = (List<Map<String, Object>>) exchange.getIn().getBody();
        List<OrdemServico> ordens = new ArrayList<>();
        
        itens.forEach(map -> {
            OrdemServico ordem = new OrdemServico();
            ordem.setId(Long.parseLong(map.get("id").toString()));
            ordem.setClienteEmail(map.get("cliente_email").toString());
            ordem.setClienteEndereco(map.get("cliente_endereco").toString());
            ordem.setClienteNome(map.get("cliente_nome").toString());
            ordem.setClienteTelefone(map.get("cliente_telefone").toString());
            ordem.setPrestadorEmail(map.get("prestador_email").toString());
            ordem.setPrestadorNome(map.get("prestador_nome").toString());
            ordem.setPrestadorTelefone(map.get("prestador_telefone").toString());
            ordem.setDescricao(map.get("descricao").toString());
            ordem.setAtendida((boolean) map.get("atendida")); 
            
            ordens.add(ordem);
        });
        
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setBody(ordens);
    }
}
