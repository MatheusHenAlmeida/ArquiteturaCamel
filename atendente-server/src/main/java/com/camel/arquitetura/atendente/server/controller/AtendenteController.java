package com.camel.arquitetura.atendente.server.controller;

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

import com.camel.arquitetura.atendente.server.entity.Atendente;
import com.camel.arquitetura.atendente.server.enums.Cargos;
import com.camel.arquitetura.atendente.server.model.dto.AddAtendenteDTO;
import com.camel.arquitetura.atendente.server.model.dto.RemoveAtendenteDTO;
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
    
    @PostMapping("/create")
    public Atendente createAtendente(@RequestBody AddAtendenteDTO addAtendenteDTO) {
        Long userId = addAtendenteDTO.getUserId();
        Cargos cargo = addAtendenteDTO.getCargo();
        
        Atendente atendente = atendenteRepository.findById(userId).get();
                
        if (atendente.getCargo().ordinal() > cargo.ordinal()) {
            Atendente novoAtendente = new Atendente();
            novoAtendente.setNome(addAtendenteDTO.getNome());
            novoAtendente.setBase(addAtendenteDTO.getBase());
            novoAtendente.setCargo(addAtendenteDTO.getCargo());
            novoAtendente.setNivel(addAtendenteDTO.getNivel());
            Atendente atendenteRegistrado = atendenteRepository.save(novoAtendente);
            return atendenteRegistrado;
        }
            
        return null;
    }
    
    @DeleteMapping("/remove")
    public Atendente removeAtendente(@RequestBody RemoveAtendenteDTO removeAtendenteDTO) {
        Atendente requirer = atendenteRepository.findById(removeAtendenteDTO.getUserId()).get();
        Atendente atendenteToBeRemoved =  atendenteRepository.findById(removeAtendenteDTO.getRemoveId()).get();
        
        if (!requirer.getCargo().equals(Cargos.SUPERVISOR) ||
                requirer.getId().equals(atendenteToBeRemoved.getId()) || 
                atendenteToBeRemoved.getCargo().equals(Cargos.SUPERVISOR))
            return null;
       
        atendenteRepository.deleteById(removeAtendenteDTO.getRemoveId());
        return atendenteToBeRemoved;
    }
}
