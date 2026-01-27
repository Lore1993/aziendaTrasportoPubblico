package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.StatoMezzo;

import java.util.UUID;

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
	public Mezzo getMezzo(long id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Mezzo mezzo = em.find(Mezzo.class, id);
		tx.commit();
	return mezzo;
	}
}
