package br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa um notebook no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("NOTEBOOK")
public class Notebook extends DispositivoInformatica {

    @NotNull(message = "O processador não pode ser vazio")
    private String processador;

    @NotNull(message = "A memória RAM não pode ser vazia")
    private String memoriaRam;

    @NotNull(message = "O tamanho da tela não pode ser vazio")
    private String tamanhoTela;

}
