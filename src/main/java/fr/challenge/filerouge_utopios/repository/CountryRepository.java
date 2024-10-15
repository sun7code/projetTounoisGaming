package fr.challenge.filerouge_utopios.repository;


import fr.challenge.filerouge_utopios.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByLabel(String label);

    Country findByTag(String tag);
}