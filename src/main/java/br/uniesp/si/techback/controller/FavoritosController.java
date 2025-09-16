package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favoritos;
import br.uniesp.si.techback.service.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritosController {

    private final FavoritosService favoritosService;

    @Autowired
    public FavoritosController(FavoritosService favoritosService) {
        this.favoritosService = favoritosService;
    }

    @PostMapping
    public ResponseEntity<Favoritos> adicionarFavorito(@RequestBody Favoritos favorito) {
        Favoritos novoFavorito = favoritosService.salvarFavorito(favorito);
        return new ResponseEntity<>(novoFavorito, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Favoritos>> listarFavoritos() {
        List<Favoritos> favoritos = favoritosService.listarFavoritos();
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favoritos> buscarPorId(@PathVariable Long id) {
        return favoritosService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favoritos>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<Favoritos> favoritos = favoritosService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFavorito(@PathVariable Long id) {
        favoritosService.removerFavorito(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Favoritos> atualizarFavorito(
            @PathVariable Long id,
            @RequestBody Favoritos favoritoAtualizado) {
        return favoritosService.atualizarFavorito(id, favoritoAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
