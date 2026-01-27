package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Tessera;

import java.time.LocalDate;

public class TesseraDAO {
    private EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }
    // Salva nuova tessera
    public void save(Tessera tessera) {
        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();
    }
    // Cerca una tessera tramite numero tessera
    public Tessera findByNumeroTessera(String numeroTessera) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.numeroTessera = :numero",
                Tessera.class
        );
        query.setParameter("numero", numeroTessera);

        return query.getResultStream().findFirst().orElse(null);
    }

    // Verifica validità
    public boolean isTesseraValida(String numeroTessera) {
        Tessera tessera = findByNumeroTessera(numeroTessera);
        return tessera != null && tessera.isValida();
    }

    // Rinnova la tessera (1 anno da data attuale)
    public void rinnovaTessera(String numeroTessera) {
        Tessera tessera = findByNumeroTessera(numeroTessera);

        if (tessera != null) {
            em.getTransaction().begin();
            tessera.setDataEmissione(LocalDate.now());

            em.merge(tessera);
            em.getTransaction().commit();
        }
    }

}
