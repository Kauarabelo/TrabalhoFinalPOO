package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LanceDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LanceRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ClienteRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar lances.
 */
@RestController
@RequestMapping("/lances")
@RequiredArgsConstructor
@Slf4j
public class LanceController {

    private final LanceRepository lanceRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;

    /**
     * Listar todos os lances.
     *
     * @return Lista de lances.
     */
    @GetMapping
    @Transactional
    public ResponseEntity<List<Lance>> listarTodos() {
        List<Lance> lances = lanceRepository.findAll();
        lances.forEach(lance -> Hibernate.initialize(lance.getCliente().getLances()));
        log.info("Listando todos os lances. Total: {}", lances.size());
        return ResponseEntity.ok(lances);
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
                .map(lance -> {
                    log.info("Lance encontrado: {}", lance);
                    return ResponseEntity.ok(lance);
                })
                .orElseGet(() -> {
                    log.warn("Lance não encontrado para o ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Criar um novo lance.
     *
     * @param lanceDTO Dados do lance.
     * @return Lance criado.
     */
    @PostMapping
    public ResponseEntity<Lance> criar(@Valid @RequestBody LanceDTO lanceDTO) {
        try {
            Produto produto = produtoRepository.findById(lanceDTO.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            Cliente cliente = clienteRepository.findById(lanceDTO.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

            Leilao leilao = produto.getLeilao();

            Lance lance = new Lance();
            lance.setProduto(produto);
            lance.setCliente(cliente);
            lance.setLeilao(leilao);
            lance.setValor(lanceDTO.getValor());
            lance.setDataLance(lanceDTO.getDataLance());

            Lance savedLance = lanceRepository.save(lance);
            log.info("Lance criado: {}", savedLance);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLance);
        } catch (EntityNotFoundException e) {
            log.error("Erro ao criar lance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar lance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Atualizar um lance existente.
     *
     * @param id       ID do lance a ser atualizado.
     * @param lanceDTO Dados atualizados do lance.
     * @return Lance atualizado ou 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lance> atualizar(@PathVariable Long id, @Valid @RequestBody LanceDTO lanceDTO) {
        if (!lanceRepository.existsById(id)) {
            log.warn("Lance não encontrado para atualização: {}", id);
            return ResponseEntity.notFound().build();
        }

        try {
            Produto produto = produtoRepository.findById(lanceDTO.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
            Cliente cliente = clienteRepository.findById(lanceDTO.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

            Lance lance = new Lance();
            lance.setId(id);
            lance.setProduto(produto);
            lance.setLeilao(produto.getLeilao());
            lance.setCliente(cliente);
            lance.setValor(lanceDTO.getValor());
            lance.setDataLance(lanceDTO.getDataLance());

            Lance lanceAtualizado = lanceRepository.save(lance);
            log.info("Lance atualizado: {}", lanceAtualizado);
            return ResponseEntity.ok(lanceAtualizado);
        } catch (EntityNotFoundException e) {
            log.error("Erro ao atualizar lance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar lance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
            log.warn("Lance não encontrado para remoção: {}", id);
            return ResponseEntity.notFound().build();
        }
        lanceRepository.deleteById(id);
        log.info("Lance removido: {}", id);
        return ResponseEntity.noContent().build();
    }
}
