package com.camel.arquitetura.atendimento.system.model.dto;

import java.io.Serializable;

import com.camel.arquitetura.atendimento.system.enums.BaseIdentifier;
import com.camel.arquitetura.atendimento.system.enums.Cargos;
import com.camel.arquitetura.atendimento.system.enums.Niveis;
import com.google.gson.annotations.SerializedName;

public class AtendenteResponseDTO implements Serializable {
    private Long id;
   
    private String nome;
   
    @SerializedName("cargo")
    private Cargos cargo;
    
    @SerializedName("base")
    private BaseIdentifier base;
    
    @SerializedName("nivel")
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

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public BaseIdentifier getBase() {
        return base;
    }

    public void setBase(BaseIdentifier base) {
        this.base = base;
    }

    public Niveis getNivel() {
        return nivel;
    }

    public void setNivel(Niveis nivel) {
        this.nivel = nivel;
    }
    
    
}
