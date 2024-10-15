package fr.challenge.filerouge_utopios.repository;


import fr.challenge.filerouge_utopios.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}