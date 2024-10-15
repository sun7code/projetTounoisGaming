package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.Country;
import fr.challenge.filerouge_utopios.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<Country> findAll() {
        return repository.findAll();
    }

    public Country findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Country findByLabel(String label) {
        return repository.findByLabel(label);
    }

    public Country findByTag(String tag) {
        return repository.findByTag(tag);
    }
}
