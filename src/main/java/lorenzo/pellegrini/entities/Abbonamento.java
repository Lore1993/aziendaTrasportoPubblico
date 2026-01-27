package lorenzo.pellegrini.entities;

import jakarta.persistence.*;
import lorenzo.pellegrini.enums.TipoAbbonamento;

import java.time.LocalDate;


@Entity
@Table(name = "abbonamenti") // Tabella specifica per gli abbonamenti
@PrimaryKeyJoinColumn(name = "id_titolo")
@DiscriminatorValue("abbonamento")
public class Abbonamento extends TitoloViaggio {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abbonamento")
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "tessera_id") // Relazione Many-To-One con la tessera
    private Tessera tessera;

    // Costruttore vuoto
    public Abbonamento() {
        super();
    }

    // Costruttore con super + istanze
    public Abbonamento(LocalDate dataEmissione, PuntoVendita puntoVendita, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        super(dataEmissione, puntoVendita);
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
    }

    // Getter e Setter
    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Tessera getTesseraId() {
        return tessera;
    }

    public void setTesseraId(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + getId() +
                ", dataEmissione=" + getDataEmissione() +
                ", puntoVendita=" + getPuntoVendita() +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", tessera=" + tessera +
                '}';
    }
}