package com.camel.arquitetura.atendimento.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prestadores")
public class PrestadoresController {
    // Só supervisor pode apagar prestadores e eles devem ser da mesma base
    // Atendentes só veêm prestadores da mesma base
    // Atendentes podem registrar prestadores de mesma região
}
