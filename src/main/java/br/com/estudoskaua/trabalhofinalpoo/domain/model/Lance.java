package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

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
    private Date dataLance;

    private Date dateTime = new Date();

    // Construtores
    public Lance() {}

    public Lance(Leilao leilao, Cliente cliente, Produto produto, Double valor, Date dataLance) {
        this.leilao = leilao;
        this.cliente = cliente;
        this.produto = produto;
        this.valor = valor;
        this.dataLance = dataLance;
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

    public Date getDataLance() {
        return dataLance;
    }

    //Ao adicinar um novo lance, a data e hora será adicionada automaticamente
    public void setDataLance(Date dataLance) {
        dataLance = dateTime;
        this.dataLance = dataLance;
    }

    public Leilao getLeilao() {
        return leilao;
    }

    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
}
