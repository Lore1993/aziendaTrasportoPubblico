package lorenzo.pellegrini.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("tram")
public class Tram extends Mezzo {

	public Tram(){
	}

	public Tram(StatoAttuale statoAttuale,int capacita) {
		super(statoAttuale, capacita);
	}

	@Override
	public String toString() {
		return "Tram{} " + super.toString();
	}
}
