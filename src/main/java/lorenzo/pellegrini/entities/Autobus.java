package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lorenzo.pellegrini.enums.StatoAttuale;

@Entity
@DiscriminatorValue("autobus")
public class Autobus extends Mezzo {

    public Autobus() {
    }

    public Autobus(StatoAttuale statoAttuale, int capacita) {
        super(statoAttuale, capacita);
    }


    @Override
    public String toString() {
        return "Autobus{}" + super.toString();
    }
}
