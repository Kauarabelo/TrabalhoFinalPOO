package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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

    @OneToMany(mappedBy = "leilao", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("leilao") // Ignora leilao para evitar referência circular
    private List<Produto> produtos = new ArrayList<>();

    // Adicionando a relação com InstituicoesFinanceiras
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "leilao_instituicao_financeira",
            joinColumns = @JoinColumn(name = "leilao_id"),
            inverseJoinColumns = @JoinColumn(name = "instituicao_financeira_id")
    )
    @JsonIgnoreProperties("leiloes") // Evitar problemas de serialização circular
    private List<InstituicaoFinanceira> instituicoesFinanceiras = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

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
