package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.StatoAttuale;

import java.time.LocalDate;

@Entity
public class StatoMezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInizio;
    private LocalDate dataFine; // Se è null, il mezzo è attualmente in questo stato

    @Enumerated(EnumType.STRING)
    private StatoAttuale statoAttuale; // MANUTENZIONE, SERVIZIO

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    public StatoMezzo() {
    }

    public StatoMezzo(LocalDate dataInizio, StatoAttuale statoAttuale, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.statoAttuale = statoAttuale;
        this.mezzo = mezzo;
        this.dataFine = null; // Di default un nuovo stato non ha ancora una fine
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public StatoAttuale getTipoStato() {
        return statoAttuale;
    }

    public void setTipoStato(StatoAttuale statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    // Metodo per chiudere un periodo
    public void concludiPeriodo() {
        this.dataFine = LocalDate.now();
    }


    //Ritorna true se questo è lo stato attualmente attivo

    public boolean isStatoAttuale() {
        return dataFine == null;
    }


    // Ritorna true se il mezzo è in servizio

    public boolean isInServizio() {
        return isStatoAttuale() && statoAttuale == StatoAttuale.IN_SERVIZIO;
    }


    // Ritorna true se il mezzo è in manutenzione

    public boolean isInManutenzione() {
        return isStatoAttuale() && statoAttuale == StatoAttuale.IN_MANUTENZIONE;
    }


    @Override
    public String toString() {
        return "StatoMezzo{" +
                "id=" + id +
                ", tipo=" + statoAttuale +
                ", dal=" + dataInizio +
                ", al=" + (dataFine != null ? dataFine : "IN CORSO") +
                '}';
    }
}