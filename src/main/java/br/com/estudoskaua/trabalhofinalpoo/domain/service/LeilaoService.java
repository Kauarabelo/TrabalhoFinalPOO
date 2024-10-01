package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.domain.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.dto.ProdutoDTO;
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
 * LeilaoService
 *
 * @author kaua
 */
@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;

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

    // Método para buscar os produtos por IDs e garantir que existam
    private List<Produto> buscarProdutosPorIds(List<Long> produtoIds) {
        return produtoRepository.findAllById(produtoIds);
    }

    // Método para buscar as instituições financeiras por IDs e garantir que existam
    private List<InstituicaoFinanceira> buscarInstituicoesFinanceirasPorIds(List<Long> instituicaoFinanceiraIds) {
        return instituicaoFinanceiraRepository.findAllById(instituicaoFinanceiraIds);
    }

    // Método para definir o status do leilão com base nas datas de início e fim
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


    private Leilao buscarLeilaoPorId(Long leilaoId) {
        return leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new EntityNotFoundException("Leilão com ID " + leilaoId + " não encontrado"));
    }


}
