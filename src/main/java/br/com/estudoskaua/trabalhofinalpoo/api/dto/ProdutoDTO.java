package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) para representar um produto.
 * Utilizado para transferir dados entre camadas da aplicação.
 *
 * @author Kaua
 */
public class ProdutoDTO {

    @NotBlank(message = "Nome do produto não pode ser vazio")
    @Size(max = 100, message = "Nome do produto não pode exceder 100 caracteres")
    private String nome; // Nome do produto

    @NotBlank(message = "Descrição não pode estar vazia")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    private String descricao; // Descrição do produto

    @NotNull(message = "Valor do produto não pode ser nulo")
    @Positive(message = "Valor do produto deve ser maior que zero")
    private Double valor; // Valor do produto

    @Pattern(regexp = "^(http|https)://.*$", message = "URL da imagem deve ser uma URL válida")
    private String imagemUrl; // URL da imagem do produto

    @NotNull(message = "ID do leilão não pode ser nulo")
    private Long leilaoId; // Para associar o produto a um leilão

    /**
     * Obtém o nome do produto.
     *
     * @return o nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome o nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a descrição do produto.
     *
     * @return a descrição do produto.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao a descrição do produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o valor do produto.
     *
     * @return o valor do produto.
     */
    public Double getValor() {
        return valor;
    }

    /**
     * Define o valor do produto.
     *
     * @param valor o valor do produto.
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * Obtém a URL da imagem do produto.
     *
     * @return a URL da imagem do produto.
     */
    public String getImagemUrl() {
        return imagemUrl;
    }

    /**
     * Define a URL da imagem do produto.
     *
     * @param imagemUrl a URL da imagem do produto.
     */
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    /**
     * Obtém o ID do leilão associado ao produto.
     *
     * @return o ID do leilão.
     */
    public Long getLeilaoId() {
        return leilaoId;
    }

    /**
     * Define o ID do leilão associado ao produto.
     *
     * @param leilaoId o ID do leilão.
     */
    public void setLeilaoId(Long leilaoId) {
        this.leilaoId = leilaoId;
    }
}
