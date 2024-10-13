package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data Transfer Object (DTO) para representar um produto.
 * Utilizado para transferir dados entre camadas da aplicação.
 */
@ProdutoDTO.OneTypeOnly(message = "Apenas um tipo de produto deve ser especificado (Informática ou Veículo)")
public class ProdutoDTO {
    @NotBlank(message = "Nome do produto não pode ser vazio")
    @Size(max = 100, message = "Nome do produto não pode exceder 100 caracteres")
    private String nome; // Nome do produto

    @NotBlank(message = "Descrição não pode estar vazia")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    private String descricao; // Descrição do produto

    @NotNull(message = "Valor do produto não pode ser nulo")
    @Positive(message = "Valor do produto deve ser maior que zero")
    private Double valor; // Valor do produto

    @Pattern(regexp = "^(http|https)://.*$", message = "URL da imagem deve ser uma URL válida")
    private String imagemUrl; // URL da imagem do produto

    @NotNull(message = "ID do leilão não pode ser nulo")
    private Long leilaoId; // Para associar o produto a um leilão

    private String tipoInformatica; // Para dispositivos de informática
    private String tipoVeiculo; // Para veículos
    private String marca;    // Marca do veículo
    private String modelo;   // Modelo do veículo
    private Integer anoDeFabricacao; // Ano de fabricação do veículo

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Long getLeilaoId() {
        return leilaoId;
    }

    public void setLeilaoId(Long leilaoId) {
        this.leilaoId = leilaoId;
    }

    public String getTipoInformatica() {
        return tipoInformatica;
    }

    public void setTipoInformatica(String tipoInformatica) {
        this.tipoInformatica = tipoInformatica;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(Integer anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    @Constraint(validatedBy = OneTypeOnlyValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OneTypeOnly {
        String message() default "Apenas um tipo de produto deve ser especificado (Informática ou Veículo)";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class OneTypeOnlyValidator implements ConstraintValidator<OneTypeOnly, ProdutoDTO> {
        @Override
        public boolean isValid(ProdutoDTO produtoDTO, ConstraintValidatorContext context) {
            boolean hasTipoInformatica = produtoDTO.getTipoInformatica() != null && !produtoDTO.getTipoInformatica().isEmpty();
            boolean hasTipoVeiculo = produtoDTO.getTipoVeiculo() != null && !produtoDTO.getTipoVeiculo().isEmpty();
            return hasTipoInformatica ^ hasTipoVeiculo; // Retorna true se apenas um dos campos estiver preenchido
        }
    }
}
