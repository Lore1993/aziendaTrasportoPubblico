package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lorenzo.pellegrini.entities.Biglietto;


public class BigliettiDao {
    private final EntityManager em;
    public BigliettiDao(EntityManager em) {
        this.em = em;
    }
    public void salvaBiglietto(Biglietto biglietto) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(biglietto);
        tx.commit();
        System.out.println("il mezzo "+biglietto+" è stato salvato");
    }
}
