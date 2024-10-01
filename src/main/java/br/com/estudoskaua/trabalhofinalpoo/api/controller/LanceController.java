package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LanceRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar lances.
 */
@RestController
@RequestMapping("/lances")
public class LanceController {

    private final LanceRepository lanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(LanceController.class);

    public LanceController(LanceRepository lanceRepository) {
        this.lanceRepository = lanceRepository;
    }

    /**
     * Listar todos os lances.
     *
     * @return Lista de lances.
     */
    @GetMapping
    public List<Lance> listarTodos() {
        List<Lance> lances = lanceRepository.findAll();
        logger.info("Listando todos os lances. Total: {}", lances.size());
        return lances;
    }

    /**
     * Buscar lance por ID.
     *
     * @param id ID do lance.
     * @return Lance correspondente ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lance> buscarPorId(@PathVariable Long id) {
        return lanceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Criar um novo lance.
     *
     * @param lance Dados do lance.
     * @return Lance criado.
     */
    @PostMapping
    public ResponseEntity<Lance> criar(@Valid @RequestBody Lance lance) {
        Lance savedLance = lanceRepository.save(lance);
        logger.info("Lance criado: {}", savedLance);
        return ResponseEntity.status(201).body(savedLance);
    }

    /**
     * Atualizar um lance existente.
     *
     * @param id    ID do lance a ser atualizado.
     * @param lance Dados atualizados do lance.
     * @return Lance atualizado ou 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lance> atualizar(@PathVariable Long id, @Valid @RequestBody Lance lance) {
        if (!lanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lance.setId(id);
        Lance updatedLance = lanceRepository.save(lance);
        logger.info("Lance atualizado: {}", updatedLance);
        return ResponseEntity.ok(updatedLance);
    }

    /**
     * Deletar um lance por ID.
     *
     * @param id ID do lance a ser deletado.
     * @return 204 No Content ou 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!lanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lanceRepository.deleteById(id);
        logger.info("Lance removido: {}", id);
        return ResponseEntity.noContent().build();
    }
}
