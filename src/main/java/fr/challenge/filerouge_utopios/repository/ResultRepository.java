package fr.challenge.filerouge_utopios.repository;

import fr.challenge.filerouge_utopios.entity.Game;
import fr.challenge.filerouge_utopios.entity.Result;
import fr.challenge.filerouge_utopios.entity.ResultId;
import fr.challenge.filerouge_utopios.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, ResultId> {
    List<Result> findAllByGame(Game game);

    List<Result> findAllByUser(User user);
}
