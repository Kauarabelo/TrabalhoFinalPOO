package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Representa um leilão que contém produtos e instituições financeiras associadas.
 */
@Entity
@Table(name = "leilao")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "produtos"})
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Leilao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Descrição não pode estar vazia")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Data de início não pode ser nula")
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de término não pode ser nula")
    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @NotNull(message = "Data de visitação não pode estar vazia")
    @Column(name = "data_visitacao")
    private LocalDateTime dataVisitacao;

    @NotNull(message = "Endereço não pode estar vazio")
    @Column(name = "endereco")
    private String endereco;

    @NotNull(message = "Cidade não pode estar vazia")
    @Column(name = "cidade")
    private String cidade;

    @NotNull(message = "Estado não pode estar vazio")
    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"produtos", "instituicoesFinanceiras"}) // Evitar problemas de serialização
    private Cliente cliente;

    @OneToMany(mappedBy = "leilao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("leilao") // Ignora leilao para evitar referência circular
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "leilao_instituicao_financeira",
            joinColumns = @JoinColumn(name = "leilao_id"),
            inverseJoinColumns = @JoinColumn(name = "instituicao_financeira_id")
    )
    @JsonIgnoreProperties("leiloes")
    private List<InstituicaoFinanceira> instituicoesFinanceiras = new ArrayList<>();

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Construtores
    public Leilao() {}

    public Leilao(
            Long id, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim,
            LocalDateTime dataVisitacao, String endereco, String cidade,
            String estado, List<Produto> produtos, List<InstituicaoFinanceira> instituicoesFinanceiras,
            Status status
    ) {
        this.id = id;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataVisitacao = dataVisitacao;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.produtos = produtos;
        this.instituicoesFinanceiras = instituicoesFinanceiras;
        this.status = calcularStatus();
    }

    @PrePersist
    @PreUpdate
    private void atualizarStatus() {
        this.status = calcularStatus();
    }

    private Status calcularStatus() {
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(dataInicio)) {
            return Status.ABERTO;
        } else if (agora.isAfter(dataFim)) {
            return Status.FINALIZADO;
        } else {
            return Status.EM_ANDAMENTO;
        }
    }

    public boolean isAtivo() {
        LocalDateTime agora = LocalDateTime.now();
        return agora.isAfter(dataInicio) && agora.isBefore(dataFim);
    }

}
