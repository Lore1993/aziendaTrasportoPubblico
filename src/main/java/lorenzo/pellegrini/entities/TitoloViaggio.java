package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "titoli_viaggio")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_acquisto", discriminatorType = DiscriminatorType.STRING)
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "punto_vendita_id", nullable = false)
    private PuntoVendita puntoVendita;

    // Costruttore vuoto
    public TitoloViaggio() {
    }

    // Costruttore con parametri
    public TitoloViaggio(LocalDate dataEmissione, PuntoVendita puntoVendita) {
        this.dataEmissione = dataEmissione;
        this.puntoVendita = puntoVendita;
    }

    // Getter e Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public PuntoVendita getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(PuntoVendita puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    @Override
    public String toString() {
        return "TitoloViaggio{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", puntoVendita=" + puntoVendita +
                '}';
    }
}
