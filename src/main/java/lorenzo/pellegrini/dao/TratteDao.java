package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Tratta;

import java.util.List;

public class TratteDao {
    private final EntityManager em;
    public TratteDao(EntityManager em) {
        this.em = em;
    }

    // Salva una nuova tratta (es. Roma-Milano)
    public void save(Tratta tratta) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(tratta);
            t.commit();
            System.out.println("Tratta salvata correttamente: " + tratta.getPartenza() + " - " + tratta.getCapolinea());
        } catch (Exception e) {
            System.err.println("Errore nel salvataggio della tratta: " + e.getMessage());
        }
    }
    // Cerca una tratta per ID
    public Tratta findById(long id){
       return em.find(Tratta.class, id);
    }

    // Recupera tutte le tratte disponibili
    public List<Tratta> findAllTratte(){
        TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t", Tratta.class);
        return query.getResultList();
    }

    // Elimina una tratta
    public void deleteById(long id){
        EntityTransaction t = em.getTransaction();
        Tratta trovata = findById(id);
        if (trovata != null){
            t.begin();
            em.remove(trovata);
            t.commit();
            System.out.println("Tratta eliminata.");
        } else {
            System.out.println("Tratta non trovata.");
        }
    }

}
