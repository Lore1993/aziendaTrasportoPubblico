package lorenzo.pellegrini.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Mezzo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Mezzo {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "stato_attuale", nullable = false)
	private StatoAttuale statoAttuale;


	public Mezzo() {
	}

	public UUID getId() {
		return id;
	}

	public StatoAttuale getStatoAttuale() {
		return statoAttuale;
	}

	public void setStatoAttuale(StatoAttuale statoAttuale) {
		this.statoAttuale = statoAttuale;
	}

	@Override
	public String toString() {
		return "Mezzo{" +
				"id=" + id +
				", statoAttuale=" + statoAttuale +
				'}';
	}
}
