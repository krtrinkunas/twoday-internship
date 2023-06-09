package lt.krtrinkunas.twodayinternship.repository;

import lt.krtrinkunas.twodayinternship.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findBySpecies (String species);
}
