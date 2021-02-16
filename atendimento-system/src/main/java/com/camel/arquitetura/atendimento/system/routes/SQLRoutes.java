package com.camel.arquitetura.atendimento.system.routes;

import com.camel.arquitetura.atendimento.system.exceptions.CredentialsInvalidException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.camel.arquitetura.atendimento.system.enums.Cargos;
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
        
        from("direct:update-ordem-by-id")
            .to("direct:supervisor-analista-credentials")
            .setProperty("userId", simple("${header.id}"))
            .toD("sql:UPDATE ordem_servico SET atendida = true WHERE id = ${header.id}")
            .to("direct:get-ordem-by-id")
            .unmarshal().json(JsonLibrary.Gson, OrdemServico.class);

        from("direct:supervisor-analista-credentials")
            .setHeader("supervisor",constant(Cargos.SUPERVISOR)) 
            .setHeader("analista",constant(Cargos.ANALISTA)) 
            .choice()
                .when(simple("${header.user.getCargo()} != ${header.supervisor} and "
                        + "${header.user.getCargo()} != ${header.analista}"))
                    .throwException(new CredentialsInvalidException("O usuário precisa ser um analista ou supervisor para esta operação"))
                .endChoice()
            .end();
        
        from("direct:supervisor-credentials")
            .setHeader("supervisor",constant(Cargos.SUPERVISOR)) 
            .choice()
                .when(simple("${header.user.getCargo()} != ${header.supervisor}"))
                    .throwException(new CredentialsInvalidException("Somente supervisores podem apagar clientes"))
                .endChoice()
            .end();
        
        from("direct:insert-ordem-servico")
            .log("Cliente: ${header.cliente.getRazaoSocial()} - "
                    + "Prestador: ${header.prestador.getRazaoSocial()} - "
                    + "Descricao: ${header.descricao}")
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
