package com.camel.arquitetura.atendimento.system.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.camel.arquitetura.atendimento.system.model.OrdemServico;
import com.camel.arquitetura.atendimento.system.processors.FilterByStatusProcessor;
import com.camel.arquitetura.atendimento.system.processors.GenerateOrdemServicoListProcessor;
import com.camel.arquitetura.atendimento.system.processors.GetFirstOrdemServicoProcessor;

@Component
public class SQLRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:get-ordens-servico")
            .to("sql:SELECT * FROM ordem_servico")
            .process(new GenerateOrdemServicoListProcessor())
            .marshal().json(JsonLibrary.Gson);
        
        from("direct:get-ordens-by-status")
            .to("direct:get-ordens-servico")
            .unmarshal().json(JsonLibrary.Gson, OrdemServico[].class)
            .process(new FilterByStatusProcessor())
            .marshal().json(JsonLibrary.Gson);
        
        from("direct:get-ordem-by-id")
            // :#id serve se houver no header e usando componente sql
            .to("sql:SELECT * FROM ordem_servico WHERE id = :#id")
            .process(new GenerateOrdemServicoListProcessor())
            .process(new GetFirstOrdemServicoProcessor())
            .marshal().json(JsonLibrary.Gson);
        
        from("direct:insert-ordem-servico")
            .log("${header.cliente.getRazaoSocial()} - ${header.prestador.getRazaoSocial()} - ${header.descricao}")
            .toD("sql:INSERT INTO "
                    + "ordem_servico(prestador_nome, prestador_email, prestador_telefone, cliente_nome, "
                    + "cliente_endereco, cliente_email, cliente_telefone, descricao) "
                    + "VALUES ('${header.prestador.getRazaoSocial()}', '${header.prestador.getEmail()}', "
                    + "'${header.prestador.getTelefone()}', '${header.cliente.getRazaoSocial()}', "
                    + "'${header.cliente.getEndereco()}', '${header.cliente.getEmail()}',"
                    + "'${header.cliente.getTelefone()}', '${header.descricao}')")
            .to("sql:SELECT * FROM ordem_servico WHERE id = LAST_INSERT_ID()")
            .process(new GenerateOrdemServicoListProcessor())
            .process(new GetFirstOrdemServicoProcessor())
            .marshal().json(JsonLibrary.Gson);
    }

}
