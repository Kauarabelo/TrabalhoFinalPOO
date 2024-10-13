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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;
    private static final Logger logger = LoggerFactory.getLogger(LanceController.class);


    public LanceController(LanceRepository lanceRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository, LeilaoRepository leilaoRepository) {
        this.lanceRepository = lanceRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.leilaoRepository = leilaoRepository;
    }

    /**
     * Listar todos os lances.
     *
     * @return Lista de lances.
     */
    @GetMapping
    public ResponseEntity<List<Lance>> listarTodos() {
        List<Lance> lances = lanceRepository.findAll();
        logger.info("Listando todos os lances. Total: {}", lances.size());
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
                    logger.info("Lance encontrado: {}", lance);
                    return ResponseEntity.ok(lance);
                })
                .orElseGet(() -> {
                    logger.warn("Lance não encontrado para o ID: {}", id);
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
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Cliente cliente = clienteRepository.findById(lanceDTO.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            Leilao leilao = produto.getLeilao();

            Lance lance = new Lance();
            lance.setProduto(produto);
            lance.setCliente(cliente);
            lance.setLeilao(leilao);
            lance.setValor(lanceDTO.getValor());
            lance.setDataLance(lanceDTO.getDataLance());

            Lance savedLance = lanceRepository.save(lance);
            logger.info("Lance criado: {}", savedLance);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLance);
        } catch (Exception e) {
            logger.error("Erro ao criar lance: {}", e.getMessage());
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
            logger.warn("Lance não encontrado para atualização: {}", id);
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
            logger.info("Lance atualizado: {}", lanceAtualizado);
            return ResponseEntity.ok(lanceAtualizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar lance: {}", e.getMessage());
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
            logger.warn("Lance não encontrado para remoção: {}", id);
            return ResponseEntity.notFound().build();
        }
        lanceRepository.deleteById(id);
        logger.info("Lance removido: {}", id);
        return ResponseEntity.noContent().build();
    }
}
