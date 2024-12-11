package br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa uma impressora no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("IMPRESSORA")
public class Impressora extends DispositivoInformatica {

    @NotNull(message = "O tipo de impressora não pode ser vazio")
    private String tipoImpressora;

    @NotNull(message = "A resolução não pode ser vazia")
    private String resolucao;

}

