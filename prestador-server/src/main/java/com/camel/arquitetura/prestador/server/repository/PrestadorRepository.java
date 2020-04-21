package com.camel.arquitetura.prestador.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.camel.arquitetura.prestador.server.entity.Prestador;

@Repository
public interface PrestadorRepository extends CrudRepository<Prestador, Long>{

}
