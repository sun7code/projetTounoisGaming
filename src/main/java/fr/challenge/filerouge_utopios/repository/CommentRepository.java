package fr.challenge.filerouge_utopios.repository;


import fr.challenge.filerouge_utopios.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}