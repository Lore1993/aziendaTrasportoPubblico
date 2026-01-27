package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.DistributoreAutomatico;
import lorenzo.pellegrini.entities.PuntoVendita;
import lorenzo.pellegrini.entities.TitoloViaggio;

import java.time.LocalDate;
import java.util.List;

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
        System.out.println("Ho salvato il punto vendita: " + pv.getNome());
    }

    // cerco un punto vendita per ID
    public PuntoVendita findById(Long id) {
        PuntoVendita trovato = em.find(PuntoVendita.class, id);
        return trovato;
    }

    // imposto un nuovo stato di un distributore (se attivo o meno)
    public void impostaStatoDistributore(Long id, boolean nuovoStato) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            PuntoVendita pv = em.find(PuntoVendita.class, id);

            if (pv instanceof DistributoreAutomatico) {
                DistributoreAutomatico dist = (DistributoreAutomatico) pv;
                dist.setAttivo(nuovoStato);

                System.out.println("Il distributore " + dist.getNome() + " è ora " + (nuovoStato ? "ATTIVO" : "FUORI SERVIZIO"));
            }
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // cerco solo i distributori automatici attivi
    public List<DistributoreAutomatico> findDistributoriAttivi() {
        TypedQuery<DistributoreAutomatico> query = em.createQuery(
                "SELECT d FROM DistributoreAutomatico d WHERE d.attivo = true",
                DistributoreAutomatico.class
        );
        return query.getResultList();
    }

    // conto quanti titoli ha emesso un determinato punto Vendita in un lasso di tempo
    public Long countTitoliEmessi(Long puntoVenditaId, LocalDate inizio, LocalDate fine) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM TitoloViaggio t " +
                "WHERE t.puntoVendita.id = :id " +
                "AND t.dataEmissione BETWEEN :inizio AND :fine ", Long.class);

        query.setParameter("id", puntoVenditaId);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);

        return query.getSingleResult();
    }
}