package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Tessera {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String numeroTessera;

    private LocalDate dataEmissione;
    private LocalDate dataScadenza;

    @OneToOne
    @JoinColumn(name = "utente_id", unique = true)
    private Utente utente;

    public Tessera() {
    }

    public Tessera(String numeroTessera, LocalDate dataEmissione, Utente utente) {
        this.numeroTessera = numeroTessera;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusYears(1);
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(String numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusYears(1);
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public boolean isValida() {
        return LocalDate.now().isBefore(dataScadenza);
    }
}
