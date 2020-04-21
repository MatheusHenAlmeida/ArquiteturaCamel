package com.camel.arquitetura.prestador.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.camel.arquitetura.prestador.server.entity.Prestador;
import com.camel.arquitetura.prestador.server.enums.BaseIdentifier;

@Repository
public interface PrestadorRepository extends CrudRepository<Prestador, Long>{
    List<Prestador> findByBase(BaseIdentifier base);
}
