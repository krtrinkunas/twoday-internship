package lt.krtrinkunas.twodayinternship.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnclosureDto {
    private String name;
    private String size;
    private String location;
    private List<String> objects;
}