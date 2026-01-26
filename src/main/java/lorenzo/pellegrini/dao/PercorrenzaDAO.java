package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import lorenzo.pellegrini.entities.Percorrenza;

public class PercorrenzaDAO {

    private final EntityManager em;

    public PercorrenzaDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Percorrenza percorrenza) {
        em.persist(percorrenza);
    }

    public Percorrenza findById(long id) {
        return em.find(Percorrenza.class, id);
    }

    public void delete(long id) {
        Percorrenza p = findById(id);
        if (p != null) {
            em.remove(p);
        } else {
            System.out.println("ID non presente nel database");
        }
    }
}