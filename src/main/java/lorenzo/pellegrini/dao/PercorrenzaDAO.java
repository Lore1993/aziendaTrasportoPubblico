package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Percorrenza;
import lorenzo.pellegrini.entities.Tratta;

import java.util.List;

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

public List<Percorrenza> findAll() {
        TypedQuery<Percorrenza> query = em.createQuery("SELECT p FROM Percorrenza p", Percorrenza.class);
        return query.getResultList();
    }
    public Double getTempoMedioEffettvo(long id) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(p.tempoEffettivoMinuti) FROM Percorrenza p WHERE p.tratta.id=:id",Double.class);
        query.setParameter("id", id);
        Double avgRes= query.getSingleResult();
        return avgRes != null ? avgRes : 0.0;
    }

    public Long getCountCorseSuTratta(long id) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT (p) FROM Percorrenza p WHERE p.tratta.id=:id", Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult();
    }

    public Long corseMezzoSuTratta(long mezzoId,long trattaId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Percorrenza p WHERE p.tratta.id=:trattaId AND p.mezzo.id=:mezzoId", Long.class
        );
        query.setParameter("mezzoId", mezzoId);
        query.setParameter("trattaId", trattaId);
        return (long) query.getSingleResult();
    }
}