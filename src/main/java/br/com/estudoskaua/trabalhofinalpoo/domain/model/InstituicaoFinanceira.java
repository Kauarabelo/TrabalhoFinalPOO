package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma instituição financeira que pode estar associada a leilões.
 * Inclui informações como nome e CNPJ da instituição.
 */
@Entity
@Table(name = "instituicao_financeira")
public class InstituicaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "CNPJ não pode ser vazio")
    @Size(min = 14, max = 14, message = "CNPJ deve ter exatamente 14 caracteres")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter apenas números")
    @Column(unique = true, nullable = false)
    private String cnpj;

    @PastOrPresent(message = "Data de cadastro deve ser no passado ou presente")
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @ManyToMany(mappedBy = "instituicoesFinanceiras", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore // Evita loops durante a serialização JSON
    private List<Leilao> leiloes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "leilao_id")
    private Leilao leilao;

    // Construtor padrão
    public InstituicaoFinanceira() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Construtor com parâmetros
    public InstituicaoFinanceira(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Leilao> getLeiloes() {
        return leiloes;
    }

    public void setLeiloes(List<Leilao> leiloes) {
        this.leiloes = leiloes;
    }

    public Leilao getLeilao() {
        return leilao;
    }

    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }

    // Métodos para comparação e hash
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstituicaoFinanceira that = (InstituicaoFinanceira) o;
        return Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }

    @Override
    public String toString() {
        return "InstituicaoFinanceira{id=" + id + ", nome='" + nome + "', cnpj='" + cnpj + "', dataCadastro=" + dataCadastro + "}";
    }
}
