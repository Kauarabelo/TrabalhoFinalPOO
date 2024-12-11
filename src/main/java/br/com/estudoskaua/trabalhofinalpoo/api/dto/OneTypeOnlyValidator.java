package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador personalizado para garantir que apenas um tipo de produto (Informática ou Veículo) seja preenchido.
 */
public class OneTypeOnlyValidator implements ConstraintValidator<ProdutoDTO.OneTypeOnly, ProdutoDTO> {

    @Override
    public void initialize(ProdutoDTO.OneTypeOnly constraintAnnotation) {
        // Inicialização do validador (não necessário nesse caso)
    }

    @Override
    public boolean isValid(ProdutoDTO produtoDTO, ConstraintValidatorContext context) {
        boolean tipoInformaticaPreenchido = produtoDTO.getTipoInformatica() != null && !produtoDTO.getTipoInformatica().isEmpty();
        boolean tipoVeiculoPreenchido = produtoDTO.getTipoVeiculo() != null && !produtoDTO.getTipoVeiculo().isEmpty();

        if (tipoInformaticaPreenchido && tipoVeiculoPreenchido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Você pode preencher apenas um tipo de produto: Informática ou Veículo.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
