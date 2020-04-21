package com.camel.arquitetura.prestador.server.model.dto;

import com.camel.arquitetura.prestador.server.enums.BaseIdentifier;

public class AddPrestadorDTO {
    private String razaoSocial;
    
    private BaseIdentifier base;
    
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
        return this.base != null && this.telefone != null
                && this.email !=null && this.razaoSocial != null;
    }
}
