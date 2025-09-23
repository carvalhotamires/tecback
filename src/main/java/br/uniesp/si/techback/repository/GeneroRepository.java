package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas à entidade Genero.
 */
@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    
    /**
     * Busca um gênero pelo nome (case-insensitive).
     *
     * @param nome Nome do gênero a ser buscado
     * @return Optional contendo o gênero, se encontrado
     */
    Optional<Genero> findByNomeIgnoreCase(String nome);
    
    /**
     * Verifica se existe um gênero com o nome especificado (case-insensitive).
     *
     * @param nome Nome do gênero a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByNomeIgnoreCase(String nome);
    
    /**
     * Retorna todos os gêneros ordenados por nome em ordem alfabética.
     *
     * @return Lista de gêneros ordenados
     */
    List<Genero> findAllByOrderByNomeAsc();
    
    /**
     * Busca gêneros que contenham o termo no nome (case-insensitive).
     *
     * @param termo Termo de busca
     * @return Lista de gêneros que correspondem ao termo
     */
    List<Genero> findByNomeContainingIgnoreCase(String termo);
}
