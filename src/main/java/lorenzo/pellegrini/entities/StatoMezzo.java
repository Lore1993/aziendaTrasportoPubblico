package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class StatoMezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInizio;
    private LocalDate dataFine; // Se è null, il mezzo è attualmente in questo stato

    @Enumerated(EnumType.STRING)
    private TipoStato tipoStato; // MANUTENZIONE, SERVIZIO

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    public StatoMezzo(){}

    public StatoMezzo(LocalDate dataInizio, TipoStato tipoStato, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.tipoStato = tipoStato;
        this.mezzo = mezzo;
        this.dataFine = null; // Di default un nuovo stato non ha ancora una fine
    }

    public Long getId() { return id; }

    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    public TipoStato getTipoStato() { return tipoStato; }
    public void setTipoStato(TipoStato tipoStato) { this.tipoStato = tipoStato; }

    public Mezzo getMezzo() { return mezzo; }
    public void setMezzo(Mezzo mezzo) { this.mezzo = mezzo; }

    // Metodo per chiudere un periodo
    public void concludiPeriodo() {
        this.dataFine = LocalDate.now();
    }

    @Override
    public String toString() {
        return "StatoMezzo{" +
                "id=" + id +
                ", tipo=" + tipoStato +
                ", dal=" + dataInizio +
                ", al=" + (dataFine != null ? dataFine : "IN CORSO") +
                '}';
    }
}