package com.camel.arquitetura.atendimento.system.routes;

import com.camel.arquitetura.atendimento.system.exceptions.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;
import com.camel.arquitetura.atendimento.system.processors.BuildCreateClienteProcessor;
import com.camel.arquitetura.atendimento.system.processors.BuildCreatePrestadorProcessor;
import com.camel.arquitetura.atendimento.system.processors.FilterClientesByBaseProcessor;
import com.camel.arquitetura.atendimento.system.processors.FilterPrestadoresByBaseProcessor;

@Component
public class HttpRoutes extends RouteBuilder {
    @Value("${hostname.atendente-server}")
    private String atendenteUrl;
    
    @Value("${hostname.cliente-server}")
    private String clienteUrl;
    
    @Value("${hostname.prestador-server}")
    private String prestadorUrl;

    @Override
    public void configure() throws Exception {
        from("direct:hello-world")
            .setBody(constant("Bem vindo ao sistema de assistencia tecnica"));
        
        // Rota de integracao com sistema de atendentes
        from("direct:get-atendente")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("atendentes/${body}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .doTry()
                .to("http4://" + atendenteUrl)
                .unmarshal().json(JsonLibrary.Gson, AtendenteResponseDTO.class)
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de atendentes"))
            .end()
            .choice()
                .when(simple("${body} == null"))
                    .throwException(new AtendenteNotFoundException("Não foi encontrado o antendente"))
                .endChoice()
            .end();
        
        // Rotas de integracao com o sistema de prestadores
        from("direct:get-prestadores")
            .setBody(simple("${header.userId}"))
            .to("direct:get-atendente")
            .setProperty("atendente", simple("${body}"))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .to("http4://" + prestadorUrl)
            .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO[].class)
            .process(new FilterPrestadoresByBaseProcessor());
        
        from("direct:get-prestador-by-id")
            .setBody(simple("${header.userId}"))
            .setProperty("ordemId", simple("${header.id}"))
            .to("direct:get-atendente")
            .setProperty("atendente", simple("${body}"))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores/${property.ordemId}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .to("http4://" + prestadorUrl)
            .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO.class)
            .choice()
                .when(simple("${body.getBase()} != ${property.atendente.getBase()}"))
                    .throwException(new OutsideClientException("Cliente não pertence à base do atendente"))
                .endChoice()
            .end();
        
        from("direct:create-prestador")
            .setProperty("dto", simple("${body}"))
            .setBody(simple("${header.userId}"))
            .to("direct:get-atendente")
            .setHeader("user", simple("${body}"))
            .to("direct:supervisor-analista-credentials")
            .process(new BuildCreatePrestadorProcessor())
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores/create"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .to("http4://" + prestadorUrl)
            .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO.class);
        
        from("direct:remove-prestador")
            .setProperty("clienteId", simple("${body}"))
            .setHeader("id", simple("${body}"))
            .to("direct:get-prestador-by-id")
            .setHeader("user", simple("${property.atendente}"))
            .to("direct:supervisor-credentials")
            .setBody(simple(""))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.DELETE))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores/delete/${property.clienteId}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .to("http4://" + prestadorUrl)
            .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO.class);
        
        from("direct:get-prestador-by-base")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores/base/${body}"))
            .setBody(simple(""))
            .doTry()
                .to("http4://" + prestadorUrl)
                .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO.class)
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de prestadores"))
            .end()
            .choice()
                .when(simple("${body} == null"))
                    .throwException(new PrestadorNotFoundException("Não existe prestador nesta base"))
                .endChoice()
            .end();
        
        // Rotas de integracao com sistema de clientes
        from("direct:get-clientes")
            .setBody(simple("${header.userId}"))
            .to("direct:get-atendente")
            .setProperty("atendente", simple("${body}"))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("clientes"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .doTry()
            .to("http4://" + clienteUrl)
                .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO[].class)
                .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de clientes"))
            .end()
            .process(new FilterClientesByBaseProcessor());
        
        from("direct:get-cliente-by-id")
            .setBody(simple("${header.userId}"))
            .setProperty("ordemId", simple("${header.id}"))
            .to("direct:get-atendente")
            .setProperty("atendente", simple("${body}"))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("clientes/${property.ordemId}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .doTry()
                .to("http4://" + clienteUrl)
                .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO.class)
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de clientes"))
            .end()
            .choice()
                .when(simple("${body.getBase()} != ${property.atendente.getBase()}"))
                    .throwException(new OutsideClientException("Cliente não pertence à base do atendente"))
                .endChoice()
            .end();
        
        from("direct:create-cliente")
            .setProperty("dto", simple("${body}"))
            .setBody(simple("${header.userId}"))
            .to("direct:get-atendente")
            .setHeader("user", simple("${body}"))
            .to("direct:supervisor-analista-credentials")
            .process(new BuildCreateClienteProcessor())
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.HTTP_PATH, simple("clientes/create"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .doTry()
                .to("http4://" + clienteUrl)
                .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO.class)
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de clientes"))
            .end();
        
        from("direct:remove-cliente")
            .setProperty("clienteId", simple("${body}"))
            .setHeader("id", simple("${body}"))
            .to("direct:get-cliente-by-id")
            .setHeader("user", simple("${property.atendente}"))
            .to("direct:supervisor-credentials")
            .setBody(simple(""))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.DELETE))
            .setHeader(Exchange.HTTP_PATH, simple("clientes/delete/${property.clienteId}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .to("http4://" + clienteUrl)
            .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO.class);
        
        from("direct:get-cliente-by-name")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("clientes/razao-social/${body}"))
            .setBody(simple(""))
            .doTry()
                .to("http4://" + clienteUrl)
                .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO.class)
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new ServerNotFoundException("Não foi possível acessar o servidor de clientes"))
            .end()
            .choice()
                .when(simple("${body} == null"))
                    .throwException(new ClientNotFoundException("Não foi possível encontrar o cliente desejado"))
                .endChoice()
                .when(simple("${header.base} != ${body.getBase()}"))
                    .throwException(new OutsideClientException("Só um atendente da mesma base do cliente pode abrir uma OS"))
                .endChoice()
            .end();
    }

}
