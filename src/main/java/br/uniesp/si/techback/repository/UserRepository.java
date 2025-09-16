package br.uniesp.si.techback.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.uniesp.si.techback.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
