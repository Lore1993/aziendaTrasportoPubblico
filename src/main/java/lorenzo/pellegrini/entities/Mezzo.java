package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.StatoAttuale;
import lorenzo.pellegrini.enums.TipoMezzo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Mezzo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Mezzo_di_Trasporto", discriminatorType = DiscriminatorType.STRING)
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiato da UUID a IDENTITY
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_attuale")
    private StatoAttuale statoAttuale;
    @Enumerated(EnumType.STRING)

    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @Column(name = "capacita")
    private int capacita;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<StatoMezzo> storicoStati = new ArrayList<>();

    public Mezzo() {
    }

    public Mezzo(TipoMezzo tipoMezzo, int capacita, StatoAttuale statoIniziale) {
        this.tipoMezzo = tipoMezzo;
        this.capacita = capacita;
        this.statoAttuale = statoIniziale;

        StatoMezzo stato = new StatoMezzo(LocalDate.now(), statoIniziale, this);
        this.storicoStati.add(stato);
    }


    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public long getId() {
        return id;
    }

    // Aggiunge un nuovo stato al mezzo
    public void aggiungiStato(StatoMezzo stato) {
        storicoStati.add(stato);
        stato.setMezzo(this);
    }

    // Restituisce lo stato attuale
    public StatoMezzo getStatoAttuale() {
        return storicoStati.stream()
                .filter(StatoMezzo::isStatoAttuale)
                .findFirst()
                .orElse(null);
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
