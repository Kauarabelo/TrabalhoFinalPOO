package br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um hub no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("HUB")
public class Hub extends DispositivoInformatica {

    @NotNull(message = "O frequencia não pode ser vazio")
    private String frequencia;

    @NotNull(message = "A quantidade de portas não pode ser vazia")
    private String qtdPorta;

}
