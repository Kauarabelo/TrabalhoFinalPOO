package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Representa um lance feito por um cliente em um leilão para um produto específico.
 */
@Entity
@Table(name = "lance")
public class Lance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leilao_id", nullable = false)
    private Leilao leilao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull(message = "Valor do lance não pode ser nulo")
    private Double valor;

    @NotNull(message = "Data do lance não pode ser nula")
    @Column(name = "data_lance")
    private LocalDateTime dataLance;

    /**
     * Construtor padrão que inicializa a data do lance com a data/hora atual.
     */
    public Lance() {
        this.dataLance = LocalDateTime.now(); // Inicializa com a data/hora atual
    }

    /**
     * Construtor que inicializa um lance com os parâmetros fornecidos.
     *
     * @param leilao  O leilão ao qual o lance está associado
     * @param cliente O cliente que fez o lance
     * @param produto O produto que está sendo leiloado
     * @param valor   O valor do lance
     */
    public Lance(Leilao leilao, Cliente cliente, Produto produto, Double valor) {
        this.leilao = leilao;
        this.cliente = cliente;
        this.produto = produto;
        this.valor = valor;
        this.dataLance = LocalDateTime.now(); // Define a data/hora no momento da criação do lance
    }

    // Getters e Setters

    /**
     * Retorna o ID do lance.
     *
     * @return ID do lance
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do lance.
     *
     * @param id ID do lance
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o valor do lance.
     *
     * @return Valor do lance
     */
    public Double getValor() {
        return valor;
    }

    /**
     * Define o valor do lance.
     *
     * @param valor Valor do lance
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * Retorna o produto associado a este lance.
     *
     * @return Produto associado ao lance
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado a este lance.
     *
     * @param produto Produto associado ao lance
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna o cliente que fez o lance.
     *
     * @return Cliente que fez o lance
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente que fez o lance.
     *
     * @param cliente Cliente que fez o lance
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a data e hora em que o lance foi feito.
     *
     * @return Data do lance
     */
    public LocalDateTime getDataLance() {
        return dataLance;
    }

    /**
     * Define a data e hora em que o lance foi feito.
     *
     * @param dataLance Data do lance
     */
    public void setDataLance(LocalDateTime dataLance) {
        this.dataLance = dataLance;
    }

    /**
     * Retorna o leilão ao qual este lance está associado.
     *
     * @return Leilão associado ao lance
     */
    public Leilao getLeilao() {
        return leilao;
    }

    /**
     * Define o leilão ao qual este lance está associado.
     *
     * @param leilao Leilão associado ao lance
     */
    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
}
