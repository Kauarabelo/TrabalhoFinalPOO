package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LanceRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lances") // Usar plural para a URL
public class LanceController {

    private final LanceRepository lanceRepository;

    public LanceController(LanceRepository lanceRepository) {
        this.lanceRepository = lanceRepository;
    }

    @GetMapping
    public List<Lance> listarTodos() {
        return lanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lance> buscarPorId(@PathVariable Long id) {
        return lanceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lance> criar(@Valid @RequestBody Lance lance) {
        Lance savedLance = lanceRepository.save(lance);
        return ResponseEntity.status(201).body(savedLance); // Retorna 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lance> atualizar(@PathVariable Long id, @Valid @RequestBody Lance lance) {
        if (!lanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lance.setId(id);
        Lance updatedLance = lanceRepository.save(lance);
        return ResponseEntity.ok(updatedLance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!lanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lanceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
