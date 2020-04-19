package com.camel.arquitetura.cliente.server.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.camel.arquitetura.cliente.server.enums.BaseIdentifier;

public class AddClienteDTO {
    private String razaoSocial;
    
    private BaseIdentifier base;
    
    private String segmento;
    
    private String endereco;
    
    private String email;
    
    private String telefone;

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public BaseIdentifier getBase() {
        return base;
    }

    public void setBase(BaseIdentifier base) {
        this.base = base;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public boolean isValid() {
        return this.base != null &&
                this.email != null &&
                this.endereco != null &&
                this.razaoSocial != null;
        
    }
}
