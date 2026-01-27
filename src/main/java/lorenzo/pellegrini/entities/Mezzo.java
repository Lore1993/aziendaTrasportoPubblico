package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.StatoAttuale;
import lorenzo.pellegrini.enums.TipoMezzo;

@Entity
@Table(name = "Mezzo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mezzo", discriminatorType = DiscriminatorType.STRING)
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiato da UUID a IDENTITY
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_attuale", nullable = false)
    private StatoAttuale statoAttuale;
    @Enumerated(EnumType.STRING)

    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @Column(name = "capacita")
    private int capacita;

    public Mezzo() {
    }

    public Mezzo(StatoAttuale statoAttuale, int capacita) {
        this.statoAttuale = statoAttuale;
        this.capacita = capacita;
    }

    public Mezzo(int capacita, TipoMezzo tipoMezzo) {
        this.capacita = capacita;
        this.tipoMezzo = tipoMezzo;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public long getId() {
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
