package br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um carro no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CARRO")
public class Carro extends Veiculo {

    @NotNull(message = "O número de portas não pode ser vazio")
    private Integer numeroDePortas;

    @NotNull(message = "O número de passageiros não pode ser vazio")
    private Integer numeroDePassegeiros;

    @NotNull(message = "O tipo de combustível não pode ser vazio")
    private String tipoDeCombustivel;
}
