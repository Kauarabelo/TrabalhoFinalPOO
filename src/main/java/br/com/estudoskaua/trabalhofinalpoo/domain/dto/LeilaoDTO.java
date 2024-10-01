package br.com.estudoskaua.trabalhofinalpoo.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class LeilaoDTO {

    @NotNull(message = "Descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "IDs dos produtos são obrigatórios")
    private List<Long> produtoIds;

    @NotNull(message = "IDs das instituições financeiras são obrigatórios")
    private List<Long> instituicaoFinanceiraIds;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de término é obrigatória")
    private LocalDateTime dataFim;

    @NotNull(message = "Data de visitação é obrigatória")
    private LocalDateTime dataVisitacao;

    @NotNull(message = "Endereço é obrigatório")
    private String endereco;

    @NotNull(message = "Cidade é obrigatória")
    private String cidade;

    @NotNull(message = "Estado é obrigatório")
    private String estado;

    // Getters e Setters

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Long> getProdutoIds() {
        return produtoIds;
    }

    public void setProdutoIds(List<Long> produtoIds) {
        this.produtoIds = produtoIds;
    }

    public List<Long> getInstituicaoFinanceiraIds() {
        return instituicaoFinanceiraIds;
    }

    public void setInstituicaoFinanceiraIds(List<Long> instituicaoFinanceiraIds) {
        this.instituicaoFinanceiraIds = instituicaoFinanceiraIds;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataVisitacao() {
        return dataVisitacao;
    }

    public void setDataVisitacao(LocalDateTime dataVisitacao) {
        this.dataVisitacao = dataVisitacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
