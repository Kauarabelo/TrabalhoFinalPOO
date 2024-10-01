package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.Entity;

@Entity
public class DispositivoInformatica extends Produto{

    private String marca;
    private String modelo;
    private String especificacoes;

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

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }
}
