package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Planos;
import br.uniesp.si.techback.service.PlanosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanosController {

    private final PlanosService planosService;

    @Autowired
    public PlanosController(PlanosService planosService) {
        this.planosService = planosService;
    }

    @PostMapping
    public ResponseEntity<Planos> criarPlano(@RequestBody Planos plano) {
        Planos novoPlano = planosService.salvar(plano);
        return new ResponseEntity<>(novoPlano, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Planos>> listarTodos() {
        List<Planos> planos = planosService.listarTodos();
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Planos>> listarAtivos() {
        List<Planos> planos = planosService.listarAtivos();
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Planos>> listarPaginado(
            @PageableDefault(size = 10, sort = {"preco"}) Pageable pageable) {
        Page<Planos> planos = planosService.listarAtivosPaginados(pageable);
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planos> buscarPorId(@PathVariable Long id) {
        Planos plano = planosService.buscarPorId(id);
        return new ResponseEntity<>(plano, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planos> atualizarPlano(
            @PathVariable Long id, @RequestBody Planos planoAtualizado) {
        Planos plano = planosService.atualizar(id, planoAtualizado);
        return new ResponseEntity<>(plano, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPlano(@PathVariable Long id) {
        planosService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id, @RequestParam boolean ativo) {
        planosService.ativarDesativar(id, ativo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/faixa-preco")
    public ResponseEntity<List<Planos>> buscarPorFaixaPreco(
            @RequestParam BigDecimal precoMinimo,
            @RequestParam BigDecimal precoMaximo) {
        List<Planos> planos = planosService.buscarPorFaixaPreco(precoMinimo, precoMaximo);
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/mais-caro")
    public ResponseEntity<Planos> buscarMaisCaro() {
        return planosService.encontrarPlanoMaisCaro()
                .map(plano -> new ResponseEntity<>(plano, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/mais-barato")
    public ResponseEntity<Planos> buscarMaisBarato() {
        return planosService.encontrarPlanoMaisBarato()
                .map(plano -> new ResponseEntity<>(plano, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Planos>> buscarPorTermo(@RequestParam(required = false) String termo) {
        List<Planos> planos = planosService.buscarPorTermo(termo != null ? termo : "");
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/contar-ativos")
    public ResponseEntity<Long> contarPlanosAtivos() {
        long total = planosService.contarPlanosAtivos();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}
