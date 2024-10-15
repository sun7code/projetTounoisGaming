package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.Comment;
import fr.challenge.filerouge_utopios.entity.Message;
import fr.challenge.filerouge_utopios.repository.CommentRepository;
import fr.challenge.filerouge_utopios.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    public void delete(Comment comment) {
        repository.delete(comment);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
