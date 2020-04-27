package com.camel.arquitetura.atendimento.system.model;

public class OrdemServico {
    private Long id;
    private String prestadorNome;
    private String prestadorEmail;
    private String prestadorTelefone;
    private String clienteNome;
    private String clienteEndereco;
    private String clienteEmail;
    private String clienteTelefone;
    private String descricao;
    private boolean atendida;
    
    public boolean isAtendida() {
        return atendida;
    }
    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPrestadorNome() {
        return prestadorNome;
    }
    public void setPrestadorNome(String prestadorNome) {
        this.prestadorNome = prestadorNome;
    }
    public String getPrestadorEmail() {
        return prestadorEmail;
    }
    public void setPrestadorEmail(String prestadorEmail) {
        this.prestadorEmail = prestadorEmail;
    }
    public String getPrestadorTelefone() {
        return prestadorTelefone;
    }
    public void setPrestadorTelefone(String prestadorTelefone) {
        this.prestadorTelefone = prestadorTelefone;
    }
    public String getClienteNome() {
        return clienteNome;
    }
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    public String getClienteEndereco() {
        return clienteEndereco;
    }
    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }
    public String getClienteEmail() {
        return clienteEmail;
    }
    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }
    public String getClienteTelefone() {
        return clienteTelefone;
    }
    public void setClienteTelefone(String clienteTelefone) {
        this.clienteTelefone = clienteTelefone;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
