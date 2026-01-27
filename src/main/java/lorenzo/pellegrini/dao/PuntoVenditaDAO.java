package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
        System.out.println("Ho salvato il punto vendita: " + pv.getNome());
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
}