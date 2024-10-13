package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class OneTypeOnlyValidator implements ConstraintValidator<ProdutoDTO.OneTypeOnly, ProdutoDTO> {
    @Override
    public boolean isValid(ProdutoDTO produtoDTO, ConstraintValidatorContext context) {
        boolean hasTipoInformatica = produtoDTO.getTipoInformatica() != null && !produtoDTO.getTipoInformatica().isEmpty();
        boolean hasTipoVeiculo = produtoDTO.getTipoVeiculo() != null && !produtoDTO.getTipoVeiculo().isEmpty();
        return hasTipoInformatica ^ hasTipoVeiculo; // Retorna true se apenas um dos campos estiver preenchido
    }
}
