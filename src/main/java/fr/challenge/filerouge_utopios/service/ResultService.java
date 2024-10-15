package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.Game;
import fr.challenge.filerouge_utopios.entity.Result;
import fr.challenge.filerouge_utopios.entity.ResultId;
import fr.challenge.filerouge_utopios.entity.User;
import fr.challenge.filerouge_utopios.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepository repository;

    public ResultService(ResultRepository repository) {
        this.repository = repository;
    }

    public List<Result> findAll() {
        return repository.findAll();
    }

    public Result findById(ResultId id) {
        return repository.findById(id).orElse(null);
    }

    public List<Result> findAllByGame(Game game) {
        return repository.findAllByGame(game);
    }

    public List<Result> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public Result save(Result result) {
        return repository.save(result);
    }

    public void delete(Result result) {
        repository.delete(result);
    }

    public void deleteById(ResultId id) {
        repository.deleteById(id);
    }
}
