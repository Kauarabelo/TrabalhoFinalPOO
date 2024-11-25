package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Representa um lance feito por um cliente em um leilão para um produto específico.
 */
@Entity
@Table(name = "lance")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Lance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "leilao_id", nullable = false)
    private Leilao leilao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"produtos", "instituicoesFinanceiras"}) // Evitar problemas de serialização
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    @JsonBackReference
    private Produto produto;

    @NotNull(message = "Valor do lance não pode ser nulo")
    @Min(value = 0, message = "Valor do lance deve ser maior que zero")
    private Double valor;

    @NotNull(message = "Data do lance não pode ser nula")
    @Column(name = "data_lance", nullable = false, updatable = false)
    private LocalDateTime dataLance;

    /**
     * Construtor padrão que inicializa a data do lance com a data/hora atual.
     */
    public Lance() {
        this.dataLance = LocalDateTime.now();
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
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor do lance deve ser maior que zero.");
        }
        this.leilao = leilao;
        this.cliente = cliente;
        this.produto = produto;
        this.valor = valor;
        this.dataLance = LocalDateTime.now();
    }
}
