package lorenzo.pellegrini.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("autobus")
public class Autobus extends Mezzo {

	private int capacita;


}
