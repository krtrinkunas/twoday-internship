package lt.krtrinkunas.twodayinternship.controller;

import lt.krtrinkunas.twodayinternship.controller.dto.AnimalDto;
import lt.krtrinkunas.twodayinternship.controller.dto.AnimalListDto;
import lt.krtrinkunas.twodayinternship.controller.dto.AnimalReturnDto;
import lt.krtrinkunas.twodayinternship.model.Animal;
import lt.krtrinkunas.twodayinternship.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AnimalController {
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/animals")
    public void loadAnimals(@RequestBody AnimalListDto animals) {
        animalService.createAnimals(animals);
    }

    @PostMapping("/animal")
    public void loadSingleAnimal(@RequestBody AnimalDto animalDto) {
        animalService.createAnimal(animalDto);
    }

    @GetMapping("/animals/{id}")
    public AnimalReturnDto getAnimal(@PathVariable Long id) {
        Animal animal =  animalService.readAnimal(id);
        return new AnimalReturnDto(animal);
    }

    @GetMapping("/animals")
    public List<AnimalReturnDto> getAllAnimals() {
        List<Animal> animalList = animalService.readAllAnimals();
        return animalList.stream()
                .map(AnimalReturnDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/animals/{id}")
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }

    @PutMapping("/animals/{id}")
    public AnimalReturnDto updateAnimal(@PathVariable Long id, @RequestBody AnimalDto animalDto) {
        Animal animal = animalService.updateAnimal(id, animalDto);
        return new AnimalReturnDto(animal);
    }
}
