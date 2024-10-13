package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para gerenciar produtos.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    public ProdutoController(ProdutoRepository produtoRepository, LeilaoRepository leilaoRepository) {
        this.produtoRepository = produtoRepository;
        this.leilaoRepository = leilaoRepository;
    }

    /**
     * Listar todos os produtos.
     *
     * @return Lista de produtos.
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        logger.info("Listando todos os produtos. Total: {}", produtos.size());
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar produto por ID.
     *
     * @param id ID do produto.
     * @return Produto correspondente ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Produto com ID {} não encontrado.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Criar um novo produto vinculado a um leilão.
     *
     * @param produto Dados do produto.
     * @return ResponseEntity com o produto criado e o status HTTP correspondente.
     */
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        // Busca o leilão associado ao ID fornecido
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(produtoDTO.getLeilaoId());

        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão com ID {} não encontrado para vinculação.", produtoDTO.getLeilaoId());
            return ResponseEntity.badRequest().body(null);
        }

        Leilao leilao = leilaoOptional.get();

        // Converte ProdutoDTO para entidade Produto
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
        produto.setImagemUrl(produtoDTO.getImagemUrl());
        produto.setLeilao(leilao);  // Associa o leilão encontrado

        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto criado e vinculado ao leilão: {}", savedProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    /**
     * Desassociar um produto de um leilão.
     *
     * @param id ID do produto.
     * @return ResponseEntity com o produto desassociado ou 404 Not Found.
     */
    @PutMapping("/{id}/desassociar")
    public ResponseEntity<Produto> desassociarProduto(@PathVariable Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            logger.warn("Produto com ID {} não encontrado para desassociação.", id);
            return ResponseEntity.notFound().build();
        }

        Produto produto = produtoOptional.get();

        // Verifica se o produto foi vendido
        if (produto.isVendido()) {
            logger.warn("Produto com ID {} não pode ser desassociado, pois já foi vendido.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Produto não pode ser desassociado se foi vendido
        }

        produto.setLeilao(null);  // Desassocia do leilão
        produtoRepository.save(produto);
        logger.info("Produto desassociado do leilão: {}", produto.getId());
        return ResponseEntity.ok(produto);
    }

    /**
     * Associar um produto a outro leilão.
     *
     * @param id      ID do produto.
     * @param leilaoId ID do leilão.
     * @return ResponseEntity com o produto associado ou 404 Not Found.
     */
    @PutMapping("/{id}/associar-leilao/{leilaoId}")
    public ResponseEntity<Produto> associarAOutroLeilao(@PathVariable Long id, @PathVariable Long leilaoId) {
        logger.info("Tentando associar produto com ID {} ao leilão com ID {}", id, leilaoId);

        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(leilaoId);

        // Verifica se o produto ou o leilão não foram encontrados
        if (produtoOptional.isEmpty()) {
            logger.warn("Produto com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão com ID {} não encontrado.", leilaoId);
            return ResponseEntity.notFound().build();
        }

        Produto produto = produtoOptional.get();
        Leilao leilao = leilaoOptional.get();

        // Verifica se o produto já recebeu lances
        if (produto.getLances().isEmpty()) {
            produto.setLeilao(leilao);
            produtoRepository.save(produto);
            logger.info("Produto associado ao novo leilão: {}", leilao.getId());
            return ResponseEntity.ok(produto);
        }

        logger.warn("O produto com ID {} já recebeu lances e não pode ser associado a outro leilão.", id);
        return ResponseEntity.badRequest().build(); // Retorna apenas o status 400
    }

    /**
     * Remover um produto por ID.
     *
     * @param id ID do produto.
     * @return ResponseEntity com o status correspondente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            logger.warn("Produto com ID {} não encontrado para remoção.", id);
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        logger.info("Produto removido: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca produtos de um leilão específico aplicando filtros opcionais.
     *
     * @param leilaoId      ID do leilão.
     * @param tipo          Tipo de produto (dispositivo ou veículo) - opcional.
     * @param minValor      Valor mínimo do produto - opcional.
     * @param maxValor      Valor máximo do produto - opcional.
     * @param palavraChave  Palavra-chave contida no nome do produto - opcional.
     * @return ResponseEntity contendo a lista de produtos filtrados.
     */
    @GetMapping("/leiloes/{leilaoId}/produtos")
    public ResponseEntity<List<Produto>> buscarProdutosPorFiltros(
            @PathVariable Long leilaoId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Double minValor,
            @RequestParam(required = false) Double maxValor,
            @RequestParam(required = false) String palavraChave) {

        List<Produto> produtos = produtoRepository.findAll().stream()
                .filter(produto -> produto.getLeilao().getId().equals(leilaoId))
                .filter(produto -> tipo == null || produto.getTipo().equalsIgnoreCase(tipo))
                .filter(produto -> minValor == null || produto.getValor() >= minValor)
                .filter(produto -> maxValor == null || produto.getValor() <= maxValor)
                .filter(produto -> palavraChave == null || produto.getNome().contains(palavraChave))
                .collect(Collectors.toList());

        return ResponseEntity.ok(produtos);
    }

}
