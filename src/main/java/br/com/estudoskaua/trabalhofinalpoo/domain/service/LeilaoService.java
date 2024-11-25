package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios relacionada aos leilões.
 * <p>
 * Esta classe contém métodos para criar leilões, além de
 * validar as entidades relacionadas, como produtos e instituições financeiras.
 * </p>
 *
 * Autor: Kaua
 */
@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;

    /**
     * Cria um novo leilão com base nas informações fornecidas no {@link LeilaoDTO}.
     *
     * @param leilaoDTO Objeto que contém as informações necessárias para criar o leilão.
     * @return O leilão criado.
     */
    @Transactional
    @Operation(summary = "Cria um novo leilão", description = "Cria um leilão com os dados fornecidos no DTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilão criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto ou Instituição Financeira não encontrado")
    })
    public Leilao criarLeilao(LeilaoDTO leilaoDTO) {
        // Valida os produtos e as instituições financeiras
        List<Produto> produtos = buscarProdutosPorIds(leilaoDTO.getProdutoIds());
        List<InstituicaoFinanceira> instituicoesFinanceiras = buscarInstituicoesFinanceirasPorIds(leilaoDTO.getInstituicaoFinanceiraIds());

        // Verifica se algum produto ou instituição financeira já está associado a um leilão finalizado
        validarProdutosEInstituicoesFinanceiras(produtos, instituicoesFinanceiras);

        // Cria a entidade Leilao
        Leilao leilao = new Leilao();
        leilao.setDescricao(leilaoDTO.getDescricao());
        leilao.setProdutos(produtos);
        leilao.setInstituicoesFinanceiras(instituicoesFinanceiras);
        leilao.setDataInicio(leilaoDTO.getDataInicio());
        leilao.setDataFim(leilaoDTO.getDataFim());
        leilao.setDataVisitacao(leilaoDTO.getDataVisitacao());
        leilao.setEndereco(leilaoDTO.getEndereco());
        leilao.setCidade(leilaoDTO.getCidade());
        leilao.setEstado(leilaoDTO.getEstado());
        leilao.setStatus(definirStatus(leilaoDTO.getDataInicio(), leilaoDTO.getDataFim()));

        // Salva no banco
        return leilaoRepository.save(leilao);
    }

    /**
     * Verifica se algum produto ou instituição financeira já está associado a um leilão finalizado.
     *
     * @param produtos Lista de produtos a serem verificados.
     * @param instituicoesFinanceiras Lista de instituições financeiras a serem verificadas.
     * @throws EntityNotFoundException Se algum produto ou instituição financeira já estiver associado a um leilão finalizado.
     */
    private void validarProdutosEInstituicoesFinanceiras(List<Produto> produtos, List<InstituicaoFinanceira> instituicoesFinanceiras) {
        for (Produto produto : produtos) {
            if (produto.getLeilao() != null && produto.getLeilao().getStatus() == Status.FINALIZADO) {
                throw new EntityNotFoundException("Produto " + produto.getId() + " já está associado a um leilão finalizado.");
            }
        }
        for (InstituicaoFinanceira instituicao : instituicoesFinanceiras) {
            if (instituicao.getLeilao() != null && instituicao.getLeilao().getStatus() == Status.FINALIZADO) {
                throw new EntityNotFoundException("Instituição financeira " + instituicao.getId() + " já está associada a um leilão finalizado.");
            }
        }
    }

    /**
     * Busca os produtos por IDs e garante que todos existam.
     *
     * @param produtoIds Lista de IDs dos produtos a serem buscados.
     * @return Lista de produtos encontrados.
     * @throws EntityNotFoundException Se algum produto não for encontrado.
     */
    private List<Produto> buscarProdutosPorIds(List<Long> produtoIds) {
        List<Produto> produtos = produtoRepository.findAllById(produtoIds);
        List<Long> produtoIdsEncontrados = produtos.stream()
                .map(Produto::getId)
                .collect(Collectors.toList());

        if (produtoIds.size() != produtoIdsEncontrados.size()) {
            throw new EntityNotFoundException("Um ou mais produtos não foram encontrados.");
        }
        return produtos;
    }

    /**
     * Busca as instituições financeiras por IDs e garante que todas existam.
     *
     * @param instituicaoFinanceiraIds Lista de IDs das instituições financeiras a serem buscadas.
     * @return Lista de instituições financeiras encontradas.
     * @throws EntityNotFoundException Se alguma instituição financeira não for encontrada.
     */
    private List<InstituicaoFinanceira> buscarInstituicoesFinanceirasPorIds(List<Long> instituicaoFinanceiraIds) {
        List<InstituicaoFinanceira> instituicoesFinanceiras = instituicaoFinanceiraRepository.findAllById(instituicaoFinanceiraIds);
        List<Long> instituicaoFinanceiraIdsEncontradas = instituicoesFinanceiras.stream()
                .map(InstituicaoFinanceira::getId)
                .collect(Collectors.toList());

        if (instituicaoFinanceiraIds.size() != instituicaoFinanceiraIdsEncontradas.size()) {
            throw new EntityNotFoundException("Uma ou mais instituições financeiras não foram encontradas.");
        }
        return instituicoesFinanceiras;
    }

    /**
     * Define o status do leilão com base nas datas de início e fim.
     *
     * @param dataInicio Data de início do leilão.
     * @param dataFim Data de término do leilão.
     * @return O status do leilão.
     */
    private Status definirStatus(LocalDateTime dataInicio, LocalDateTime dataFim) {
        LocalDateTime agora = LocalDateTime.now();

        // Determina o status do leilão de forma mais explícita
        if (dataInicio.isAfter(agora)) {
            return Status.ABERTO; // Leilão ainda não começou
        } else if (dataInicio.isBefore(agora) && dataFim.isAfter(agora)) {
            return Status.EM_ANDAMENTO; // Leilão está em andamento
        } else if (dataFim.isBefore(agora)) {
            return Status.FINALIZADO; // Leilão já terminou
        }
        return Status.FINALIZADO; // Default caso não se encaixe em nenhuma condição
    }

    /**
     * Busca um leilão pelo ID e lança uma exceção se não encontrado.
     *
     * @param leilaoId ID do leilão a ser buscado.
     * @return O leilão encontrado.
     * @throws EntityNotFoundException Se o leilão não for encontrado.
     */
    private Leilao buscarLeilaoPorId(Long leilaoId) {
        return leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new EntityNotFoundException("Leilão com ID " + leilaoId + " não encontrado"));
    }
}
