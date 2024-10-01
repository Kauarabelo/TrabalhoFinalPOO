package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.dto.LeilaoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.service.LeilaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leiloes") // Usar plural para a URL
public class LeilaoController {

    private final LeilaoRepository leilaoRepository;
    private final LeilaoService leilaoService;

    public LeilaoController(LeilaoRepository leilaoRepository, LeilaoService leilaoService) {
        this.leilaoRepository = leilaoRepository;
        this.leilaoService = leilaoService;
    }

    @GetMapping
    public List<Leilao> listarTodos() {
        return leilaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leilao> buscarPorId(@PathVariable Long id) {
        return leilaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Envia os dados para o service para tratar o cliente_id e produto_id
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Leilao> criarLeilao(@RequestBody LeilaoDTO leilaoDTO) {
        Leilao leilao = leilaoService.criarLeilao(leilaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(leilao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leilao> atualizar(@PathVariable Long id, @Valid @RequestBody Leilao leilao) {
        if (!leilaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        leilao.setId(id);
        Leilao updatedLeilao = leilaoRepository.save(leilao);
        return ResponseEntity.ok(updatedLeilao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!leilaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        leilaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
