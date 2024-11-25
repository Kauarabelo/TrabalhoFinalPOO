package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Enum que representa os tipos de veículos disponíveis em um leilão.
 * <p>
 * Os tipos são:
 * <ul>
 *     <li><b>CARRO</b>: Veículo motorizado com quatro rodas.</li>
 *     <li><b>MOTOCICLETA_PASSEIO</b>: Veículo motorizado de duas rodas, destinado ao transporte de passageiros.</li>
 *     <li><b>CAMINHÃO</b>: Veículo motorizado grande, projetado para transporte de carga.</li>
 *     <li><b>UTILITÁRIOS</b>: Veículos destinados a serviços específicos ou comerciais.</li>
 * </ul>
 * </p>
 */
public enum TipoVeiculo {
    CARRO("Veículo motorizado com quatro rodas"),
    MOTOCICLETA_PASSEIO("Veículo motorizado de duas rodas, destinado ao transporte de passageiros"),
    CAMINHÃO("Veículo motorizado grande, projetado para transporte de carga"),
    UTILITARIOS("Veículos destinados a serviços específicos ou comerciais");

    private final String descricao;

    /**
     * Construtor para associar uma descrição ao tipo de veículo.
     *
     * @param descricao Descrição legível do tipo de veículo
     */
    TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição amigável do tipo de veículo.
     *
     * @return Descrição do tipo de veículo
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o tipo de veículo correspondente à descrição fornecida.
     *
     * @param descricao Descrição do tipo de veículo
     * @return Tipo de veículo correspondente
     * @throws IllegalArgumentException Se a descrição não corresponder a nenhum tipo
     */
    public static TipoVeiculo fromDescricao(String descricao) {
        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descrição inválida para TipoVeiculo: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
