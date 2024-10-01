package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.Entity;

/**
 * Representa um dispositivo de informática que é um tipo de produto.
 * Inclui informações como marca, modelo e especificações do dispositivo.
 */
@Entity
public class DispositivoInformatica extends Produto {

    private String marca;
    private String modelo;
    private String especificacoes;

    /**
     * Retorna a marca do dispositivo.
     *
     * @return Marca do dispositivo
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define a marca do dispositivo.
     *
     * @param marca Marca do dispositivo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Retorna o modelo do dispositivo.
     *
     * @return Modelo do dispositivo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define o modelo do dispositivo.
     *
     * @param modelo Modelo do dispositivo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Retorna as especificações do dispositivo.
     *
     * @return Especificações do dispositivo
     */
    public String getEspecificacoes() {
        return especificacoes;
    }

    /**
     * Define as especificações do dispositivo.
     *
     * @param especificacoes Especificações do dispositivo
     */
    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }
}
