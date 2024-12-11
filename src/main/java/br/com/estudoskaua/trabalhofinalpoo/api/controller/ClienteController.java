package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ClienteRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LanceRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar clientes.
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;
    private final LanceRepository lanceRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    /**
     * Listar todos os clientes.
     *
     * @return Lista de clientes.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Registrar um novo cliente.
     *
     * @param cliente Dados do cliente.
     * @return Cliente registrado.
     */
    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(@RequestBody @Valid Cliente cliente) {
        Cliente savedCliente = clienteRepository.save(cliente);
        logger.info("Cliente registrado: {}", savedCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    /**
     * Atualizar os dados de um cliente existente.
     *
     * @param clienteId ID do cliente.
     * @param cliente   Dados atualizados do cliente.
     * @return Cliente atualizado.
     */
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long clienteId, @RequestBody @Valid Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            logger.warn("Cliente não encontrado: {}", clienteId);
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        Cliente updatedCliente = clienteRepository.save(cliente);
        logger.info("Cliente atualizado: {}", updatedCliente);
        return ResponseEntity.ok(updatedCliente);
    }

    /**
     * Remover um cliente.
     *
     * @param clienteId ID do cliente.
     * @return Resposta indicando se a remoção foi bem-sucedida.
     */
    @DeleteMapping("/{clienteId}")
    @Transactional
    public ResponseEntity<Void> removerCliente(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            logger.warn("Cliente não encontrado: {}", clienteId);
            return ResponseEntity.notFound().build();
        }

        // Excluir lances associados ao excluir um cliente
        lanceRepository.deleteByClienteId(clienteId);

        // Excluir o cliente
        clienteRepository.deleteById(clienteId);

        logger.info("Cliente removido: {}", clienteId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Listar produtos de leilão para um cliente.
     *
     * @param clienteId ID do cliente.
     * @return Lista de produtos em leilão.
     */
    @GetMapping("/{clienteId}/leiloes")
    public ResponseEntity<List<Produto>> listarProdutosLeilao(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            logger.warn("Cliente não encontrado: {}", clienteId);
            return ResponseEntity.notFound().build();
        }

        // Buscar os leilões associados ao cliente
        List<Leilao> leiloes = leilaoRepository.findByCliente_Id(clienteId);  // Método corrigido

        if (leiloes.isEmpty()) {
            logger.warn("Nenhum leilão encontrado para o cliente: {}", clienteId);
            return ResponseEntity.notFound().build();
        }

        // Buscar produtos dos leilões ativos (status EM_ANDAMENTO, por exemplo)
        List<Produto> produtos = produtoRepository.findByLeilaoIdAndLeilaoStatus(
                leiloes.get(0).getId(), Status.EM_ANDAMENTO);  // Exemplo de status, você pode personalizar conforme necessário

        return ResponseEntity.ok(produtos);
    }

}
