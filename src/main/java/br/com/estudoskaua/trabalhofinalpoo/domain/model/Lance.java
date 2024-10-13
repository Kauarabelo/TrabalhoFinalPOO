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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataLance() {
        return dataLance;
    }

    public void setDataLance(LocalDateTime dataLance) {
        this.dataLance = dataLance;
    }

    public Leilao getLeilao() {
        return leilao;
    }

    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
}
