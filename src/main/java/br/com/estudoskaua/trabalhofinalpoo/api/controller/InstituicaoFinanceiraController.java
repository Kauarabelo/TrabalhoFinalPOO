package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar operações relacionadas a instituições financeiras.
 */
@RestController
@RequestMapping("/instituicoes")
public class InstituicaoFinanceiraController {

    private final InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;
    private static final Logger logger = LoggerFactory.getLogger(InstituicaoFinanceiraController.class);

    public InstituicaoFinanceiraController(InstituicaoFinanceiraRepository instituicaoFinanceiraRepository) {
        this.instituicaoFinanceiraRepository = instituicaoFinanceiraRepository;
    }

    /**
     * Obtém todas as instituições financeiras.
     *
     * @return Lista de instituições financeiras
     */
    @GetMapping
    public ResponseEntity<List<InstituicaoFinanceira>> listar() {
        List<InstituicaoFinanceira> instituicoes = instituicaoFinanceiraRepository.findAll();
        return ResponseEntity.ok(instituicoes);
    }

    /**
     * Obtém uma instituição financeira pelo ID.
     *
     * @param id ID da instituição financeira
     * @return Instituição financeira encontrada ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoFinanceira> buscarPorId(@PathVariable Long id) {
        Optional<InstituicaoFinanceira> instituicao = instituicaoFinanceiraRepository.findById(id);
        if (instituicao.isPresent()) {
            logger.info("Instituição financeira encontrada: {}", instituicao.get());
            return ResponseEntity.ok(instituicao.get());
        } else {
            logger.warn("Instituição financeira não encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cria uma nova instituição financeira.
     *
     * @param instituicaoFinanceira Instituição financeira a ser criada
     * @return Instituição financeira criada
     */
    @PostMapping
    public ResponseEntity<InstituicaoFinanceira> criar(@RequestBody @Valid InstituicaoFinanceira instituicaoFinanceira) {
        InstituicaoFinanceira novaInstituicao = instituicaoFinanceiraRepository.save(instituicaoFinanceira);
        logger.info("Instituição financeira criada: {}", novaInstituicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaInstituicao);
    }

    /**
     * Atualiza uma instituição financeira existente.
     *
     * @param id ID da instituição financeira a ser atualizada
     * @param instituicaoFinanceira Instituição financeira com os novos dados
     * @return Instituição financeira atualizada ou 404 se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoFinanceira> atualizar(@PathVariable Long id, @RequestBody @Valid InstituicaoFinanceira instituicaoFinanceira) {
        if (!instituicaoFinanceiraRepository.existsById(id)) {
            logger.warn("Instituição financeira não encontrada para atualização: {}", id);
            return ResponseEntity.notFound().build();
        }
        instituicaoFinanceira.setId(id);
        InstituicaoFinanceira instituicaoAtualizada = instituicaoFinanceiraRepository.save(instituicaoFinanceira);
        logger.info("Instituição financeira atualizada: {}", instituicaoAtualizada);
        return ResponseEntity.ok(instituicaoAtualizada);
    }

    /**
     * Remove uma instituição financeira pelo ID.
     *
     * @param id ID da instituição financeira a ser removida
     * @return 204 No Content se removido com sucesso, ou 404 se não existir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!instituicaoFinanceiraRepository.existsById(id)) {
            logger.warn("Instituição financeira não encontrada para remoção: {}", id);
            return ResponseEntity.notFound().build();
        }
        instituicaoFinanceiraRepository.deleteById(id);
        logger.info("Instituição financeira removida: {}", id);
        return ResponseEntity.noContent().build();
    }
}