package com.camel.arquitetura.atendimento.system.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:hello-world")
            .setBody(constant("Hello World!"));
        
    }

}
