package lt.krtrinkunas.twodayinternship.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.krtrinkunas.twodayinternship.model.Animal;

@Getter
@Setter
@NoArgsConstructor
public class AnimalReturnDto {
    public AnimalReturnDto(Animal animal) {
        this.id = animal.getId();
        this.species = animal.getSpecies();
        this.food = animal.getFood();
        this.amount = animal.getAmount();
        this.enclosure = animal.getEnclosure().getName();
    }
    private Long id;
    private String species;
    private String food;
    private int amount;
    private String enclosure;
}
