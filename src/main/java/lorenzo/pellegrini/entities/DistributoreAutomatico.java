package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISTRIBUTORE")
public class DistributoreAutomatico extends PuntoVendita {

    // Costruttori
    public DistributoreAutomatico() {
        super();
    }

    public DistributoreAutomatico(String nome, boolean attivo) {
        super(nome);
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{id=" + getId() +
                ", nome='" + getNome() +
                "}";
    }
}