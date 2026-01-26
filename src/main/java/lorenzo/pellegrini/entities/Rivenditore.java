package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RIVENDITORE")
public class Rivenditore extends PuntoVendita {
    public Rivenditore() {
        super();
    }

    public Rivenditore(String nome) {
        super(nome);
    }

    @Override
    public String toString() {
        return "Rivenditore{id=" + getId() + ", nome='" + getNome() + "'}";
    }
}