package br.com.estudoskaua.trabalhofinalpoo.api.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    // Construtor que recebe a mensagem de erro
    public ProdutoNaoEncontradoException(String message) {
        super(message);  // Passa a mensagem para a classe pai (RuntimeException)
    }
}
