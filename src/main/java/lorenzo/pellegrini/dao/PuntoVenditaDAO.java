package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Biglietto;
import lorenzo.pellegrini.entities.PuntoVendita;
import java.time.LocalDate;

public class PuntoVenditaDAO {
    private EntityManager em;

    public PuntoVenditaDAO(EntityManager em) {
        this.em = em;
    }

    // salvo punto vendita
    public void save(PuntoVendita pv) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(pv);
        t.commit();
        System.out.println("Salvato il punto vendita: " + pv.getNome());
    }

    // cerco un punto vendita per ID
    public PuntoVendita findById(Long id) {
        PuntoVendita trovato = em.find(PuntoVendita.class, id);
        return trovato;
    }

    // Metodo per vendere un biglietto
    public void vendiBiglietto(PuntoVendita pv) {
        // Creo il biglietto
        Biglietto b = new Biglietto();
        b.setDataEmissione(LocalDate.now());
        b.setPuntoVendita(pv);

        // Salvo il biglietto
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(b);
        t.commit();

        System.out.println("Biglietto venduto da " + pv.getNome());
    }

    // metodo che filtri le vendite non solo per data, ma anche per lo specifico rivenditore o distributore.
    public long countTitoliPerPuntoVendita(Long puntoVenditaId, LocalDate inizio, LocalDate fine){
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM TitoloViaggio t " +
                "WHERE t.puntoVendita.id = :id " +
                "AND t.dataEmissione BETWEEN :inizio AND :fine", Long.class);
        query.setParameter("pvId", puntoVenditaId);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);
        return query.getSingleResult();
    }
}