package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Planos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanosRepository extends JpaRepository<Planos, Long> {
    
    // Busca por nome (case insensitive, contendo o termo)
    List<Planos> findByNomeContainingIgnoreCase(String nome);
    
    // Busca planos ativos
    List<Planos> findByAtivoTrue();
    
    // Busca planos por faixa de preço
    List<Planos> findByPrecoBetween(BigDecimal precoMinimo, BigDecimal precoMaximo);
    
    // Busca planos com preço menor ou igual ao valor informado
    List<Planos> findByPrecoLessThanEqual(BigDecimal precoMaximo);
    
    // Busca planos por duração mínima
    List<Planos> findByDuracaoDiasGreaterThanEqual(Integer duracaoMinima);
    
    // Busca planos por número mínimo de dispositivos
    List<Planos> findByLimiteDispositivosGreaterThanEqual(Integer limiteMinimo);
    
    // Verifica se já existe um plano com o mesmo nome (ignorando case)
    boolean existsByNomeIgnoreCase(String nome);
    
    // Busca paginada de planos ativos ordenados por preço
    Page<Planos> findByAtivoTrueOrderByPreco(Pageable pageable);
    
    // Busca por nome ou descrição (case insensitive)
    @Query("SELECT p FROM Planos p WHERE " +
           "LOWER(p.nome) LIKE LOWER(concat('%', :termo, '%')) OR " +
           "LOWER(p.descricao) LIKE LOWER(concat('%', :termo, '%'))")
    List<Planos> buscarPorNomeOuDescricao(@Param("termo") String termo);
    
    // Busca o plano mais caro
    @Query("SELECT p FROM Planos p WHERE p.preco = (SELECT MAX(p2.preco) FROM Planos p2)")
    Optional<Planos> encontrarPlanoMaisCaro();
    
    // Busca o plano mais barato
    @Query("SELECT p FROM Planos p WHERE p.preco = (SELECT MIN(p2.preco) FROM Planos p2)")
    Optional<Planos> encontrarPlanoMaisBarato();
    
    // Conta quantos planos estão ativos
    long countByAtivoTrue();
    
    // Verifica se existe outro plano com o mesmo nome (excluindo o plano com o ID especificado)
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM Planos p WHERE LOWER(p.nome) = LOWER(:nome) AND p.id <> :id")
    boolean existsByNomeIgnoreCaseAndIdNot(@Param("nome") String nome, @Param("id") Long id);
}
