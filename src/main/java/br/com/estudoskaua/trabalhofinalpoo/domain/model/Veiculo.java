package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Classe que representa um veículo em um leilão.
 * Esta classe herda da classe {@link Produto} e contém informações específicas sobre veículos.
 *
 * @author Kaua
 */
public class Veiculo extends Produto {

    private String marca; // Marca do veículo
    private String modelo; // Modelo do veículo
    private int anoFabricacao; // Ano de fabricação do veículo

    /**
     * Obtém a marca do veículo.
     *
     * @return a marca do veículo
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define a marca do veículo.
     *
     * @param marca a nova marca do veículo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtém o modelo do veículo.
     *
     * @return o modelo do veículo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define o modelo do veículo.
     *
     * @param modelo o novo modelo do veículo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtém o ano de fabricação do veículo.
     *
     * @return o ano de fabricação do veículo
     */
    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    /**
     * Define o ano de fabricação do veículo.
     *
     * @param anoFabricacao o novo ano de fabricação do veículo
     */
    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
}
