package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a Gêneros.
 */
@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Cria um novo gênero.
     *
     * @param genero O gênero a ser criado
     * @return O gênero criado com status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Genero> criar(@RequestBody Genero genero) {
        Genero generoCriado = generoService.salvar(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoCriado);
    }

    /**
     * Lista todos os gêneros cadastrados.
     *
     * @return Lista de gêneros com status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        List<Genero> generos = generoService.listarTodos();
        return ResponseEntity.ok(generos);
    }

    /**
     * Busca um gênero pelo ID.
     *
     * @param id ID do gênero
     * @return O gênero encontrado com status 200 (OK) ou 404 (Not Found) se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
        Genero genero = generoService.buscarPorId(id);
        return ResponseEntity.ok(genero);
    }

    /**
     * Atualiza um gênero existente.
     *
     * @param id ID do gênero a ser atualizado
     * @param generoAtualizado Dados atualizados do gênero
     * @return O gênero atualizado com status 200 (OK) ou 404 (Not Found) se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizar(
            @PathVariable Long id,
            @RequestBody Genero generoAtualizado) {
        Genero genero = generoService.atualizar(id, generoAtualizado);
        return ResponseEntity.ok(genero);
    }

    /**
     * Exclui um gênero pelo ID.
     *
     * @param id ID do gênero a ser excluído
     * @return Resposta vazia com status 204 (No Content) ou 404 (Not Found) se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        generoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca gêneros por termo (case-insensitive).
     *
     * @param termo Termo de busca
     * @return Lista de gêneros que correspondem ao termo com status 200 (OK)
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Genero>> buscarPorTermo(@RequestParam(required = false) String termo) {
        List<Genero> generos = generoService.buscarPorTermo(termo != null ? termo : "");
        return ResponseEntity.ok(generos);
    }

    /**
     * Busca um gênero pelo nome (case-insensitive).
     *
     * @param nome Nome do gênero
     * @return O gênero encontrado com status 200 (OK) ou 404 (Not Found) se não encontrado
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Genero> buscarPorNome(@PathVariable String nome) {
        Genero genero = generoService.buscarPorNome(nome);
        return ResponseEntity.ok(genero);
    }
}
