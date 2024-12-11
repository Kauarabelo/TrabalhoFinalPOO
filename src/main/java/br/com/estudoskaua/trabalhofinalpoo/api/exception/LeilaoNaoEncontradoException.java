package br.com.estudoskaua.trabalhofinalpoo.api.exception;

public class LeilaoNaoEncontradoException extends RuntimeException{

    // Construtor que recebe a mensagem de erro
    public LeilaoNaoEncontradoException(String message) {
        super(message);  // Passa a mensagem para a classe pai (RuntimeException)
    }
}
