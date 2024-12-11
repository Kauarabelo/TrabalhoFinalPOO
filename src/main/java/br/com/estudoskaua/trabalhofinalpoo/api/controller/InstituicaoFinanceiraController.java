package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar Instituições Financeiras.
 */
@RestController
@RequestMapping("/instituicoes")
public class InstituicaoFinanceiraController {

    private final InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;

    @Autowired
    public InstituicaoFinanceiraController(InstituicaoFinanceiraRepository instituicaoFinanceiraRepository) {
        this.instituicaoFinanceiraRepository = instituicaoFinanceiraRepository;
    }


    private static final Logger logger = LoggerFactory.getLogger(InstituicaoFinanceiraController.class);

    /**
     * Lista todas as instituições financeiras.
     *
     * @return Lista de todas as instituições financeiras.
     */
    @GetMapping
    public ResponseEntity<List<InstituicaoFinanceira>> listarTodas() {
        List<InstituicaoFinanceira> instituicoes = instituicaoFinanceiraRepository.findAll();
        return ResponseEntity.ok(instituicoes);
    }

    /**
     * Busca uma instituição financeira por ID.
     *
     * @param id ID da instituição financeira.
     * @return Instituição financeira encontrada ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoFinanceira> buscarPorId(@PathVariable Long id) {
        Optional<InstituicaoFinanceira> instituicao = instituicaoFinanceiraRepository.findById(id);
        return instituicao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova instituição financeira.
     *
     * @param instituicaoFinanceira A instituição financeira a ser criada.
     * @return ResponseEntity com a instituição financeira criada.
     */
    @PostMapping
    public ResponseEntity<InstituicaoFinanceira> criar(@RequestBody InstituicaoFinanceira instituicaoFinanceira) {
        InstituicaoFinanceira instituicaoSalva = instituicaoFinanceiraRepository.save(instituicaoFinanceira);
        return ResponseEntity.status(201).body(instituicaoSalva);
    }

    /**
     * Deleta uma instituição financeira por ID.
     *
     * @param id ID da instituição financeira a ser deletada.
     * @return ResponseEntity com status adequado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!instituicaoFinanceiraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        instituicaoFinanceiraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza uma instituição financeira por ID.
     *
     * @param id ID da instituição financeira a ser atualizada.
     * @param instituicaoFinanceira Objeto com os novos dados para atualização.
     * @return ResponseEntity com a instituição financeira atualizada ou 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoFinanceira> atualizar(@PathVariable Long id,
                                                           @Valid @RequestBody InstituicaoFinanceira instituicaoFinanceira) {
        Optional<InstituicaoFinanceira> instituicaoExistente = instituicaoFinanceiraRepository.findById(id);
        if (instituicaoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        instituicaoFinanceira.setId(id); // Garantir que o ID da entidade seja o mesmo
        InstituicaoFinanceira instituicaoAtualizada = instituicaoFinanceiraRepository.save(instituicaoFinanceira);
        return ResponseEntity.ok(instituicaoAtualizada);
    }
}

