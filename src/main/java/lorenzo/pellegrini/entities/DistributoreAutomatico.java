package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISTRIBUTORE")
public class DistributoreAutomatico extends PuntoVendita {

    private boolean attivo; // True = Attivo, False = Fuori Servizio

    // Costruttori
    public DistributoreAutomatico() {
        super();
    }

    public DistributoreAutomatico(String nome, boolean attivo) {
        super(nome);
        this.attivo = attivo;
    }

    // Getter e Setter specifici
    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{id=" + getId() +
                ", nome='" + getNome() +
                "', stato=" + (attivo ? "ATTIVO" : "FUORI SERVIZIO") + "}";
    }
}