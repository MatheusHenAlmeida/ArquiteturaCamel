package com.camel.arquitetura.atendente.server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.camel.arquitetura.atendente.server.entity.Atendente;

@Repository
public interface AtendenteRepository extends CrudRepository<Atendente, Long> {
    Optional<Atendente> findById(Long id);
    Iterable<Atendente> findAll();
}
