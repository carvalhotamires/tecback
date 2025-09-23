package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Enderecos;
import br.uniesp.si.techback.service.EnderecosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecosController {

    private final EnderecosService enderecosService;

    @Autowired
    public EnderecosController(EnderecosService enderecosService) {
        this.enderecosService = enderecosService;
    }

    @PostMapping
    public ResponseEntity<Enderecos> criarEndereco(@RequestBody Enderecos endereco) {
        Enderecos novoEndereco = enderecosService.salvarEndereco(endereco);
        return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Enderecos>> listarEnderecos() {
        List<Enderecos> enderecos = enderecosService.listarEnderecos();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enderecos> buscarEnderecoPorId(@PathVariable Long id) {
        return enderecosService.buscarEnderecoPorId(id)
                .map(endereco -> new ResponseEntity<>(endereco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enderecos> atualizarEndereco(@PathVariable Long id, @RequestBody Enderecos enderecoAtualizado) {
        return enderecosService.buscarEnderecoPorId(id)
                .map(endereco -> {
                    enderecoAtualizado.setId(id);
                    Enderecos enderecoAtualizadoSalvo = enderecosService.salvarEndereco(enderecoAtualizado);
                    return new ResponseEntity<>(enderecoAtualizadoSalvo, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        return enderecosService.buscarEnderecoPorId(id)
                .map(endereco -> {
                    enderecosService.deletarEndereco(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
