package fr.challenge.filerouge_utopios.entity;

import fr.challenge.filerouge_utopios.util.enums.Format;
import fr.challenge.filerouge_utopios.util.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Tournament extends AbstractEntity {
    @Column(nullable = false)
    @NotNull(message = "You must enter a label")
    @NotBlank(message = "You must enter a label")
    private String label;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "You must enter a format")
    private Format format;

    @Column(nullable = false)
    @NotNull(message = "You must enter a start date")
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull(message = "You must enter an end date")
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.NOT_STARTED;

    private int minAge;

    private int minElo;

    @ManyToMany(mappedBy = "tournaments", fetch = FetchType.EAGER)
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "tournament")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "tournament", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Game> games = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = true, name = "user_id", referencedColumnName = "id")
    private User creator;

    public int getScore(User user) {
        int score = 0;
        for (Game game : games) {
            for (Result result : game.getResults()) {
                if (result.getUser().equals(user)) {
                    score += result.getResult();
                }
            }
        }

        return score;
    }
}
