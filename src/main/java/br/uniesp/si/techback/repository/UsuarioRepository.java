package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.model.Usuario.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Verifica se um email já está em uso
    boolean existsByEmail(String email);
    
    // Busca um usuário pelo email
    Optional<Usuario> findByEmail(String email);
    
    // Busca usuários por nome (case insensitive, contendo o termo)
    List<Usuario> findByNameContainingIgnoreCase(String name);
    
    // Busca usuários por papel (role)
    List<Usuario> findByRole(UserRole role);
    
    // Busca usuários por nome e papel
    List<Usuario> findByNameContainingIgnoreCaseAndRole(String name, UserRole role);
    
    // Busca paginada de usuários ordenados por nome
    Page<Usuario> findAllByOrderByName(Pageable pageable);
    
    // Busca usuários por parte do email (case insensitive)
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.email) LIKE LOWER(concat('%', :email, '%'))")
    List<Usuario> buscarPorEmailParcial(@Param("email") String email);
    
    // Busca usuários por nome ou email (case insensitive)
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.name) LIKE LOWER(concat('%', :termo, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(concat('%', :termo, '%'))")
    List<Usuario> buscarPorNomeOuEmail(@Param("termo") String termo);
    
    // Atualiza a senha de um usuário
    @Modifying
    @Query("UPDATE Usuario u SET u.password = :novaSenha WHERE u.id = :id")
    void atualizarSenha(@Param("id") Long id, @Param("novaSenha") String novaSenha);
    
    // Atualiza o papel de um usuário
    @Modifying
    @Query("UPDATE Usuario u SET u.role = :novoRole WHERE u.id = :id")
    void atualizarRole(@Param("id") Long id, @Param("novoRole") UserRole novoRole);
    
    // Verifica se existe outro usuário com o mesmo email (excluindo o usuário com o ID especificado)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
    
    // Conta usuários por papel
    long countByRole(UserRole role);
    
    // Busca os N primeiros usuários ordenados por nome
    List<Usuario> findFirst10ByOrderByName();
}
