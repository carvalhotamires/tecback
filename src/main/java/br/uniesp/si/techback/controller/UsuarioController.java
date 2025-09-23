package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.UsuarioRequest;
import br.uniesp.si.techback.dto.UsuarioResponse;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest,
                                                      UriComponentsBuilder uriBuilder) {
        log.info("Criando novo usuário com email: {}", usuarioRequest.getEmail());
        
        if (userRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setName(usuarioRequest.getName());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        
        Usuario usuarioSalvo = userRepository.save(usuario);
        
        URI uri = uriBuilder.path("/api/usuarios/{id}").buildAndExpand(usuarioSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(UsuarioResponse.fromEntity(usuarioSalvo));
    }
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacao) {
        log.info("Listando usuários com paginação: {}", paginacao);
        Page<Usuario> usuarios = userRepository.findAll(paginacao);
        return ResponseEntity.ok(usuarios.map(UsuarioResponse::fromEntity));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuario(@PathVariable Long id) {
        log.info("Buscando usuário com id: {}", id);
        return userRepository.findById(id)
                .map(UsuarioResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, 
                                                          @Valid @RequestBody UsuarioRequest usuarioRequest) {
        log.info("Atualizando usuário com id: {}", id);
        
        return userRepository.findById(id)
                .map(usuarioExistente -> {
                    if (!usuarioExistente.getEmail().equals(usuarioRequest.getEmail()) && 
                        userRepository.existsByEmail(usuarioRequest.getEmail())) {
                        throw new IllegalArgumentException("Email já está em uso por outro usuário");
                    }
                    
                    usuarioExistente.setName(usuarioRequest.getName());
                    usuarioExistente.setEmail(usuarioRequest.getEmail());
                    
                    // Só atualiza a senha se for fornecida e for diferente da atual
                    if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
                        usuarioExistente.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
                    }
                    
                    Usuario usuarioAtualizado = userRepository.save(usuarioExistente);
                    return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioAtualizado));
                })
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable Long id) {
        log.info("Deletando usuário com id: {}", id);
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
        
        userRepository.delete(usuario);
    }
}
