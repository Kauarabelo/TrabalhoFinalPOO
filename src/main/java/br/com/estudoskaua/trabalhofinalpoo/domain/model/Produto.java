package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Representa um produto que pode ser leiloado, podendo ser um dispositivo ou um veículo.
 */
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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lance> lances;

    public Produto() {
    }

    /**
     * Construtor que inicializa um produto com os parâmetros fornecidos.
     *
     * @param id                 O ID do produto
     * @param nome               O nome do produto
     * @param descricao          A descrição do produto
     * @param valor              O valor do produto
     * @param imagemUrl          A URL da imagem do produto
     * @param tipo               O tipo do produto (dispositivo ou veículo)
     * @param especificacoes     As especificações do dispositivo
     * @param marca              A marca do produto
     * @param modelo             O modelo do produto
     * @param ano                O ano do veículo
     * @param placa              A placa do veículo
     * @param leilao            O leilão ao qual o produto pertence
     */
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

    // Getters e Setters

    /**
     * Retorna o ID do produto.
     *
     * @return ID do produto
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do produto.
     *
     * @param id ID do produto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return Nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome Nome do produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição do produto.
     *
     * @return Descrição do produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao Descrição do produto
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o valor do produto.
     *
     * @return Valor do produto
     */
    public Double getValor() {
        return valor;
    }

    /**
     * Define o valor do produto.
     *
     * @param valor Valor do produto
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * Retorna a URL da imagem do produto.
     *
     * @return URL da imagem do produto
     */
    public String getImagemUrl() {
        return imagemUrl;
    }

    /**
     * Define a URL da imagem do produto.
     *
     * @param imagemUrl URL da imagem do produto
     */
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    /**
     * Retorna o tipo do produto (dispositivo ou veículo).
     *
     * @return Tipo do produto
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do produto.
     *
     * @param tipo Tipo do produto
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna as especificações do dispositivo.
     *
     * @return Especificações do dispositivo
     */
    public String getEspecificacoes() {
        return especificacoes;
    }

    /**
     * Define as especificações do dispositivo.
     *
     * @param especificacoes Especificações do dispositivo
     */
    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    /**
     * Retorna a marca do produto.
     *
     * @return Marca do produto
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define a marca do produto.
     *
     * @param marca Marca do produto
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Retorna o modelo do produto.
     *
     * @return Modelo do produto
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define o modelo do produto.
     *
     * @param modelo Modelo do produto
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Retorna o ano do veículo.
     *
     * @return Ano do veículo
     */
    public Integer getAno() {
        return ano;
    }

    /**
     * Define o ano do veículo.
     *
     * @param ano Ano do veículo
     */
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * Retorna a placa do veículo.
     *
     * @return Placa do veículo
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Define a placa do veículo.
     *
     * @param placa Placa do veículo
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Retorna o leilão ao qual o produto pertence.
     *
     * @return Leilão associado ao produto
     */
    public Leilao getLeilao() {
        return leilao;
    }

    /**
     * Define o leilão ao qual o produto pertence.
     *
     * @param leilao Leilão associado ao produto
     */
    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }

    /**
     * Retorna a lista de lances associados ao produto.
     *
     * @return Lista de lances do produto
     */
    public List<Lance> getLances() {
        return lances;
    }

    /**
     * Define a lista de lances associados ao produto.
     *
     * @param lances Lista de lances do produto
     */
    public void setLances(List<Lance> lances) {
        this.lances = lances;
    }

    /**
     * Verifica se o produto foi vendido com base nos lances recebidos.
     *
     * @return true se o produto foi vendido, false caso contrário
     */
    @JsonIgnore
    public boolean isVendido() {
        return lances != null && !lances.isEmpty();
    }
}
