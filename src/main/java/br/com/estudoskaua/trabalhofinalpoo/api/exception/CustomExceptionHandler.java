package br.com.estudoskaua.trabalhofinalpoo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Classe de tratamento de exceções personalizada para a aplicação.
 *
 * Essa classe captura exceções globais e fornece respostas adequadas
 * com informações detalhadas sobre o erro ocorrido.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Manipula exceções globais e retorna uma resposta com status 500 (Internal Server Error).
     *
     * @param ex a exceção que ocorreu
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), "Erro interno do servidor", request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manipula exceções de ponteiro nulo e retorna uma resposta com status 400 (Bad Request).
     *
     * @param ex a exceção de ponteiro nulo que ocorreu
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), "Um valor nulo foi encontrado", request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipula exceções de argumento ilegal e retorna uma resposta com status 400 (Bad Request).
     *
     * @param ex a exceção de argumento ilegal que ocorreu
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), "Argumento inválido fornecido", request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
