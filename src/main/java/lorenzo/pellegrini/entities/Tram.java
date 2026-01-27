package lorenzo.pellegrini.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lorenzo.pellegrini.enums.StatoAttuale;

@Entity
@DiscriminatorValue("tram")
public class Tram extends Mezzo {

    public Tram() {
    }

    public Tram( int capacita) {
        super( capacita);
    }

    @Override
    public String toString() {
        return "Tram{} " + super.toString();
    }
}
