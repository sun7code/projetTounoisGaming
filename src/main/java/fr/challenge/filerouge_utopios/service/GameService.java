package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.Game;
import fr.challenge.filerouge_utopios.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> findAll() {
        return repository.findAll();
    }

    public Game findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Game save(Game game) {
        return repository.save(game);
    }

    public void delete(Game game) {
        repository.delete(game);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
