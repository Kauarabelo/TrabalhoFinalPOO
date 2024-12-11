package br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe base para dispositivos de informática no sistema de leilão.
 * <p>
 * Esta classe serve como base para tipos de dispositivos de informática, como
 * Notebook, Monitor, Roteador, etc.
 * </p>
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class DispositivoInformatica extends Produto {

    @NotNull(message = "As especificações não podem estar vazias")
    private String especificacoes;

    @NotNull(message = "A marca não pode estar vazia")
    private String marca;

    @NotNull(message = "O modelo não pode estar vazio")
    private String modelo;

    /**
     * Construtor que inicializa os campos básicos de um dispositivo de informática.
     *
     * @param especificacoes Especificações do dispositivo
     * @param marca          Marca do dispositivo
     * @param modelo         Modelo do dispositivo
     */
    public DispositivoInformatica(String especificacoes, String marca, String modelo) {
        this.especificacoes = especificacoes;
        this.marca = marca;
        this.modelo = modelo;
    }

    public DispositivoInformatica() {

    }
}
