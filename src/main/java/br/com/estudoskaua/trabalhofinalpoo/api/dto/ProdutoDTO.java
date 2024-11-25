package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data Transfer Object (DTO) para representar um produto.
 * <p>
 * Utilizado para transferir dados entre camadas da aplicação.
 * </p>
 */
@Data
public class ProdutoDTO {

    @NotBlank(message = "Nome do produto não pode ser vazio")
    @Size(max = 100, message = "Nome do produto não pode exceder 100 caracteres")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    private String descricao;

    @NotNull(message = "Valor do produto não pode ser nulo")
    @Positive(message = "Valor do produto deve ser maior que zero")
    private Double valor;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL da imagem deve ser válida")
    private String imagemUrl;

    @NotNull(message = "ID do leilão não pode ser nulo")
    private Long leilaoId;

    private String tipoInformatica;
    private String tipoVeiculo;
    private String marca;
    private String modelo;

    @Positive(message = "Ano de fabricação deve ser maior que zero")
    private Integer anoDeFabricacao;

    // Novo campo para Especificações
    @NotBlank(message = "Especificações não podem estar vazias")
    private String especificacoes;

    /**
     * Valida que apenas um tipo de produto (Informática ou Veículo) seja preenchido.
     */
    @Constraint(validatedBy = OneTypeOnlyValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OneTypeOnly {
        String message() default "Apenas um tipo de produto deve ser especificado (Informática ou Veículo)";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

}
