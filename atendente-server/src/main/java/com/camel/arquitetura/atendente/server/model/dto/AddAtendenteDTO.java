package com.camel.arquitetura.atendente.server.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.camel.arquitetura.atendente.server.enums.BaseIdentifier;
import com.camel.arquitetura.atendente.server.enums.Cargos;
import com.camel.arquitetura.atendente.server.enums.Niveis;

public class AddAtendenteDTO {
    private Long userId;
    
    private BaseIdentifier base;

    private Cargos cargo;

    private Niveis nivel;
    
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
