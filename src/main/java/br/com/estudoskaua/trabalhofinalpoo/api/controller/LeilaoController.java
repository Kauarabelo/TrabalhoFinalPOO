package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar leilões.
 */
@RestController
@RequestMapping("/leiloes")
public class LeilaoController {
    private final LeilaoRepository leilaoRepository;
    private final ProdutoRepository produtoRepository;
    private final InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;
    private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    public LeilaoController(LeilaoRepository leilaoRepository, ProdutoRepository produtoRepository, InstituicaoFinanceiraRepository instituicaoFinanceiraRepository) {
        this.leilaoRepository = leilaoRepository;
        this.produtoRepository = produtoRepository;
        this.instituicaoFinanceiraRepository = instituicaoFinanceiraRepository;
    }

    /**
     * Listar todos os leilões.
     *
     * @return Lista de todos os leilões registrados.
     */
    @GetMapping
    public ResponseEntity<List<Leilao>> listarTodosLeiloes() {
        List<Leilao> leiloes = leilaoRepository.findAll();
        leiloes.forEach(this::atualizarStatusLeilao); // Atualiza o status de todos os leilões
        logger.info("Listando todos os leilões. Total: {}", leiloes.size());
        if (leiloes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(leiloes);
    }

    /**
     * Cria um novo leilão com base nas informações fornecidas no LeilaoDTO.
     *
     * @param leilaoDTO o DTO que contém as informações do leilão a ser criado.
     * @return ResponseEntity com o leilão criado e o status HTTP correspondente.
     */
    @PostMapping
    public ResponseEntity<Leilao> criarLeilao(@RequestBody LeilaoDTO leilaoDTO) {
        try {
            Leilao leilao = new Leilao();
            leilao.setDescricao(leilaoDTO.getDescricao());
            leilao.setDataInicio(leilaoDTO.getDataInicio());
            leilao.setDataFim(leilaoDTO.getDataFim());
            leilao.setDataVisitacao(leilaoDTO.getDataVisitacao());
            leilao.setEndereco(leilaoDTO.getEndereco());
            leilao.setCidade(leilaoDTO.getCidade());
            leilao.setEstado(leilaoDTO.getEstado());

            // Atualizar status com base nas datas
            atualizarStatusLeilao(leilao);

            // Persistir o leilão primeiro para obter seu ID
            Leilao leilaoSalvo = leilaoRepository.save(leilao);

            // Adicionar Produtos ao Leilão
            List<Produto> produtos = produtoRepository.findAllById(leilaoDTO.getProdutoIds());
            produtos.forEach(produto -> produto.setLeilao(leilaoSalvo));
            produtoRepository.saveAll(produtos);

            // Adicionar Instituições Financeiras ao Leilão
            List<InstituicaoFinanceira> instituicoes = instituicaoFinanceiraRepository.findAllById(leilaoDTO.getInstituicaoFinanceiraIds());
            leilaoSalvo.setInstituicoesFinanceiras(instituicoes);
            leilaoRepository.save(leilaoSalvo);

            logger.info("Leilão criado com ID: {}", leilaoSalvo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(leilaoSalvo);
        } catch (Exception e) {
            logger.error("Erro ao criar leilão: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Associar uma instituição financeira a um leilão.
     *
     * @param leilaoId      ID do leilão.
     * @param instituicaoId ID da instituição financeira.
     * @return ResponseEntity com o leilão atualizado ou 404 Not Found.
     */
    @PostMapping("/{leilaoId}/instituicoes-financeiras/{instituicaoId}")
    public ResponseEntity<Leilao> associarInstituicaoFinanceira(@PathVariable Long leilaoId, @PathVariable Long instituicaoId) {
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(leilaoId);
        Optional<InstituicaoFinanceira> instituicaoOptional = instituicaoFinanceiraRepository.findById(instituicaoId);
        if (leilaoOptional.isEmpty() || instituicaoOptional.isEmpty()) {
            logger.warn("Leilão ou Instituição financeira não encontrados: {} - {}", leilaoId, instituicaoId);
            return ResponseEntity.notFound().build();
        }
        Leilao leilao = leilaoOptional.get();
        leilao.getInstituicoesFinanceiras().add(instituicaoOptional.get());
        leilaoRepository.save(leilao);
        logger.info("Instituição financeira associada ao leilão: {}", leilao.getId());
        return ResponseEntity.ok(leilao);
    }

    /**
     * Listar instituições financeiras associadas a um leilão.
     *
     * @param leilaoId ID do leilão.
     * @return Lista de instituições financeiras ou 404 Not Found.
     */
    @GetMapping("/{leilaoId}/instituicoes-financeiras")
    public ResponseEntity<List<InstituicaoFinanceira>> listarInstituicoesFinanceiras(@PathVariable Long leilaoId) {
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(leilaoId);
        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão não encontrado: {}", leilaoId);
            return ResponseEntity.notFound().build();
        }
        List<InstituicaoFinanceira> instituicoes = leilaoOptional.get().getInstituicoesFinanceiras();
        logger.info("Instituições financeiras associadas ao leilão {}: {}", leilaoId, instituicoes.size());
        return ResponseEntity.ok(instituicoes);
    }

    /**
     * Atualiza o status de um leilão com base na data e hora atuais.
     *
     * @param leilao Leilão a ser atualizado.
     */
    private void atualizarStatusLeilao(Leilao leilao) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(leilao.getDataInicio())) {
            leilao.setStatus(Status.ABERTO);
        } else if (now.isAfter(leilao.getDataFim())) {
            leilao.setStatus(Status.FINALIZADO);
        } else {
            leilao.setStatus(Status.EM_ANDAMENTO);

        }
    }

    /**
     * Deletar um leilão por ID.
     *
     * @param id ID do leilão a ser deletado.
     * @return 204 No Content ou 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLeilao(@PathVariable Long id) {
        if (!leilaoRepository.existsById(id)) {
            logger.warn("Leilão não encontrado para remoção: {}", id);
            return ResponseEntity.notFound().build();
        }
        leilaoRepository.deleteById(id);
        logger.info("Leilão removido: {}", id);
        return ResponseEntity.ok().build();
    }

    /**
     * Buscar leilão por ID.
     *
     * @param id ID do leilão.
     * @return Leilão correspondente ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leilao> buscarPorId(@PathVariable Long id) {
        return leilaoRepository.findById(id)
                .map(leilao -> {
                    logger.info("Leilão encontrado: {}", leilao);
                    return ResponseEntity.ok(leilao);
                })
                .orElseGet(() -> {
                    logger.warn("Leilão não encontrado para o ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

}
