package com.camel.arquitetura.atendente.server.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.camel.arquitetura.atendente.server.enums.BaseIdentifier;
import com.camel.arquitetura.atendente.server.enums.Cargos;
import com.camel.arquitetura.atendente.server.enums.Niveis;

@Entity
public class Atendente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private BaseIdentifier base;
    
    @Enumerated(EnumType.STRING)
    private Cargos cargo;
    
    @Enumerated(EnumType.STRING)
    private Niveis nivel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BaseIdentifier getBase() {
        return base;
    }

    public void setBase(BaseIdentifier base) {
        this.base = base;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public Niveis getNivel() {
        return nivel;
    }

    public void setNivel(Niveis nivel) {
        this.nivel = nivel;
    }
}
