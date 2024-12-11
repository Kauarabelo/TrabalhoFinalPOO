package br.com.estudoskaua.trabalhofinalpoo.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa a resposta de erro retornada pela API.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private int status;
}
