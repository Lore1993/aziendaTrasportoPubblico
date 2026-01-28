package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.StatoMezzo;
import lorenzo.pellegrini.enums.StatoAttuale;

import java.time.LocalDate;
import java.util.List;


public class StatoMezzoDAO {
    private EntityManager em;

    public StatoMezzoDAO(EntityManager em) {
        this.em = em;
    }

    // Salva nuovo stato
    public void save(StatoMezzo statoMezzo) {
        em.getTransaction().begin();
        em.persist(statoMezzo);
        em.getTransaction().commit();
    }

    // Recupero stato attuale di un mezzo
    public StatoMezzo getStatoAttuale(Mezzo mezzo) {
        TypedQuery<StatoMezzo> query = em.createQuery(
                "SELECT s FROM StatoMezzo s WHERE s.mezzo = :mezzo AND s.dataFine IS NULL",
                StatoMezzo.class
        );
        query.setParameter("mezzo", mezzo);

        return query.getResultStream().findFirst().orElse(null);
    }

    // Chiusura stato attuale di un mezzo
    public void chiudiStatoAttuale(Mezzo mezzo) {
        StatoMezzo statoAttuale = getStatoAttuale(mezzo);

        if (statoAttuale != null) {
            em.getTransaction().begin();
            statoAttuale.concludiPeriodo();
            em.merge(statoAttuale);
            em.getTransaction().commit();
        }
    }

    // Restituisce stato mezzi
    public List<StatoMezzo> getMezziPerStato(StatoAttuale stato) {
        TypedQuery<StatoMezzo> query = em.createQuery(
                "SELECT s FROM StatoMezzo s WHERE s.statoAttuale = :stato AND s.dataFine IS NULL",
                StatoMezzo.class
        );
        query.setParameter("stato", stato);

        return query.getResultList();
    }

    // Storico degli stati di un mezzo
    public List<StatoMezzo> getStoricoMezzo(Mezzo mezzo) {
        TypedQuery<StatoMezzo> query = em.createQuery(
                "SELECT s FROM StatoMezzo s WHERE s.mezzo = :mezzo ORDER BY s.dataInizio",
                StatoMezzo.class
        );
        query.setParameter("mezzo", mezzo);

        return query.getResultList();
    }

    public void cambiaStato(Mezzo mezzo, StatoAttuale nuovoStato) {
        // Chiude il periodo attuale
        chiudiStatoAttuale(mezzo);
        // Crea nuovo stato
        StatoMezzo nuovo = new StatoMezzo(LocalDate.now(), nuovoStato, mezzo);
        // Salva nel DB
        save(nuovo);
        // Aggiorna la lista storica interna al mezzo
        mezzo.aggiungiStato(nuovo);
    }

}
