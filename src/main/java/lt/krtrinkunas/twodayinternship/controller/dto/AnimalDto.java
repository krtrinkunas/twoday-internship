package lt.krtrinkunas.twodayinternship.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.krtrinkunas.twodayinternship.model.Animal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    public AnimalDto(Animal animal) {
        this.species = animal.getSpecies();
        this.food = animal.getFood();
        this.amount = animal.getAmount();
    }
    private String species;
    private String food;
    private int amount;
}