package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lorenzo.pellegrini.entities.PuntoVendita;

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
}