package lt.krtrinkunas.twodayinternship.repository;

import lt.krtrinkunas.twodayinternship.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
