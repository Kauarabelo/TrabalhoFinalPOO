package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("Veiculo")
public class Veiculo extends Produto {

    @NotNull(message = "Marca não pode estar vazia")
    private String marca;

    @NotNull(message = "Modelo não pode estar vazio")
    private String modelo;

    @NotNull(message = "Ano de fabricação não pode ser nulo")
    private Integer anoDeFabricacao;

    @NotNull(message = "Placa não pode estar vazia")
    private String placa;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de veículo deve ser especificado")
    private TipoVeiculo tipoVeiculo;

    // Construtor com o Leilao
    public Veiculo(String nome, String descricao, Double valor, String imagemUrl, Leilao leilao, String marca, String modelo, Integer anoDeFabricacao, TipoVeiculo tipoVeiculo) {
        super(nome, descricao, valor, imagemUrl, leilao);  // Passa o Leilao para o super (classe Produto)
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.tipoVeiculo = tipoVeiculo;
    }

    @Override
    public TipoProduto getTipo() {
        return TipoProduto.VEICULO;
    }
}
