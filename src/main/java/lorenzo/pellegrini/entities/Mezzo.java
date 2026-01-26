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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_attuale", nullable = false)
    private StatoAttuale statoAttuale;

    @Column(name = "capacita")
    private int capacita;

    public Mezzo() {
    }

    public Mezzo(StatoAttuale statoAttuale, int capacita) {
        this.statoAttuale = statoAttuale;
        this.capacita = capacita;
    }

    public UUID getId() {
        return id;
    }

    public StatoAttuale getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(StatoAttuale statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", statoAttuale=" + statoAttuale +
                '}';
    }
}
