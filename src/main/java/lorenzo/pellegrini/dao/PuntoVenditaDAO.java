package lorenzo.pellegrini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lorenzo.pellegrini.entities.Biglietto;
import lorenzo.pellegrini.entities.PuntoVendita;
import lorenzo.pellegrini.entities.Tratta;

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
        System.out.println("Salvato il punto vendita: " + pv.getNome());
    }

    // cerco un punto vendita per ID
    public PuntoVendita findById(Long id) {
        PuntoVendita trovato = em.find(PuntoVendita.class, id);
        return trovato;
    }

    public List<PuntoVendita> findAllPuntoVendita(){
        TypedQuery<PuntoVendita> query = em.createQuery("SELECT p FROM PuntoVendita p", PuntoVendita.class);
        return query.getResultList();
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

    // controllo che il distributore funzioni
    public void verificaStatoDelDistributore(Long idPuntoVendita) {

        // cerco il punto vendita nel DB

        PuntoVendita puntoVendita = em.find(PuntoVendita.class, idPuntoVendita);

        if (puntoVendita != null) {
            // se la variabile inServizio è vera allora funziona
            if (puntoVendita.isInServizio() == true) {
                System.out.println("Il distributore " + puntoVendita.getNome() + " è ATTIVO.");
            } else {
                System.out.println("Il distributore " + puntoVendita.getNome() + " è FUORI SERVIZIO.");
            }
        } else {
            System.out.println("Errore: Il punto vendita con ID " + idPuntoVendita + " non esiste.");
        }
    }

    // metodo per verificare il cambio stato attivo o in manutenzione
    public void impostaStatoDistributore(Long idPuntoVendita, boolean nuovoStato) {
        PuntoVendita puntoVendita = em.find(PuntoVendita.class, idPuntoVendita);

        if (puntoVendita != null) {
            em.getTransaction().begin();
            // imposto lo stato
            puntoVendita.setInServizio(nuovoStato);
            em.getTransaction().commit();
            System.out.println("Lo stato di " + puntoVendita.getNome() + " è stato aggiornato.");
        }
    }

    // controllo lo stato prima di vendere il biglietto
    public void vendiBiglietto(PuntoVendita puntoVendita) {
        // Se il distributore è fuori servizio (false), non facciamo nulla
        if (puntoVendita.isInServizio() == false) {
            System.out.println("Impossibile vendere: il distributore è fuori servizio!");
        } else {
            // se il distributore è in servizio procedo alla vendiat del biglietto
            Biglietto nuovoBiglietto = new Biglietto();
            nuovoBiglietto.setDataEmissione(LocalDate.now());
            nuovoBiglietto.setPuntoVendita(puntoVendita);

            em.getTransaction().begin();
            em.persist(nuovoBiglietto);
            em.getTransaction().commit();
            System.out.println("Biglietto numero: "  + nuovoBiglietto.getId() + " emesso con successo da " + puntoVendita.getNome() );
        }
    }
}