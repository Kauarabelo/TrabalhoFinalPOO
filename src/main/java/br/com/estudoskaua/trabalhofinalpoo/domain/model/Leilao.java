package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa um leilão que contém produtos e instituições financeiras associadas.
 */
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

    /**
     * Construtor que inicializa um leilão com os parâmetros fornecidos.
     *
     * @param id                     O ID do leilão
     * @param descricao              A descrição do leilão
     * @param dataInicio             A data de início do leilão
     * @param dataFim                A data de término do leilão
     * @param dataVisitacao          A data de visitação do leilão
     * @param endereco               O endereço onde o leilão ocorre
     * @param cidade                 A cidade onde o leilão ocorre
     * @param estado                 O estado onde o leilão ocorre
     * @param produtos               A lista de produtos associados ao leilão
     * @param instituicoesFinanceiras A lista de instituições financeiras associadas ao leilão
     * @param status                 O status do leilão
     */
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

    /**
     * Retorna o ID do leilão.
     *
     * @return ID do leilão
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do leilão.
     *
     * @param id ID do leilão
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna a descrição do leilão.
     *
     * @return Descrição do leilão
     */
    public @NotNull(message = "Descrição não pode estar vazia") String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do leilão.
     *
     * @param descricao Descrição do leilão
     */
    public void setDescricao(@NotNull(message = "Descrição não pode estar vazia") String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a data de início do leilão.
     *
     * @return Data de início do leilão
     */
    public @NotNull(message = "Data de início não pode ser nula") LocalDateTime getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do leilão.
     *
     * @param dataInicio Data de início do leilão
     */
    public void setDataInicio(@NotNull(message = "Data de início não pode ser nula") LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Retorna a data de término do leilão.
     *
     * @return Data de término do leilão
     */
    public @NotNull(message = "Data de término não pode ser nula") LocalDateTime getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de término do leilão.
     *
     * @param dataFim Data de término do leilão
     */
    public void setDataFim(@NotNull(message = "Data de término não pode ser nula") LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Retorna a data de visitação do leilão.
     *
     * @return Data de visitação do leilão
     */
    public @NotNull(message = "Data de visitação não pode estar vazia") LocalDateTime getDataVisitacao() {
        return dataVisitacao;
    }

    /**
     * Define a data de visitação do leilão.
     *
     * @param dataVisitacao Data de visitação do leilão
     */
    public void setDataVisitacao(@NotNull(message = "Data de visitação não pode estar vazia") LocalDateTime dataVisitacao) {
        this.dataVisitacao = dataVisitacao;
    }

    /**
     * Retorna o endereço onde o leilão ocorre.
     *
     * @return Endereço do leilão
     */
    public @NotNull(message = "Endereço não pode estar vazio") String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço onde o leilão ocorre.
     *
     * @param endereco Endereço do leilão
     */
    public void setEndereco(@NotNull(message = "Endereço não pode estar vazio") String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna a cidade onde o leilão ocorre.
     *
     * @return Cidade do leilão
     */
    public @NotNull(message = "Cidade não pode estar vazia") String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade onde o leilão ocorre.
     *
     * @param cidade Cidade do leilão
     */
    public void setCidade(@NotNull(message = "Cidade não pode estar vazia") String cidade) {
        this.cidade = cidade;
    }

    /**
     * Retorna o estado onde o leilão ocorre.
     *
     * @return Estado do leilão
     */
    public @NotNull(message = "Estado não pode estar vazio") String getEstado() {
        return estado;
    }

    /**
     * Define o estado onde o leilão ocorre.
     *
     * @param estado Estado do leilão
     */
    public void setEstado(@NotNull(message = "Estado não pode estar vazio") String estado) {
        this.estado = estado;
    }

    /**
     * Retorna a lista de produtos associados ao leilão.
     *
     * @return Lista de produtos do leilão
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Define a lista de produtos associados ao leilão.
     *
     * @param produtos Lista de produtos do leilão
     */
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * Retorna a lista de instituições financeiras associadas ao leilão.
     *
     * @return Lista de instituições financeiras do leilão
     */
    public List<InstituicaoFinanceira> getInstituicoesFinanceiras() {
        return instituicoesFinanceiras;
    }

    /**
     * Define a lista de instituições financeiras associadas ao leilão.
     *
     * @param instituicoesFinanceiras Lista de instituições financeiras do leilão
     */
    public void setInstituicoesFinanceiras(List<InstituicaoFinanceira> instituicoesFinanceiras) {
        this.instituicoesFinanceiras = instituicoesFinanceiras;
    }

    /**
     * Retorna o status do leilão.
     *
     * @return Status do leilão
     */
    public @NotNull(message = "Status não pode ser nulo") Status getStatus() {
        return status;
    }

    /**
     * Define o status do leilão.
     *
     * @param status Status do leilão
     */
    public void setStatus(@NotNull(message = "Status não pode ser nulo") Status status) {
        this.status = status;
    }
}
