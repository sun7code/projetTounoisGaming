package fr.challenge.filerouge_utopios.entity;

import fr.challenge.filerouge_utopios.util.enums.AccountLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends AbstractEntity {
    @Column(nullable = false, unique = true)
    @NotNull(message = "You must enter an email")
    @NotBlank(message = "You must enter an email")
    @Email(message = "You must enter a valid email")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "You must enter an username")
    @NotBlank(message = "You must enter a password")
    private String password;

    @Column(nullable = false, unique = true)
    @NotNull(message = "You must enter a pseudo")
    @NotBlank(message = "You must enter a pseudo")
    private String pseudo;

    @Column(nullable = false)
    @NotNull(message = "You must enter a birth date")
    @Past(message = "Birth date cannot be in the future")
    private LocalDate birthDate;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate registrationDate = LocalDate.now();

    @Column(nullable = false)
    @Builder.Default
    private int elo = 1000;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AccountLevel accountLevel = AccountLevel.USER;

    @ManyToOne
    @JoinColumn(nullable = false, name = "country_id", referencedColumnName = "id")
    @NotNull(message = "You must enter a country")
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_tournament",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "id"))
    @Builder.Default
    private List<Tournament> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Result> results = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    public List<Game> getGames() {
        return this.results.stream()
                .map(Result::getGame)
                .toList();
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        return now.getYear() - birthDate.getYear() - ((now.getMonthValue() < birthDate.getMonthValue()) ? 1 : 0);
    }
}
