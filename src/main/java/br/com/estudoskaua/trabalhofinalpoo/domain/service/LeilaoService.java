package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.InstituicaoFinanceiraRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócios relacionada aos leilões.
 * <p>
 * Esta classe contém métodos para criar leilões e produtos, além de
 * validar as entidades relacionadas, como produtos e instituições financeiras.
 * </p>
 *
 * @author Kaua
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
    public Leilao criarLeilao(LeilaoDTO leilaoDTO) {
        // Busca e valida os produtos
        List<Produto> produtos = buscarProdutosPorIds(leilaoDTO.getProdutoIds());

        // Busca e valida as instituições financeiras
        List<InstituicaoFinanceira> instituicoesFinanceiras = buscarInstituicoesFinanceirasPorIds(leilaoDTO.getInstituicaoFinanceiraIds());

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
     * Busca os produtos por IDs e garante que existam.
     *
     * @param produtoIds Lista de IDs dos produtos a serem buscados.
     * @return Lista de produtos encontrados.
     */
    private List<Produto> buscarProdutosPorIds(List<Long> produtoIds) {
        return produtoRepository.findAllById(produtoIds);
    }

    /**
     * Busca as instituições financeiras por IDs e garante que existam.
     *
     * @param instituicaoFinanceiraIds Lista de IDs das instituições financeiras a serem buscadas.
     * @return Lista de instituições financeiras encontradas.
     */
    private List<InstituicaoFinanceira> buscarInstituicoesFinanceirasPorIds(List<Long> instituicaoFinanceiraIds) {
        return instituicaoFinanceiraRepository.findAllById(instituicaoFinanceiraIds);
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

        if (dataInicio.isAfter(agora)) {
            return Status.ABERTO; // Leilão ainda não começou
        } else if (dataInicio.isBefore(agora) && dataFim.isAfter(agora)) {
            return Status.EM_ANDAMENTO; // Leilão está em progresso
        } else if (dataFim.isBefore(agora)) {
            return Status.FINALIZADO; // Leilão já terminou
        }

        return Status.ABERTO; // Padrão de segurança
    }

    /**
     * Cria um novo produto associado a um leilão com base nas informações fornecidas no {@link ProdutoDTO}.
     *
     * @param produtoDTO Objeto que contém as informações necessárias para criar o produto.
     * @return O produto criado.
     */
    @Transactional
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        // Busca e valida o leilão
        Leilao leilao = buscarLeilaoPorId(produtoDTO.getLeilaoId());

        // Cria a entidade Produto
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
        produto.setImagemUrl(produtoDTO.getImagemUrl());
        produto.setLeilao(leilao); // Associar o produto ao leilão

        // Salva no banco
        return produtoRepository.save(produto);
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
