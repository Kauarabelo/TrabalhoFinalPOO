package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar um lance em um leilão.
 * <p>
 * Utilizado para transferir informações sobre lances em produtos.
 * </p>
 */
@Data
public class LanceDTO {

    /**
     * ID do cliente que está fazendo o lance.
     * Deve ser informado e não pode ser nulo.
     */
    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    /**
     * ID do produto para o qual o lance está sendo feito.
     * Deve ser informado e não pode ser nulo.
     */
    @NotNull(message = "O ID do produto é obrigatório.")
    private Long produtoId;

    /**
     * Valor do lance a ser registrado.
     * Deve ser um valor positivo e não pode ser nulo.
     */
    @NotNull(message = "O valor do lance é obrigatório.")
    @Positive(message = "O valor do lance deve ser maior que zero.")
    private Double valor;

    /**
     * Data e hora do lance.
     * Deve ser informada, não pode ser nula, e não deve estar no futuro.
     */
    @NotNull(message = "A data do lance é obrigatória.")
    @PastOrPresent(message = "A data do lance não pode estar no futuro.")
    private LocalDateTime dataLance;
}
