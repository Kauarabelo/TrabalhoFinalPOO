package br.com.estudoskaua.trabalhofinalpoo.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO {

    @NotBlank(message = "Nome do produto não pode ser vazio")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "Valor do produto não pode ser nulo")
    private Double valor;

    private String imagemUrl;

    @NotNull(message = "ID do leilão não pode ser nulo")
    private Long leilaoId; // Para associar o produto a um leilão

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Long getLeilaoId() {
        return leilaoId;
    }

    public void setLeilaoId(Long leilaoId) {
        this.leilaoId = leilaoId;
    }
}

