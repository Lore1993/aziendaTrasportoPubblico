package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("biglietto")
public class Biglietto extends TitoloViaggio {

    @Column(name = "vidimato")
    private boolean vidimato;

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
    public Biglietto(LocalDate dataEmissione, PuntoVendita puntoVendita, boolean vidimato,Mezzo mezzo, LocalDate dataVidimazione) {
        super(dataEmissione, puntoVendita);
        this.vidimato = vidimato;
        this.mezzo=mezzo;
        this.dataVidimazione = dataVidimazione;
    }

    // Getter e Setter
    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
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
                ", vidimato=" + vidimato +
                ", Mezzo=" + mezzo +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}