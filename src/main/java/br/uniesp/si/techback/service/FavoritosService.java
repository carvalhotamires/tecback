package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Favoritos;
import br.uniesp.si.techback.repository.FavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritosService {

    private final FavoritosRepository favoritosRepository;

    @Autowired
    public FavoritosService(FavoritosRepository favoritosRepository) {
        this.favoritosRepository = favoritosRepository;
    }

    public Favoritos salvarFavorito(Favoritos favorito) {
        return favoritosRepository.save(favorito);
    }

    public List<Favoritos> listarFavoritos() {
        return favoritosRepository.findAll();
    }

    public Optional<Favoritos> buscarPorId(Long id) {
        return favoritosRepository.findById(id);
    }

    public List<Favoritos> buscarPorUsuario(Long usuarioId) {
        // Implemente a lógica para buscar favoritos por usuário
        // Exemplo: return favoritosRepository.findByUsuarioId(usuarioId);
        // Você precisará adicionar o método correspondente no FavoritosRepository
        return favoritosRepository.findAll(); // Implementação temporária
    }

    public void removerFavorito(Long id) {
        favoritosRepository.deleteById(id);
    }

    public Optional<Favoritos> atualizarFavorito(Long id, Favoritos favoritoAtualizado) {
        return favoritosRepository.findById(id)
                .map(favorito -> {
                    // Atualize os campos necessários do favorito existente
                    // com os valores do favoritoAtualizado
                    // Exemplo:
                    // favorito.setAlgumCampo(favoritoAtualizado.getAlgumCampo());
                    return favoritosRepository.save(favorito);
                });
    }
}
