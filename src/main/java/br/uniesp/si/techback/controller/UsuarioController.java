package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.UsuarioRequest;
import br.uniesp.si.techback.dto.UsuarioResponse;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
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

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest,
                                                      UriComponentsBuilder uriBuilder) {
        log.info("Criando novo usuário com email: {}", usuarioRequest.getEmail());
        
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setName(usuarioRequest.getName());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        
        URI uri = uriBuilder.path("/api/usuarios/{id}").buildAndExpand(usuarioSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(UsuarioResponse.fromEntity(usuarioSalvo));
    }
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacao) {
        log.info("Listando usuários com paginação: {}", paginacao);
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return ResponseEntity.ok(usuarios.map(UsuarioResponse::fromEntity));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuario(@PathVariable Long id) {
        log.info("Buscando usuário com id: {}", id);
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, 
                                                          @Valid @RequestBody UsuarioRequest usuarioRequest) {
        log.info("Atualizando usuário com id: {}", id);
        
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    if (!usuarioExistente.getEmail().equals(usuarioRequest.getEmail()) && 
                        usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
                        throw new IllegalArgumentException("Email já está em uso por outro usuário");
                    }
                    
                    usuarioExistente.setName(usuarioRequest.getName());
                    usuarioExistente.setEmail(usuarioRequest.getEmail());
                    
                    // Só atualiza a senha se for fornecida e for diferente da atual
                    if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
                        usuarioExistente.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
                    }
                    
                    Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioAtualizado));
                })
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable Long id) {
        log.info("Deletando usuário com id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
        
        usuarioRepository.delete(usuario);
    }
}
