package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lorenzo.pellegrini.enums.StatoAttuale;

@Entity
@DiscriminatorValue("autobus")
public class Autobus extends Mezzo {

    public Autobus() {
    }

    public Autobus( int capacita) {
        super( capacita);
    }


    @Override
    public String toString() {
        return "Autobus{}" + super.toString();
    }
}
