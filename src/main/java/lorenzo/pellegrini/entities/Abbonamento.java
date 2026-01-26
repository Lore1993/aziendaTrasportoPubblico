package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.TipoAbbonamento;

import java.time.LocalDate;


@Entity
@DiscriminatorValue("abbonamento")
public class Abbonamento extends TitoloViaggio {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abbonamento")
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "tessera_id")
    private long tesseraId;

    // Costruttore vuoto
    public Abbonamento() {
        super();
    }

    // Costruttore con super + istanze
    public Abbonamento(LocalDate dataEmissione, long puntoVendita, TipoAbbonamento tipoAbbonamento, long tesseraId) {
        super(dataEmissione, puntoVendita);
        this.tipoAbbonamento = tipoAbbonamento;
        this.tesseraId = tesseraId;
    }

    // Getter e Setter
    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public long getTesseraId() {
        return tesseraId;
    }

    public void setTesseraId(long tesseraId) {
        this.tesseraId = tesseraId;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + getId() +
                ", dataEmissione=" + getDataEmissione() +
                ", puntoVendita=" + getPuntoVendita() +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", tesseraId=" + tesseraId +
                '}';
    }
}