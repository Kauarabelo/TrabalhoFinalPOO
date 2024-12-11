package br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa uma motocicleta no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MOTOCICLETA")
public class Motocicleta extends Veiculo {

    @NotNull(message = "O tipo de combustível não pode ser vazio")
    private String tipoDeCombustivel;

    @NotNull(message = "A cilindrada não pode ser vazia")
    private Integer cilindrada;
}
