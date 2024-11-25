package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um dispositivo de informática no sistema de leilão.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("DispositivoInformatica")
public class DispositivoInformatica extends Produto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de dispositivo deve ser especificado")
    private TipoInformatica tipoInformatica;

    @NotNull(message = "As especificações não podem estar vazias")
    private String especificacoes;

    @NotNull(message = "A marca não pode estar vazia")
    private String marca;

    @NotNull(message = "O modelo não pode estar vazio")
    private String modelo;

    /**
     * Construtor com Leilão.
     *
     * @param tipoInformatica Tipo do dispositivo de informática.
     * @param especificacoes Especificações do dispositivo.
     * @param marca Marca do dispositivo.
     * @param modelo Modelo do dispositivo.
     */
    public DispositivoInformatica(String nome, String descricao, Double valor, String imagemUrl, Leilao leilao, TipoInformatica tipoInformatica, String especificacoes, String marca, String modelo) {
        super(nome, descricao, valor, imagemUrl, leilao);  // Passa o Leilao para o super (classe Produto)
        this.tipoInformatica = tipoInformatica;
        this.especificacoes = especificacoes;
        this.marca = marca;
        this.modelo = modelo;
    }

    @Override
    public TipoProduto getTipo() {
        return TipoProduto.DISPOSITIVO_INFORMATICA;
    }
}
