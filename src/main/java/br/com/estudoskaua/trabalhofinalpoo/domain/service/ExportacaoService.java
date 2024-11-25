package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.export.LeilaoDTOExport;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportacaoService {

    /**
     * Gera um arquivo .DET com as informações dos leilões no formato semelhante a CSV.
     * @param leiloes Lista de leilões para exportação.
     * @return O caminho do arquivo gerado ou uma mensagem de erro.
     */
    public String gerarArquivoDet(List<Leilao> leiloes) {
        List<LeilaoDTOExport> leilaoDTOExportList = leiloes.stream()
                .map(leilao -> {
                    LeilaoDTOExport dto = new LeilaoDTOExport();
                    dto.setDescricao(leilao.getDescricao());
                    dto.setProdutoNomes(leilao.getProdutos().stream()
                            .map(Produto::getNome)
                            .collect(Collectors.toList()));
                    dto.setInstituicaoFinanceiraNomes(leilao.getInstituicoesFinanceiras().stream()
                            .map(InstituicaoFinanceira::getNome)
                            .collect(Collectors.toList()));
                    dto.setDataInicio(leilao.getDataInicio());
                    dto.setDataFim(leilao.getDataFim());
                    dto.setEndereco(leilao.getEndereco());
                    dto.setCidade(leilao.getCidade());
                    dto.setEstado(leilao.getEstado());
                    return dto;
                })
                .collect(Collectors.toList());

        return salvarArquivoDet(leilaoDTOExportList);
    }

    /**
     * Salva as informações do leilão no formato .DET com estrutura semelhante a CSV.
     * @param leilaoDTOExList Lista de leilões para exportação.
     * @return Caminho do arquivo gerado.
     */
    private String salvarArquivoDet(List<LeilaoDTOExport> leilaoDTOExList) {
        String caminhoArquivo = "leiloes_exportados.det";
        File arquivo = new File(caminhoArquivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            // Cabeçalho do arquivo DET
            writer.write("Leilões Exportados\n");
            writer.write("Descrição;Produtos;Instituições Financeiras;Data Início;Data Fim;Endereço;Cidade;Estado\n");

            // Escreve os dados dos leilões
            for (LeilaoDTOExport leilao : leilaoDTOExList) {
                writer.write(formatarLinha(leilao));
            }

            return arquivo.getAbsolutePath(); // Caminho do arquivo gerado
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao gerar o arquivo .DET"; // Em caso de erro
        }
    }

    /**
     * Formata uma linha de exportação para o leilão no formato semelhante a CSV.
     * @param leilao Leilão a ser formatado.
     * @return Linha formatada.
     */
    private String formatarLinha(LeilaoDTOExport leilao) {
        String separador = ";";
        return String.join(separador,
                leilao.getDescricao(),
                formatarLista(leilao.getProdutoNomes(), ","),
                formatarLista(leilao.getInstituicaoFinanceiraNomes(), ","),
                leilao.getDataInicio() != null ? leilao.getDataInicio().toString() : "",
                leilao.getDataFim() != null ? leilao.getDataFim().toString() : "",
                leilao.getEndereco(),
                leilao.getCidade(),
                leilao.getEstado()
        ) + "\n";
    }

    /**
     * Formata uma lista como uma string separada por um delimitador.
     * @param lista Lista de valores.
     * @param delimitador Delimitador para os itens da lista.
     * @return String formatada.
     */
    private String formatarLista(List<String> lista, String delimitador) {
        return lista != null ? String.join(delimitador, lista) : "";
    }
}
