package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity

public class Percorrenza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;


    @ManyToOne
    @JoinColumn(name = "tratta_id", nullable = false)
    private Tratta tratta;

    @Column(name = "tempo_effettivo_minuti")
    private int tempoEffettivoMinuti;

    @Column(name = "data_corsa")
    private LocalDate dataCorsa;

    public Percorrenza(LocalDate dataCorsa, int tempoEffettivoMinuti, Tratta tratta, Mezzo mezzo, long id) {
        this.dataCorsa = dataCorsa;
        this.tempoEffettivoMinuti = tempoEffettivoMinuti;
        this.tratta = tratta;
        this.mezzo = mezzo;
        this.id = id;
    }

    public Percorrenza() {
    }

    public Percorrenza(LocalDate now, int i, Tratta tratta1, Mezzo mezzo1) {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public int getTempoEffettivoMinuti() {
        return tempoEffettivoMinuti;
    }

    public void setTempoEffettivoMinuti(int tempoEffettivoMinuti) {
        this.tempoEffettivoMinuti = tempoEffettivoMinuti;
    }

    public LocalDate getDataCorsa() {
        return dataCorsa;
    }

    public void setDataCorsa(LocalDate dataCorsa) {
        this.dataCorsa = dataCorsa;
    }

    @Override
    public String toString() {
        return "Percorrenza{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", tratta=" + tratta +
                ", tempoEffettivoMinuti=" + tempoEffettivoMinuti +
                ", dataCorsa=" + dataCorsa +
                '}';
    }
}
