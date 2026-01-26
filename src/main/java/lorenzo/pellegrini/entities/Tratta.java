package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tratte")
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partenza;

    @Column(nullable = false)
    private String capolinea;

    @Column(name = "tempo_medio_stimato")
    private int tempoMedioStimato; // espresso in minuti

    @OneToMany(mappedBy = "tratta")
    private List<Percorrenza> percorrenzeEffettuate;

    public Tratta() {}

    public Tratta(String partenza, String capolinea, int tempoMedioStimato) {
        this.partenza = partenza;
        this.capolinea = capolinea;
        this.tempoMedioStimato = tempoMedioStimato;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public String getPartenza() { return partenza; }
    public void setPartenza(String partenza) { this.partenza = partenza; }
    public String getCapolinea() { return capolinea; }
    public void setCapolinea(String capolinea) { this.capolinea = capolinea; }
    public int getTempoMedioStimato() { return tempoMedioStimato; }
    public void setTempoMedioStimato(int tempoMedioStimato) { this.tempoMedioStimato = tempoMedioStimato; }
}