package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti") // Tabella specifica per i dati dei biglietti
@PrimaryKeyJoinColumn(name = "id_titolo") // L'ID qui è sia PK che FK verso titoli_viaggio
@DiscriminatorValue("biglietto")
public class Biglietto extends TitoloViaggio {

    @ManyToOne
    @JoinColumn(name = "mezzo_id") // Collega il biglietto al mezzo fisico
    private Mezzo mezzo;

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    // Costruttore vuoto
    public Biglietto() {
        super();
    }

    // Costruttore con super + istanze
    public Biglietto(LocalDate dataEmissione, PuntoVendita puntoVendita,Mezzo mezzo, LocalDate dataVidimazione) {
        super(dataEmissione, puntoVendita);
        this.mezzo=mezzo;
        this.dataVidimazione = dataVidimazione;
    }

    // Getter e Setter
    public boolean isVidimato() {
        return dataVidimazione != null;
    }

    public Mezzo getIdMezzo() {
        return mezzo;
    }

    public void setIdMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + getId() +
                ", dataEmissione=" + getDataEmissione() +
                ", puntoVendita=" + getPuntoVendita() +
                ", Mezzo=" + mezzo +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}