package com.camel.arquitetura.prestador.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.prestador.server.entity.Prestador;
import com.camel.arquitetura.prestador.server.model.dto.AddPrestadorDTO;
import com.camel.arquitetura.prestador.server.repository.PrestadorRepository;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {
    
    @Autowired
    private PrestadorRepository prestadorRepository;

    @GetMapping
    public List<Prestador> getAll() {
        List<Prestador> prestadores = new ArrayList<Prestador>();
        Iterable<Prestador> prestadoresFromRepository = prestadorRepository.findAll();
        
        prestadoresFromRepository.forEach(prestador -> prestadores.add(prestador));       
        return prestadores;
    }
    
    @GetMapping("/{id}")
    public Prestador getById(@PathVariable Long id) {
        Optional<Prestador> prestador = prestadorRepository.findById(id);
        
        if (prestador.isPresent())
            return prestador.get();
        return null;
    }
    
    @PostMapping("/create")
    public Prestador createPrestador(@RequestBody AddPrestadorDTO addPrestadorDTO) {
        if (addPrestadorDTO.isValid()) {
            Prestador prestador = new Prestador();
            prestador.setBase(addPrestadorDTO.getBase());
            prestador.setEmail(addPrestadorDTO.getEmail());
            prestador.setEndereco(addPrestadorDTO.getEndereco());
            prestador.setRazaoSocial(addPrestadorDTO.getRazaoSocial());
            prestador.setTelefone(addPrestadorDTO.getTelefone());
            Prestador novoPrestador = prestadorRepository.save(prestador);
            return novoPrestador;
        }
        return null;
    }
    
    @DeleteMapping("/delete/{id}")
    public Prestador removePrestador(@PathVariable Long id) {
        Prestador prestadorRemovido = prestadorRepository.findById(id).get();
        prestadorRepository.deleteById(id);
        return prestadorRemovido;
    }
}
