package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class LanceDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Valor do lance é obrigatório")
    @Positive(message = "Valor do lance deve ser positivo")
    private Double valor;

    @NotNull(message = "Data do lance é obrigatória")
    private LocalDateTime dataLance;

    // Getters e Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataLance() {
        return dataLance;
    }

    public void setDataLance(LocalDateTime dataLance) {
        this.dataLance = dataLance;
    }
}
