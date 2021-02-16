package com.camel.arquitetura.atendimento.system.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PDFRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:gera-pdf")
                .to("velocity://templates/ordem.vm")
                .to("file:os?");
    }
}
