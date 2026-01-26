package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("tram")
public class Tram extends Mezzo {

	private  int capacita;

	public Tram(){
	}

	public Tram(StatoAttuale statoAttuale,int capacita) {
		super(statoAttuale);
		this.capacita = capacita;
	}

	public int getCapacita() {
		return capacita;
	}

	public void setCapacita(int capacita) {
		this.capacita = capacita;
	}

	@Override
	public String toString() {
		return "Tram{" +
				"capacita=" + capacita +
				'}'+super.toString();
	}
}
