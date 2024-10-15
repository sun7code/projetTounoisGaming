package fr.challenge.filerouge_utopios.entity;

import fr.challenge.filerouge_utopios.util.enums.Status;
import jakarta.persistence.*;
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
public class Game extends AbstractEntity {
    @Column(nullable = false)
    @NotNull(message = "You must enter a match date")
    private LocalDate matchDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.NOT_STARTED;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Result> results = new ArrayList<>();

    public List<User> getUsers() {
        return this.results.stream()
                .map(Result::getUser)
                .toList();
    }
}
