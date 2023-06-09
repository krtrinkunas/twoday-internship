package lt.krtrinkunas.twodayinternship.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AnimalListDto {
    private List<AnimalDto> animals;
}