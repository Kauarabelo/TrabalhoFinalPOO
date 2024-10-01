package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto não pode ser vazio")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "Valor do produto não pode ser nulo")
    private Double valor;

    @Column(name = "imagem_url")
    private String imagemUrl;

    private String tipo; // 'dispositivo' ou 'veiculo'
    private String especificacoes; // Para dispositivos de informática
    private String marca; // Comum a ambos
    private String modelo; // Comum a ambos
    private Integer ano; // Apenas para veículos
    private String placa; // Apenas para veículos

    @ManyToOne
    @JoinColumn(name = "leilao_id", nullable = false)
    @JsonIgnore
    private Leilao leilao;

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, Double valor, String imagemUrl, String tipo, String especificacoes, String marca, String modelo, Integer ano, String placa, Leilao leilao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.imagemUrl = imagemUrl;
        this.tipo = tipo;
        this.especificacoes = especificacoes;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.leilao = leilao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Nome do produto não pode ser vazio") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "Nome do produto não pode ser vazio") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "Descrição não pode estar vazia") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank(message = "Descrição não pode estar vazia") String descricao) {
        this.descricao = descricao;
    }

    public @NotNull(message = "Valor do produto não pode ser nulo") Double getValor() {
        return valor;
    }

    public void setValor(@NotNull(message = "Valor do produto não pode ser nulo") Double valor) {
        this.valor = valor;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Leilao getLeilao() {
        return leilao;
    }

    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
}
