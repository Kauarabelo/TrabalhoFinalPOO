package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Representa uma instituição financeira que pode estar associada a leilões.
 * Inclui informações como nome e CNPJ da instituição.
 */
@Entity
public class InstituicaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String nome;

    //FIXME
    @NotNull
    @NotEmpty
    @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres")
    @Column(unique = true) // Garante que o cnpj seja único na tabela
    private String cnpj;

    @ManyToMany(mappedBy = "instituicoesFinanceiras", fetch = FetchType.EAGER)
    @JsonIgnore // Ignora a propriedade na serialização JSON para evitar loops
    private List<Leilao> leiloes;

    // Construtor padrão
    public InstituicaoFinanceira() {
    }

    // Construtor com parâmetros
    public InstituicaoFinanceira(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
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

    public List<Leilao> getLeiloes() {
        return leiloes;
    }

    public void setLeiloes(List<Leilao> leiloes) {
        this.leiloes = leiloes;
    }

    @Override
    public String toString() {
        return "InstituicaoFinanceira{id=" + id + ", nome='" + nome + "', cnpj='" + cnpj + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstituicaoFinanceira that = (InstituicaoFinanceira) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}