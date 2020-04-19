package com.camel.arquitetura.atendente.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.atendente.server.entity.Atendente;
import com.camel.arquitetura.atendente.server.repository.AtendenteRepository;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {
    
    @Autowired
    private AtendenteRepository atendenteRepository;

    @GetMapping
    public List<Atendente> getAll() {
        List<Atendente> atendentes = new ArrayList<Atendente>();
        Iterable<Atendente> atendentesFromRepository = atendenteRepository.findAll();
        
        atendentesFromRepository.forEach(atendente -> {
            atendentes.add(atendente);
        });
        
        return atendentes;
    }
    
    @GetMapping("/{id}")
    public Atendente getById(@PathVariable Long id) {
        Optional<Atendente> atendente = atendenteRepository.findById(id);
        if (atendente.isPresent())
            return atendente.get();
        return null;
    }
}
