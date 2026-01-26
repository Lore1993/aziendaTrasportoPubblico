package lorenzo.pellegrini.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("biglietto")
public class Biglietto extends TitoloViaggio {

    @Column(name = "vidimato")
    private boolean vidimato;

    @Column(name = "id_mezzo")
    private Integer idMezzo;

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    // Costruttore vuoto
    public Biglietto() {
        super();
    }

    // Costruttore con super + istanze
    public Biglietto(LocalDate dataEmissione, long puntoVendita, boolean vidimato, Integer idMezzo, LocalDate dataVidimazione) {
        super(dataEmissione, puntoVendita);
        this.vidimato = vidimato;
        this.idMezzo = idMezzo;
        this.dataVidimazione = dataVidimazione;
    }

    // Getter e Setter
    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public Integer getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(Integer idMezzo) {
        this.idMezzo = idMezzo;
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
                ", idMezzo=" + idMezzo +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}