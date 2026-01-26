package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "titolo_viaggio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo di acquisto", discriminatorType = DiscriminatorType.STRING)
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "punto_vendita", nullable = false)
    private long puntoVendita;

    // Costruttore vuoto
    public TitoloViaggio() {
    }

    // Costruttore con parametri
    public TitoloViaggio(LocalDate dataEmissione, long puntoVendita) {
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

    public long getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(long puntoVendita) {
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
