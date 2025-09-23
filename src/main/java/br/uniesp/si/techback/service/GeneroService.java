package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.exception.ValidacaoException;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio relacionadas a Gêneros.
 */
@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * Lista todos os gêneros cadastrados.
     *
     * @return Lista de gêneros
     */
    @Transactional(readOnly = true)
    public List<Genero> listarTodos() {
        return generoRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Busca um gênero pelo ID.
     *
     * @param id ID do gênero
     * @return O gênero encontrado
     * @throws EntidadeNaoEncontradaException Se o gênero não for encontrado
     */
    @Transactional(readOnly = true)
    public Genero buscarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id));
    }

    /**
     * Busca um gênero pelo nome (case-insensitive).
     *
     * @param nome Nome do gênero
     * @return O gênero encontrado
     * @throws EntidadeNaoEncontradaException Se o gênero não for encontrado
     */
    @Transactional(readOnly = true)
    public Genero buscarPorNome(String nome) {
        return generoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Gênero não encontrado: " + nome));
    }

    /**
     * Salva um novo gênero.
     *
     * @param genero O gênero a ser salvo
     * @return O gênero salvo
     * @throws ValidacaoException Se já existir um gênero com o mesmo nome
     */
    @Transactional
    public Genero salvar(Genero genero) {
        validarGenero(genero);
        
        if (genero.getId() != null && generoRepository.existsByNomeIgnoreCaseAndIdNot(genero.getNome(), genero.getId())) {
            throw new ValidacaoException("Já existe um gênero com este nome.");
        } else if (genero.getId() == null && generoRepository.existsByNomeIgnoreCase(genero.getNome())) {
            throw new ValidacaoException("Já existe um gênero com este nome.");
        }
        
        return generoRepository.save(genero);
    }

    /**
     * Atualiza um gênero existente.
     *
     * @param id ID do gênero a ser atualizado
     * @param generoAtualizado Dados atualizados do gênero
     * @return O gênero atualizado
     * @throws EntidadeNaoEncontradaException Se o gênero não for encontrado
     */
    @Transactional
    public Genero atualizar(Long id, Genero generoAtualizado) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNome(generoAtualizado.getNome());
            genero.setDescricao(generoAtualizado.getDescricao());
            return generoRepository.save(genero);
        }).orElseThrow(() -> new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id));
    }

    /**
     * Exclui um gênero pelo ID.
     *
     * @param id ID do gênero a ser excluído
     * @throws EntidadeNaoEncontradaException Se o gênero não for encontrado
     */
    @Transactional
    public void excluir(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id);
        }
        generoRepository.deleteById(id);
    }

    /**
     * Busca gêneros que contenham o termo no nome (case-insensitive).
     *
     * @param termo Termo de busca
     * @return Lista de gêneros que correspondem ao termo
     */
    @Transactional(readOnly = true)
    public List<Genero> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        return generoRepository.findByNomeContainingIgnoreCase(termo);
    }

    /**
     * Valida os dados do gênero.
     *
     * @param genero O gênero a ser validado
     * @throws ValidacaoException Se a validação falhar
     */
    private void validarGenero(Genero genero) {
        if (genero.getNome() == null || genero.getNome().trim().isEmpty()) {
            throw new ValidacaoException("O nome do gênero é obrigatório");
        }
        
        if (genero.getNome().length() > 50) {
            throw new ValidacaoException("O nome do gênero não pode ter mais de 50 caracteres");
        }
        
        if (genero.getDescricao() != null && genero.getDescricao().length() > 200) {
            throw new ValidacaoException("A descrição não pode ter mais de 200 caracteres");
        }
    }
}
