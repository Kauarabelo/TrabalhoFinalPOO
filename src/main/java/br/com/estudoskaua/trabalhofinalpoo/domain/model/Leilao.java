package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "leilao")
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


    @OneToMany(mappedBy = "leilao", fetch = FetchType.EAGER)
    private List<Produto> produtos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "leilao_instituicao_financeira",
            joinColumns = @JoinColumn(name = "leilao_id"),
            inverseJoinColumns = @JoinColumn(name = "instituicao_financeira_id"))
    private List<InstituicaoFinanceira> instituicoesFinanceiras;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Construtores
    public Leilao() {
    }

    public Leilao(Long id, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim, LocalDateTime dataVisitacao,
                  String endereco, String cidade, String estado, List<Produto> produtos,
                  List<InstituicaoFinanceira> instituicoesFinanceiras, Status status) {
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
        this.status = status;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Descrição não pode estar vazia") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull(message = "Descrição não pode estar vazia") String descricao) {
        this.descricao = descricao;
    }

    public @NotNull(message = "Data de início não pode ser nula") LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(@NotNull(message = "Data de início não pode ser nula") LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public @NotNull(message = "Data de término não pode ser nula") LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(@NotNull(message = "Data de término não pode ser nula") LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public @NotNull(message = "Data de visitação não pode estar vazia") LocalDateTime getDataVisitacao() {
        return dataVisitacao;
    }

    public void setDataVisitacao(@NotNull(message = "Data de visitação não pode estar vazia") LocalDateTime dataVisitacao) {
        this.dataVisitacao = dataVisitacao;
    }

    public @NotNull(message = "Endereço não pode estar vazio") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotNull(message = "Endereço não pode estar vazio") String endereco) {
        this.endereco = endereco;
    }

    public @NotNull(message = "Cidade não pode estar vazia") String getCidade() {
        return cidade;
    }

    public void setCidade(@NotNull(message = "Cidade não pode estar vazia") String cidade) {
        this.cidade = cidade;
    }

    public @NotNull(message = "Estado não pode estar vazio") String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull(message = "Estado não pode estar vazio") String estado) {
        this.estado = estado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<InstituicaoFinanceira> getInstituicoesFinanceiras() {
        return instituicoesFinanceiras;
    }

    public void setInstituicoesFinanceiras(List<InstituicaoFinanceira> instituicoesFinanceiras) {
        this.instituicoesFinanceiras = instituicoesFinanceiras;
    }

    public @NotNull(message = "Status não pode ser nulo") Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status não pode ser nulo") Status status) {
        this.status = status;
    }
}
