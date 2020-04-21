package com.camel.arquitetura.atendimento.system;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AtendimentoSystemApplication {
    @Autowired
    private CamelContext camelContext;
    
    @Bean
    public ProducerTemplate getProducerTemplate() {
        return camelContext.createProducerTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(AtendimentoSystemApplication.class, args);
	}

}
