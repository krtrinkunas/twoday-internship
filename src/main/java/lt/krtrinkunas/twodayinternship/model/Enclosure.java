package lt.krtrinkunas.twodayinternship.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "enclosure")
    private Set<Animal> animals;

    @OneToMany(mappedBy = "enclosure")
    private Set<EnclosureObject> enclosureObjects;
}
