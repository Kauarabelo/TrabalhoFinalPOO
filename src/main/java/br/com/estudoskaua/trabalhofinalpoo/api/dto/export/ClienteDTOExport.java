package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import lombok.Data;

/**
 * Data Transfer Object (DTO) simplificado para representar um cliente durante a exportação.
 * Utilizado para exportar informações de clientes de forma simplificada para o arquivo .DET.
 */
@Data
public class ClienteDTOExport {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}
