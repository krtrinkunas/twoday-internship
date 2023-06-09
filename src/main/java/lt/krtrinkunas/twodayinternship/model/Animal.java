package lt.krtrinkunas.twodayinternship.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String species;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    private Enclosure enclosure;
}
