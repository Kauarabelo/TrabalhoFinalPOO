package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Representa um cliente que pode interagir com o sistema de leilão.
 * Um cliente possui um nome, email e documento único.
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email deve ser válido")
    @Column(unique = true) // Garante que o email seja único na tabela
    private String email;

    @NotBlank(message = "Documento não pode estar vazio")
    @Size(min = 11, max = 11, message = "Documento deve ter exatamente 11 caracteres")
    private String documento;

    // Construtores
    /**
     * Construtor padrão.
     */
    public Cliente() {}

    /**
     * Construtor que inicializa o cliente com nome, email e documento.
     *
     * @param nome     Nome do cliente
     * @param email    Email do cliente
     * @param documento Documento único do cliente
     */
    public Cliente(String nome, String email, String documento) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
    }

    // Getters e Setters

    /**
     * Retorna o ID do cliente.
     *
     * @return ID do cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do cliente.
     *
     * @param id ID do cliente
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o nome do cliente.
     *
     * @return Nome do cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome Nome do cliente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o email do cliente.
     *
     * @return Email do cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do cliente.
     *
     * @param email Email do cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o documento do cliente.
     *
     * @return Documento do cliente
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Define o documento do cliente.
     *
     * @param documento Documento único do cliente
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
