package br.com.estudoskaua.trabalhofinalpoo.api.exception;

public class ProdutoVendidoException extends RuntimeException {

    // Construtor que recebe a mensagem de erro
    public ProdutoVendidoException(String message) {
        super(message);  // Passa a mensagem para a classe pai (RuntimeException)
    }
}
