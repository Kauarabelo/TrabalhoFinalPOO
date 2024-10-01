package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClienteController
 * Controlador para gerenciar operações relacionadas a Clientes.
 *
 * @author Kaua
 */
@RestController
@RequestMapping("/clientes") // Ajuste na URL para plural
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> add(@RequestBody @Validated Cliente cliente) {
        if (cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflito se o ID já existir
        }
        Cliente savedCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> edit(@PathVariable Long id, @RequestBody @Validated Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id); // Atualiza o ID no modelo
        Cliente updatedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
