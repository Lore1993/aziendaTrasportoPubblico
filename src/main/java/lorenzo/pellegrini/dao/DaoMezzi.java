package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.enums.StatoAttuale;
import lorenzo.pellegrini.enums.TipoMezzo;

import java.util.List;

public class DaoMezzi {
    private EntityManager em;

    public DaoMezzi(EntityManager em) {
        this.em = em;
    }

    //  BASE

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


}