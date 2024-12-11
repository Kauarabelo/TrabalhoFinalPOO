package br.com.estudoskaua.trabalhofinalpoo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Classe de tratamento de exceções personalizada para a aplicação.
 * <p>
 * Essa classe captura exceções globais e fornece respostas adequadas
 * com informações detalhadas sobre o erro ocorrido.
 * </p>
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
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Erro interno do servidor",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manipula exceções de cliente não encontrado e retorna uma resposta com status 404 (Not Found).
     *
     * @param ex a exceção de cliente não encontrado
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula exceções de produto não encontrado e retorna uma resposta com status 404 (Not Found).
     *
     * @param ex a exceção de produto não encontrado
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula exceções de valor de lance inválido e retorna uma resposta com status 400 (Bad Request).
     *
     * @param ex a exceção de valor de lance inválido
     * @param request a requisição que gerou a exceção
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(LanceValorInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleLanceValorInvalido(LanceValorInvalidoException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );
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
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Argumento inválido fornecido",
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
