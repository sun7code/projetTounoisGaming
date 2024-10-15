package fr.challenge.filerouge_utopios.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(ResultId.class)
@Getter
@Setter
public class Result {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Id
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Game game;

    private Integer result;
}
