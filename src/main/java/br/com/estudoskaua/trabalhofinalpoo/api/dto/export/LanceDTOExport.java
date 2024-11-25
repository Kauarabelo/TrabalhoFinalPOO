package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) simplificado para representar um lance em um leilão durante a exportação.
 * Utilizado para exportar informações sobre lances em produtos de forma simplificada para o arquivo .DET.
 */
@Data
public class LanceDTOExport {

    private Long clienteId;
    private Long produtoId;
    private Double valor;
    private LocalDateTime dataLance;
}
