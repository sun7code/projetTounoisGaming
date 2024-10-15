package fr.challenge.filerouge_utopios.repository;


import fr.challenge.filerouge_utopios.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);

    User findByEmail(String email);

    User findByPseudo(String pseudo);
}
