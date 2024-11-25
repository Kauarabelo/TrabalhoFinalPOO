package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) simplificado para representar um leilão durante a exportação.
 * Utilizado para exportar informações de leilão de forma simplificada para o arquivo .DET.
 */
@Data
public class LeilaoDTOExport {

    private String descricao;
    private List<String> produtoNomes;  // Lista apenas dos nomes dos produtos
    private List<String> instituicaoFinanceiraNomes;  // Lista dos nomes das instituições financeiras
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String endereco;
    private String cidade;
    private String estado;
}
