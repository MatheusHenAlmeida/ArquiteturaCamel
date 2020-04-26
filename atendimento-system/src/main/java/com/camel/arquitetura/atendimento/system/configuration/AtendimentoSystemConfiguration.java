package com.camel.arquitetura.atendimento.system.configuration;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtendimentoSystemConfiguration {
    @Autowired
    private CamelContext camelContext;
    
    @Bean
    public ProducerTemplate getProducerTemplate() {
        return camelContext.createProducerTemplate();
    }
}
