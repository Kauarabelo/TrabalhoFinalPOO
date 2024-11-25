package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import lombok.Data;

/**
 * Data Transfer Object (DTO) simplificado para representar um produto durante a exportação.
 * Utilizado para exportar informações de produto de forma simplificada para o arquivo .DET.
 */
@Data
public class ProdutoDTOExport {

    private String nome;
    private String descricao;
    private Double valor;
    private String tipo;  // Pode ser 'informática' ou 'veículo'
    private String especificacoes;
}
