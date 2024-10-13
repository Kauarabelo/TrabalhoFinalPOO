package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Classe que representa um veículo no sistema de leilão.
 */
@Entity
@DiscriminatorValue("Veiculo")
public class Veiculo extends Produto {

    private String marca;
    private String modelo;
    private Integer anoDeFabricacao;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    // Construtores
    public Veiculo() {}

    public Veiculo(String marca, String modelo, Integer anoDeFabricacao, TipoVeiculo tipoVeiculo) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.tipoVeiculo = tipoVeiculo;
    }

    // Getters e Setters
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

    public Integer getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(Integer anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
}
