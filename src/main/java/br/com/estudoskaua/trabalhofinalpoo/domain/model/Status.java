package br.com.estudoskaua.trabalhofinalpoo.domain.model;

/**
 * Enum que representa os diferentes estados de um leilão.
 * <p>
 * Os estados são:
 * <ul>
 *     <li>EM_ANDAMENTO: O leilão está atualmente em andamento.</li>
 *     <li>ABERTO: O leilão está aberto para lances, mas ainda não começou.</li>
 *     <li>FINALIZADO: O leilão foi finalizado e não aceita mais lances.</li>
 * </ul>
 * </p>
 *
 * @author Kaua
 */
public enum Status {
    EM_ANDAMENTO,
    ABERTO,
    FINALIZADO
}
