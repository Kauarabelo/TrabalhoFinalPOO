package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    public Cliente() {}

    public Cliente(String nome, String email, String documento) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
