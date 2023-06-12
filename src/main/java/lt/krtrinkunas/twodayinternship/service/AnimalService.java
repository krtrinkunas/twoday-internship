package lt.krtrinkunas.twodayinternship.service;

import lt.krtrinkunas.twodayinternship.controller.dto.AnimalDto;
import lt.krtrinkunas.twodayinternship.controller.dto.AnimalListDto;
import lt.krtrinkunas.twodayinternship.exception.EntityAlreadyExistsException;
import lt.krtrinkunas.twodayinternship.exception.EntityNotFoundException;
import lt.krtrinkunas.twodayinternship.model.Animal;
import lt.krtrinkunas.twodayinternship.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    private final AssignmentService assignmentService;

    @Autowired
    public AnimalService(AnimalRepository animalRepository, AssignmentService assignmentService) {
        this.animalRepository = animalRepository;
        this.assignmentService = assignmentService;
    }

    @Transactional
    public void createAnimals(AnimalListDto animalDtos) {
        for (AnimalDto animalDto : animalDtos.getAnimals()) {
            createSingleAnimal(animalDto);
        }
        reassignEnclosures();
    }

    @Transactional
    public void createSingleAnimal(AnimalDto animalDto) {
        try {
            Animal animal = new Animal();
            animal.setSpecies(animalDto.getSpecies());
            animal.setFood(animalDto.getFood());
            animal.setAmount(animalDto.getAmount());
            animalRepository.save(animal);
            reassignEnclosures();
        } catch (Exception e) {
            throw new EntityAlreadyExistsException();
        }
    }

    public Animal updateAnimal(Long id, AnimalDto animalDto) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) throw new EntityNotFoundException();
        else {
            try {
                boolean isLarger = false;
                Animal animal = optionalAnimal.get();
                if(animal.getAmount() < animalDto.getAmount()) {
                    isLarger = true;
                }
                animal.setSpecies(animalDto.getSpecies());
                animal.setFood(animalDto.getFood());
                animal.setAmount(animalDto.getAmount());
                animalRepository.save(animal);
                if(isLarger) {
                    reassignEnclosures();
                }
                return animal;
            } catch (Exception e) {
                throw new EntityAlreadyExistsException();
            }
        }
    }

    public void deleteAnimal(Long id) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) throw new EntityNotFoundException();
        else {
            animalRepository.delete(optionalAnimal.get());
        }
    }

    public Animal readAnimal(Long id) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) throw new EntityNotFoundException();
        else {
            return optionalAnimal.get();
        }
    }

    public List<Animal> readAllAnimals() {
        return animalRepository.findAll();
    }

    private void reassignEnclosures() {
        List<Animal> animalList = readAllAnimals();
        animalList = assignmentService.startAssignment(animalList);
        animalRepository.saveAll(animalList);
    }
}
