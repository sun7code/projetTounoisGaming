package fr.challenge.filerouge_utopios.repository;

import fr.challenge.filerouge_utopios.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
