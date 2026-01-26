package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lorenzo.pellegrini.entities.Utente;

public class UtenteDAO {
    private EntityManager em;
    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(utente);
        transaction.commit();

        System.out.println("Salvataggio completato!");
    }

    public Utente findById(Long id) {
        return em.find(Utente.class, id);
    }
}