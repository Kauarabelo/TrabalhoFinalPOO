package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) para representar um leilão.
 * <p>
 * Utilizado para transferir informações de leilões entre camadas.
 * </p>
 */
@Data
@LeilaoDTO.DataFimPosterior(message = "A data de término deve ser posterior à data de início")
public class LeilaoDTO {

    @NotNull(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    private String descricao;

    @NotNull(message = "IDs dos produtos são obrigatórios")
    @NotEmpty(message = "A lista de IDs dos produtos não pode estar vazia")
    private List<Long> produtoIds;

    @NotNull(message = "IDs das instituições financeiras são obrigatórios")
    @NotEmpty(message = "A lista de IDs das instituições financeiras não pode estar vazia")
    private List<Long> instituicaoFinanceiraIds;

    @NotNull(message = "Data de início é obrigatória")
    @Future(message = "A data de início deve ser futura")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de término é obrigatória")
    @Future(message = "A data de término deve ser futura")
    private LocalDateTime dataFim;

    @NotNull(message = "Data de visitação é obrigatória")
    @Future(message = "A data de visitação deve ser futura")
    private LocalDateTime dataVisitacao;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    /**
     * Anotação para validar se a data de término é posterior à data de início.
     */
    @Constraint(validatedBy = DataFimPosteriorValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataFimPosterior {
        String message() default "A data de término deve ser posterior à data de início";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    /**
     * Implementação do validador para {@code @DataFimPosterior}.
     */
    public static class DataFimPosteriorValidator implements ConstraintValidator<DataFimPosterior, LeilaoDTO> {
        @Override
        public boolean isValid(LeilaoDTO leilaoDTO, ConstraintValidatorContext context) {
            if (leilaoDTO.getDataInicio() == null || leilaoDTO.getDataFim() == null) {
                return true;
            }
            return leilaoDTO.getDataFim().isAfter(leilaoDTO.getDataInicio());
        }
    }

    /**
     * Define o status do leilão com base nas datas de início e fim.
     *
     * @return o status do leilão.
     */
    public Status definirStatus() {
        LocalDateTime agora = LocalDateTime.now();
        if (dataInicio.isAfter(agora)) {
            return Status.ABERTO;
        } else if (dataFim.isBefore(agora)) {
            return Status.FINALIZADO;
        } else {
            return Status.EM_ANDAMENTO;
        }
    }
}
