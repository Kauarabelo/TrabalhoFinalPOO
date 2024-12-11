package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.export.ClienteDTOExport;
import br.com.estudoskaua.trabalhofinalpoo.api.dto.export.LanceDTOExport;
import br.com.estudoskaua.trabalhofinalpoo.api.dto.export.LeilaoDTOExport;
import br.com.estudoskaua.trabalhofinalpoo.api.dto.export.ProdutoDTOExport;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ExportacaoService extends LeilaoDTOExport {

    private final ObjectMapper objectMapper;

    public ExportacaoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Gera o arquivo .DET contendo todos os leilões.
     *
     * @param leiloes Lista de leilões a serem exportados.
     * @return Caminho do arquivo gerado.
     * @throws IOException caso ocorra erro ao gerar o arquivo.
     */
    public String gerarArquivoDet(List<Leilao> leiloes) throws IOException {
        // Geração do caminho do arquivo
        String caminhoArquivo = "leiloes_exportados.det";

        // Cria o arquivo de saída
        File arquivo = new File(caminhoArquivo);

        // Escreve os leilões no arquivo .DET como JSON
        try {
            objectMapper.writeValue(arquivo, leiloes); // Utiliza o ObjectMapper para serializar os leilões
        } catch (IOException e) {
            throw new IOException("Erro ao gerar o arquivo .DET", e);
        }

        return caminhoArquivo;
    }
}
