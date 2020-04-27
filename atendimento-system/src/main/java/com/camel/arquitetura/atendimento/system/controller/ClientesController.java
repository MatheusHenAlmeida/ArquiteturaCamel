package com.camel.arquitetura.atendimento.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    // Só supervisor pode apagar clientes e eles devem ser da mesma base
    // Atendentes só veêm clientes da mesma base
    // Atendentes podem registrar clientes de mesma região
}
