package com.camel.arquitetura.atendente.server.model.dto;

public class RemoveAtendenteDTO {
    private Long userId;
    private Long removeId;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getRemoveId() {
        return removeId;
    }
    public void setRemoveId(Long removeId) {
        this.removeId = removeId;
    }
}
