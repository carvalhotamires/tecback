package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Enderecos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecosRepository extends JpaRepository<Enderecos, Long> {
    
    // Consultas básicas
    List<Enderecos> findByCidade(String cidade);
    List<Enderecos> findByEstado(String estado);
    List<Enderecos> findByCep(String cep);
    
    // Consultas combinadas
    List<Enderecos> findByCidadeAndEstado(String cidade, String estado);
    List<Enderecos> findByLogradouroContainingIgnoreCase(String termo);
    
    // Consulta com ordenação
    List<Enderecos> findByCidadeOrderByBairroAsc(String cidade);
    
    // Consulta personalizada com JPQL
    @Query("SELECT e FROM Enderecos e WHERE e.cidade = :cidade AND e.estado = :estado")
    List<Enderecos> buscarPorCidadeEEstado(@Param("cidade") String cidade, @Param("estado") String estado);
    
    // Consulta com paginação
    Page<Enderecos> findByCidade(String cidade, Pageable pageable);
    
    // Busca por CEP e número (retorna um único resultado)
    Optional<Enderecos> findByCepAndNumero(String cep, String numero);
    
    // Busca por bairro (case insensitive)
    List<Enderecos> findByBairroIgnoreCase(String bairro);
    
    // Busca por cidade e bairro
    List<Enderecos> findByCidadeAndBairro(String cidade, String bairro);
    
    // Contagem de endereços por estado
    @Query("SELECT COUNT(e) FROM Enderecos e WHERE e.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Verifica se existe endereço com o CEP informado
    boolean existsByCep(String cep);
    
    // Busca por CEP usando LIKE (para busca parcial)
    @Query("SELECT e FROM Enderecos e WHERE e.cep LIKE %:cep%")
    List<Enderecos> buscarPorCepParcial(@Param("cep") String cep);
    
    // Busca por cidade ou bairro contendo o termo (case insensitive)
    @Query("SELECT e FROM Enderecos e WHERE LOWER(e.cidade) LIKE LOWER(concat('%', :termo, '%')) OR " +
           "LOWER(e.bairro) LIKE LOWER(concat('%', :termo, '%'))")
    List<Enderecos> buscarPorCidadeOuBairro(@Param("termo") String termo);
}
