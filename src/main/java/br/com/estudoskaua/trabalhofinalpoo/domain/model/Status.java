package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Enum que representa os diferentes estados de um leilão.
 * <p>
 * Os estados são:
 * <ul>
 *     <li><b>EM_ANDAMENTO</b>: O leilão está atualmente em andamento.</li>
 *     <li><b>ABERTO</b>: O leilão está aberto para lances, mas ainda não começou.</li>
 *     <li><b>FINALIZADO</b>: O leilão foi finalizado e não aceita mais lances.</li>
 * </ul>
 * </p>
 */
public enum Status {
    ABERTO("Aberto para lances"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private final String descricao;

    /**
     * Construtor para associar uma descrição ao status.
     *
     * @param descricao Descrição legível do status
     */
    Status(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição amigável do status.
     *
     * @return Descrição do status
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o status correspondente à descrição fornecida.
     *
     * @param descricao Descrição do status
     * @return Status correspondente
     * @throws IllegalArgumentException Se a descrição não corresponder a nenhum status
     */
    public static Status fromDescricao(String descricao) {
        for (Status status : Status.values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Descrição inválida para Status: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
