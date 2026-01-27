package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
<<<<<<< Updated upstream
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.StatoMezzo;

import java.util.UUID;
=======
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.StatoMezzo;
import lorenzo.pellegrini.enums.StatoAttuale;
import lorenzo.pellegrini.enums.TipoMezzo;

import java.time.LocalDate;
import java.util.List;
>>>>>>> Stashed changes

public class DaoMezzi {
    private EntityManager em;

    public DaoMezzi(EntityManager em) {
        this.em = em;
    }

<<<<<<< Updated upstream
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
=======
    //  CRUD BASE

    public void salvaMezzo(Mezzo mezzo) {
        em.persist(mezzo);
        System.out.println("Mezzo salvato: " + mezzo);
    }

    public Mezzo trovaMezzoPerId(long id) {
        return em.find(Mezzo.class, id);
    }

    public List<Mezzo> trovaTuttiIMezzi() {
        return em.createQuery("SELECT m FROM Mezzo m", Mezzo.class)
                .getResultList();
    }

    public void aggiornaMezzo(Mezzo mezzo) {
        em.merge(mezzo);
        System.out.println("Mezzo aggiornato: " + mezzo);
    }

    public void eliminaMezzo(long id) {
        Mezzo mezzo = trovaMezzoPerId(id);
        if (mezzo != null) {
            em.remove(mezzo);
            System.out.println("Mezzo eliminato con ID: " + id);
        } else {
            System.out.println("Mezzo non trovato con ID: " + id);
        }
    }

    // QUERY


    // Trova tutti i mezzi di un determinato tipo (AUTOBUS o TRAM)

    public List<Mezzo> trovaMezziPerTipo(TipoMezzo tipo) {
        return em.createQuery(
                        "SELECT m FROM Mezzo m WHERE m.tipoMezzo = :tipo",
                        Mezzo.class)
                .setParameter("tipo", tipo)
                .getResultList();
    }


    // Trova tutti i mezzi attualmente in un determinato stato
    // (usa la tabella StatoMezzo per verificare lo stato attuale)

    public List<Mezzo> trovaMezziPerStatoAttuale(StatoAttuale stato) {
        return em.createQuery(
                        "SELECT DISTINCT m FROM Mezzo m " +
                                "JOIN m.storicoStati s " +
                                "WHERE s.statoAttuale = :stato AND s.dataFine IS NULL",
                        Mezzo.class)
                .setParameter("stato", stato)
                .getResultList();
    }


    //Trova tutti i mezzi con capacità maggiore o uguale a un valore

    public List<Mezzo> trovaMezziPerCapacitaMinima(int capacitaMinima) {
        return em.createQuery(
                        "SELECT m FROM Mezzo m WHERE m.capacita >= :capacita",
                        Mezzo.class)
                .setParameter("capacita", capacitaMinima)
                .getResultList();
    }

    // METODI Queries


    //Crea un mezzo e imposta subito il suo primo stato


    public Mezzo creaMezzoConStato(int capacita, TipoMezzo tipo, StatoAttuale statoIniziale) {
        // Crea il mezzo
        Mezzo mezzo = new Mezzo(capacita, tipo);
        em.persist(mezzo);

        // Crea lo stato iniziale
        StatoMezzo stato = new StatoMezzo(LocalDate.now(), statoIniziale, mezzo);
        em.persist(stato);

        System.out.println("Mezzo creato con stato iniziale: " + mezzo + " - Stato: " + statoIniziale);
        return mezzo;
    }


    // Conta tutti i mezzi presenti nel sistema

    public long contaTuttiIMezzi() {
        return em.createQuery("SELECT COUNT(m) FROM Mezzo m", Long.class)
                .getSingleResult();
    }


    // Conta i mezzi di un determinato tipo

    public long contaMezziPerTipo(TipoMezzo tipo) {
        return em.createQuery(
                        "SELECT COUNT(m) FROM Mezzo m WHERE m.tipoMezzo = :tipo",
                        Long.class)
                .setParameter("tipo", tipo)
                .getSingleResult();
    }


    // Verifica se un mezzo esiste

    public boolean mezzoEsiste(long id) {
        return trovaMezzoPerId(id) != null;
    }
}
>>>>>>> Stashed changes
