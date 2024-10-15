package fr.challenge.filerouge_utopios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Message extends AbstractEntity {
    @Column(nullable = false)
    @NotNull(message = "You must enter a label")
    @NotBlank(message = "You must enter a label")
    private String label;

    @Column(nullable = false, length = 2500)
    @NotNull(message = "You must enter a message")
    @NotBlank(message = "You must enter a message")
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    @NotNull(message = "You must enter a user")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tournament_id", referencedColumnName = "id")
    @NotNull(message = "You must enter a tournament")
    private Tournament tournament;
}
