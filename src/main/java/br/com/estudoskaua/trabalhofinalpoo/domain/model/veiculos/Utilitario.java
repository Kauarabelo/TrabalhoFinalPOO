package br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um veículo utilitário no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("UTILITARIO")
public class Utilitario extends Veiculo {

    @NotNull(message = "A capacidade de carga não pode ser vazia")
    private Double capacidadeCarga;

    @NotNull(message = "O número de portas não pode ser vazio")
    private Integer numeroDePortas;
}
