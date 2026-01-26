package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lorenzo.pellegrini.entities.Mezzo;

public class DaoMezzi {
	private EntityManager em;

	public DaoMezzi(EntityManager em) {
		this.em = em;
	}

	public void salvaMezzi(Mezzo mezzo) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(mezzo);
		tx.commit();
		System.out.println("il mezzo "+mezzo+" è stato salvato");
	}
}
