package br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um monitor no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MONITOR")
public class Monitor extends DispositivoInformatica {

    @NotNull(message = "A resolução não pode ser vazia")
    private String resolucao;

    @NotNull(message = "O tamanho da tela não pode ser vazio")
    private String tamanhoTela;

}

