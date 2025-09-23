package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Criar usuário
    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar todos os usuários
    public List<Usuario> buscarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por id
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado : " + id));
    }

    // Buscar usuário por email
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Atualizar usuário
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setName(usuarioAtualizado.getName());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    if (usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().isEmpty()) {
                        usuario.setPassword(usuarioAtualizado.getPassword());
                    }
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
