package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class InstituicaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;

    @ManyToMany(mappedBy = "instituicoesFinanceiras", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Leilao> leiloes;

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
}
