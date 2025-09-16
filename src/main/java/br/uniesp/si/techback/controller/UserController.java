package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.User;
import br.uniesp.si.techback.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping
    public ResponseEntity<User> criarUser(@RequestBody User user) {
        User userSalvo = userRepository.save(user);
        return ResponseEntity.status(201).body(userSalvo);
    }
    @GetMapping
    public ResponseEntity<List<User>> listarUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    u.setPassword(user.getPassword());
                    User userAtualizado = userRepository.save(u);
                    return ResponseEntity.ok(userAtualizado);
                })
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }
}
