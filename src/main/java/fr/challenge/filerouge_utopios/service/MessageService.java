package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.Message;
import fr.challenge.filerouge_utopios.entity.User;
import fr.challenge.filerouge_utopios.repository.MessageRepository;
import fr.challenge.filerouge_utopios.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> findAll() {
        return repository.findAll();
    }

    public Message findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Message save(Message message) {
        return repository.save(message);
    }

    public void delete(Message message) {
        repository.delete(message);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
