package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Classe que representa um dispositivo de informática no sistema de leilão.
 */
@Entity
@DiscriminatorValue("DispositivoInformatica")
public class DispositivoInformatica extends Produto {

    @Enumerated(EnumType.STRING)
    private TipoInformatica tipoInformatica;

    private String especificacoes;
    private String marca;
    private String modelo;

    // Construtores
    public DispositivoInformatica() {}

    public DispositivoInformatica(String especificacoes, String marca, String modelo, TipoInformatica tipoInformatica) {
        this.especificacoes = especificacoes;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoInformatica = tipoInformatica;
    }

    // Getters e Setters
    public TipoInformatica getTipoInformatica() {
        return tipoInformatica;
    }

    public void setTipoInformatica(TipoInformatica tipoInformatica) {
        this.tipoInformatica = tipoInformatica;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
