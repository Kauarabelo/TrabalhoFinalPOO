package br.com.estudoskaua.trabalhofinalpoo.api.dto.export;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) simplificado para representar um cliente durante a exportação.
 * Utilizado para exportar informações de clientes de forma simplificada para o arquivo .DET.
 */
@Data
public class ClienteDTOExport {

    private Long id;
    private String nome;
    private String cpf;
    private String documento;
    private String email;
    private String telefone;
    private List<Lance> lances = new ArrayList<>();
    private LocalDateTime dataCadastro;

}
