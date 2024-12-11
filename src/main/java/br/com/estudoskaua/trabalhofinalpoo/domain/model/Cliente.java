package br.com.estudoskaua.trabalhofinalpoo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cliente que pode interagir com o sistema de leilão.
 * Um cliente possui um nome, email, documento único e pode oferecer lances em leilões.
 */
@Entity
@Table(name = "cliente")
@Data
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
    @Column(unique = true) // Garante unicidade do documento
    private String documento;

    @PastOrPresent(message = "Data de cadastro deve ser no passado ou presente")
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Lance> lances = new ArrayList<>();

    // Construtores
    public Cliente() {
        this.dataCadastro = LocalDateTime.now();
    }

    public Cliente(String nome, String email, String documento) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.dataCadastro = LocalDateTime.now();
    }
}
