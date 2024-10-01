package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar leilões.
 */
@RestController
@RequestMapping("/leiloes")
public class LeilaoController {

    private final LeilaoRepository leilaoRepository;
    private final InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;
    private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    public LeilaoController(LeilaoRepository leilaoRepository, InstituicaoFinanceiraRepository instituicaoFinanceiraRepository) {
        this.leilaoRepository = leilaoRepository;
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
            leilao.setDataInicio(leilaoDTO.getDataInicio());
            leilao.setDataFim(leilaoDTO.getDataFim());
            leilao.setEndereco(leilaoDTO.getEndereco());
            leilao.setDataVisitacao(leilaoDTO.getDataVisitacao());
            leilao.setDescricao(leilaoDTO.getDescricao());
            leilao.setEstado(leilaoDTO.getEstado());
            leilao.setCidade(leilaoDTO.getCidade());

            // Define o status usando o método do DTO
            leilao.setStatus(leilaoDTO.definirStatus());

            Leilao leilaoSalvo = leilaoRepository.save(leilao);
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
            return ResponseEntity.notFound().build();
        }

        List<InstituicaoFinanceira> instituicoes = leilaoOptional.get().getInstituicoesFinanceiras();
        logger.info("Instituições financeiras associadas ao leilão {}: {}", leilaoId, instituicoes.size());
        return ResponseEntity.ok(instituicoes);
    }
}
