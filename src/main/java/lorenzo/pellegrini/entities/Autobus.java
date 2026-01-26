package lorenzo.pellegrini.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("autobus")
public class Autobus extends Mezzo {
@Column(name ="capacità")
	private int capacita;

	public Autobus(){
	}
	public Autobus(StatoAttuale statoAttuale, int capacita) {
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
		return "Autobus{" +
				"capacita=" + capacita +
				'}'+super.toString();
	}
}
