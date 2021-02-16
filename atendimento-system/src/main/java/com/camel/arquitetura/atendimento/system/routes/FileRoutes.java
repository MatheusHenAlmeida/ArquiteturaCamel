package com.camel.arquitetura.atendimento.system.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:gera-txt")
                .setProperty("id", simple("${body.getId()}"))
                .to("velocity://templates/ordem.vm")
                .toD("file:os?fileName=ordem-servico-${header.id}.txt");
    }
}
