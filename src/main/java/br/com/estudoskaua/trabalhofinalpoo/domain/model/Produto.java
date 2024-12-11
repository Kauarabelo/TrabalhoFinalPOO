package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * Classe base para os produtos no sistema de leilão.
 */
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String descricao;
    private Double valor;
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "leilao_id")
    @JsonIgnoreProperties({"produtos", "instituicoesFinanceiras"})
    private Leilao leilao;

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Lance> lances;


    /**
     * -- GETTER --
     *  Verifica se o produto foi vendido.
     *
     * @return true se o produto foi vendido, caso contrário false
     */
    @Getter
    @Column(nullable = false)
    private boolean vendido = false;

    /**
     * Define o status de venda do produto.
     *
     * @param vendido true para marcar como vendido, false caso contrário
     */
    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

}
