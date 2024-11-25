package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Enum que representa os tipos gerais de produtos disponíveis em um leilão.
 * <p>
 * Os tipos são:
 * <ul>
 *     <li><b>VEICULO</b>: Representa um veículo.</li>
 *     <li><b>DISPOSITIVO_INFORMATICA</b>: Representa um dispositivo de informática.</li>
 * </ul>
 * </p>
 */
public enum TipoProduto {
    VEICULO("Representa um veículo"),
    DISPOSITIVO_INFORMATICA("Representa um dispositivo de informática");

    private final String descricao;

    /**
     * Construtor para associar uma descrição ao tipo de produto.
     *
     * @param descricao Descrição legível do tipo de produto
     */
    TipoProduto(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição amigável do tipo de produto.
     *
     * @return Descrição do tipo de produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o tipo de produto correspondente à descrição fornecida.
     *
     * @param descricao Descrição do tipo de produto
     * @return Tipo de produto correspondente
     * @throws IllegalArgumentException Se a descrição não corresponder a nenhum tipo
     */
    public static TipoProduto fromDescricao(String descricao) {
        for (TipoProduto tipo : TipoProduto.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descrição inválida para TipoProduto: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
