package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.TipoMezzo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mezzo")
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacita", nullable = false)
    private int capacita;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @OneToMany(mappedBy = "mezzo")
    private List<Percorrenza> percorrenze = new ArrayList<>();

    @OneToMany(mappedBy = "mezzo")
    private List<StatoMezzo> storicoStati = new ArrayList<>();

    // Costruttori
    public Mezzo() {
    }

    public Mezzo(int capacita, TipoMezzo tipoMezzo) {
        this.capacita = capacita;
        this.tipoMezzo = tipoMezzo;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public int getCapacita() {
        return capacita;
    }

    public void setCapacita(int capacita) {
        this.capacita = capacita;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public List<Percorrenza> getPercorrenze() {
        return percorrenze;
    }

    public void setPercorrenze(List<Percorrenza> percorrenze) {
        this.percorrenze = percorrenze;
    }

    public List<StatoMezzo> getStoricoStati() {
        return storicoStati;
    }

    public void setStoricoStati(List<StatoMezzo> storicoStati) {
        this.storicoStati = storicoStati;
    }

    // Metodo helper per ottenere lo stato attuale
    public StatoMezzo getStatoAttuale() {
        return storicoStati.stream()
                .filter(s -> s.getDataFine() == null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipo=" + tipoMezzo +
                ", capacita=" + capacita +
                '}';
    }
}