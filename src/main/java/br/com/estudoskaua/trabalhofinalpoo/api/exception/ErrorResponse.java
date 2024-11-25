package br.com.estudoskaua.trabalhofinalpoo.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Classe que representa a resposta de erro enviada ao cliente em caso de exceções.
 * Contém informações sobre o erro ocorrido, incluindo a data e hora do erro,
 * a mensagem de erro e detalhes adicionais.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
