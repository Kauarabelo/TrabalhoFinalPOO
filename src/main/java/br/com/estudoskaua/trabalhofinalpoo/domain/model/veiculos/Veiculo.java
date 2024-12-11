package br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe base para veículos no sistema de leilão.
 * <p>
 * Esta classe serve como base para tipos de veículos, como Carro, Caminhão, etc.
 * </p>
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Veiculo extends Produto {

    @NotNull(message = "Marca não pode estar vazia")
    private String marca;

    @NotNull(message = "Modelo não pode estar vazio")
    private String modelo;

    @NotNull(message = "Ano de fabricação não pode ser nulo")
    private Integer anoDeFabricacao;

    @NotNull(message = "Placa não pode estar vazia")
    private String placa;

    /**
     * Construtor que inicializa os campos básicos de um veículo.
     *
     * @param marca          Marca do veículo
     * @param modelo         Modelo do veículo
     * @param anoDeFabricacao Ano de fabricação do veículo
     * @param placa          Placa do veículo
     */
    public Veiculo(String marca, String modelo, Integer anoDeFabricacao, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.placa = placa;
    }

    public Veiculo() {

    }

}
