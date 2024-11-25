package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import lombok.Data;

/**
 * Data Transfer Object (DTO) simplificado para representar uma instituição financeira durante a exportação.
 * Utilizado para exportar informações de instituições financeiras de forma simplificada para o arquivo .DET.
 */
@Data
public class InstituicaoFinanceiraDTOExport {

    private String nome;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String estado;
}
