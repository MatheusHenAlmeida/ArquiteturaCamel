package com.camel.arquitetura.atendimento.system.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.camel.arquitetura.atendimento.system.model.dto.AtendenteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.ClienteResponseDTO;
import com.camel.arquitetura.atendimento.system.model.dto.PrestadorResponseDTO;

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
        
        from("direct:get-atendente")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("atendentes/${body}"))
            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
            .setBody(simple(""))
            .to("http4://" + atendenteUrl)
            .unmarshal().json(JsonLibrary.Gson, AtendenteResponseDTO.class);
        
        from("direct:get-cliente-by-name")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("clientes/razao-social/${body}"))
            .setBody(simple(""))
            .to("http4://" + clienteUrl)
            .unmarshal().json(JsonLibrary.Gson, ClienteResponseDTO.class)
            .choice()
                .when(simple("${header.base} != ${body.getBase()}"))
                    .setBody(constant(""))
                .endChoice()
            .end();
        
        from("direct:get-prestador-by-base")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
            .setHeader(Exchange.HTTP_PATH, simple("prestadores/base/${body}"))
            .setBody(simple(""))
            .to("http4://" + prestadorUrl)
            .unmarshal().json(JsonLibrary.Gson, PrestadorResponseDTO.class)
            ;
    }

}
