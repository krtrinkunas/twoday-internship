package lt.krtrinkunas.twodayinternship;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.krtrinkunas.twodayinternship.controller.AnimalController;
import lt.krtrinkunas.twodayinternship.controller.dto.*;
import lt.krtrinkunas.twodayinternship.model.Animal;
import lt.krtrinkunas.twodayinternship.model.Enclosure;
import lt.krtrinkunas.twodayinternship.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class AnimalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(animalController).build();
    }

    @Test
    public void testGetAnimal() throws Exception {
        Long animalId = 1L;
        Animal animal = new Animal(1L, "Lion", "Herbivore", 3, new Enclosure());
        AnimalReturnDto expectedDto = new AnimalReturnDto(animal);

        when(animalService.readAnimal(animalId)).thenReturn(animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/{id}", animalId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(expectedDto)));

        verify(animalService, times(1)).readAnimal(animalId);
    }

    @Test
    public void testGetAllAnimals() throws Exception {
        List<Animal> animalList = Arrays.asList(new Animal(1L, "Lion", "Herbivore", 3, new Enclosure()));
        List<AnimalReturnDto> expectedDtoList = animalList.stream()
                .map(AnimalReturnDto::new)
                .collect(Collectors.toList());

        when(animalService.readAllAnimals()).thenReturn(animalList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(expectedDtoList)));

        verify(animalService, times(1)).readAllAnimals();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
