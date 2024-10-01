package br.com.estudoskaua.trabalhofinalpoo.api.exception;

import java.util.Date;

/**
 * Classe que representa a resposta de erro enviada ao cliente em caso de exceções.
 * Contém informações sobre o erro ocorrido, incluindo a data e hora do erro,
 * a mensagem de erro e detalhes adicionais.
 */
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;

    /**
     * Construtor para inicializar a resposta de erro.
     *
     * @param timestamp a data e hora em que o erro ocorreu
     * @param message a mensagem de erro
     * @param details detalhes adicionais sobre o erro
     */
    public ErrorResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters e Setters

    /**
     * Obtém a data e hora em que o erro ocorreu.
     *
     * @return a data e hora do erro
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Define a data e hora em que o erro ocorreu.
     *
     * @param timestamp a data e hora do erro
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Obtém a mensagem de erro.
     *
     * @return a mensagem de erro
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define a mensagem de erro.
     *
     * @param message a mensagem de erro
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtém detalhes adicionais sobre o erro.
     *
     * @return detalhes do erro
     */
    public String getDetails() {
        return details;
    }

    /**
     * Define detalhes adicionais sobre o erro.
     *
     * @param details detalhes do erro
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
