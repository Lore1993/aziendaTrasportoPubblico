package lorenzo.pellegrini.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_puntoVendita")
public abstract class PuntoVendita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "puntoVendita")
    private List<TitoloViaggio> titoliEmessi; // Un punto emette molti titoli

    public  PuntoVendita() {}

    public PuntoVendita(String nome){
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<TitoloViaggio> getTitoliEmessi() {
        return titoliEmessi;
    }

    public void setTitoliEmessi(List<TitoloViaggio> titoliEmessi) {
        this.titoliEmessi = titoliEmessi;
    }
}