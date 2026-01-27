package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lorenzo.pellegrini.entities.Percorrenza;
import lorenzo.pellegrini.entities.Tratta;

public class PercorrenzaDAO {

    private final EntityManager em;

    public PercorrenzaDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Percorrenza percorrenza) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(percorrenza);
            t.commit();
            System.out.println("Percorrenza salvata correttamente: " + percorrenza.getDataCorsa() + " - " + percorrenza.getTempoEffettivoMinuti());
        } catch (Exception e) {
            System.err.println("Errore nel salvataggio della percorrenza: " + e.getMessage());
        }
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