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

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Valor do lance é obrigatório")
    @Positive(message = "Valor do lance deve ser positivo")
    private Double valor;

    @NotNull(message = "Data do lance é obrigatória")
    private LocalDateTime dataLance;
}
