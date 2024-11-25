package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um produto genérico em um leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;
    private Double valor;
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "leilao_id")
    @JsonIgnoreProperties({"produtos", "instituicoesFinanceiras"})
    private Leilao leilao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto", insertable = false, updatable = false)
    private TipoProduto tipo;

    private boolean vendido;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Lance> lances = new ArrayList<>();

    /**
     * Construtor com Leilao.
     *
     * @param leilao Leilão ao qual o produto está associado.
     */
    public Produto(Leilao leilao) {
        this.leilao = leilao;
    }

    /**
     * Construtor completo para Produto.
     *
     * @param nome Nome do produto.
     * @param descricao Descrição do produto.
     * @param valor Valor do produto.
     * @param imagemUrl URL da imagem do produto.
     * @param leilao Leilão ao qual o produto está associado.
     */
    public Produto(String nome, String descricao, Double valor, String imagemUrl, Leilao leilao) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.imagemUrl = imagemUrl;
        this.leilao = leilao;
    }
}
