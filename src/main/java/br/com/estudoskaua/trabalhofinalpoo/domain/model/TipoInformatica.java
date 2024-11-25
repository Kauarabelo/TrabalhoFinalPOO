package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Enum que representa os tipos de dispositivos de informática disponíveis em um leilão.
 * <p>
 * Os tipos são:
 * <ul>
 *     <li><b>NOTEBOOK</b>: Dispositivo portátil com teclado e tela.</li>
 *     <li><b>MONITOR</b>: Tela externa para visualização de computadores.</li>
 *     <li><b>HUB</b>: Dispositivo que conecta múltiplos dispositivos em uma rede.</li>
 *     <li><b>SWITCH</b>: Dispositivo de rede que conecta dispositivos e encaminha pacotes de dados.</li>
 *     <li><b>ROTEADOR</b>: Dispositivo que direciona pacotes de dados entre redes.</li>
 * </ul>
 * </p>
 */
public enum TipoInformatica {
    NOTEBOOK("Dispositivo portátil com teclado e tela"),
    MONITOR("Tela externa para visualização de computadores"),
    HUB("Dispositivo que conecta múltiplos dispositivos em uma rede"),
    SWITCH("Dispositivo de rede que conecta dispositivos e encaminha pacotes de dados"),
    ROTEADOR("Dispositivo que direciona pacotes de dados entre redes");

    private final String descricao;

    /**
     * Construtor para associar uma descrição ao tipo de dispositivo.
     *
     * @param descricao Descrição legível do tipo de dispositivo
     */
    TipoInformatica(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição amigável do tipo de dispositivo.
     *
     * @return Descrição do tipo de dispositivo
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o tipo de dispositivo correspondente à descrição fornecida.
     *
     * @param descricao Descrição do tipo de dispositivo
     * @return Tipo de dispositivo correspondente
     * @throws IllegalArgumentException Se a descrição não corresponder a nenhum tipo
     */
    public static TipoInformatica fromDescricao(String descricao) {
        for (TipoInformatica tipo : TipoInformatica.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descrição inválida para TipoInformatica: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
