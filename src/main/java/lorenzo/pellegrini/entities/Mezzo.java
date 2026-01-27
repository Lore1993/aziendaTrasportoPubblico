package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.StatoAttuale;

import java.util.UUID;

@Entity
@Table(name = "Mezzo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mezzo", discriminatorType = DiscriminatorType.STRING)
public abstract class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiato da UUID a IDENTITY
    private Long id;

    @Column(name = "capacita")
    private int capacita;

    public Mezzo() {
    }

    public Mezzo(int capacita) {
        this.capacita = capacita;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                '}';
    }
}
