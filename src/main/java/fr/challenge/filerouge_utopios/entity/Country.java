package fr.challenge.filerouge_utopios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Country extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String label;
    @Column(nullable = false, unique = true)
    private String tag;
}
