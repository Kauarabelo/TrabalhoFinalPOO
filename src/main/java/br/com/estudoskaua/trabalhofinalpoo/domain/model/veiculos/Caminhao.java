package br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um caminhao no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CAMINHAO")
public class Caminhao extends Veiculo {

    @NotNull(message = "A capacidade de carga não pode ser vazio")
    private Double capacidadeCarga;
}
