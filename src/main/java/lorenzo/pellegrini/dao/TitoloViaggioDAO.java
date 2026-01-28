package lorenzo.pellegrini.dao;

import jakarta.persistence.*;
import lorenzo.pellegrini.entities.Abbonamento;
import lorenzo.pellegrini.entities.Biglietto;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.TitoloViaggio;
import lorenzo.pellegrini.enums.TipoAbbonamento;

import java.time.LocalDate;

public class TitoloViaggioDAO {
    private EntityManager em;

    public TitoloViaggioDAO(EntityManager em) {
        this.em = em;
    }

    public void save(TitoloViaggio titolo) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(titolo);
            t.commit();

            // Controllo del tipo per messaggio personalizzato
            if (titolo instanceof Biglietto) {
                System.out.println("Biglietto emesso con successo! ID: " + titolo.getId());
            } else if (titolo instanceof Abbonamento) {
                Abbonamento a = (Abbonamento) titolo; // Cast per accedere ai metodi dell'abbonamento
                System.out.println("Abbonamento " + a.getTipoAbbonamento() +
                        " emesso per la tessera: " + a.getTesseraId().getNumeroTessera());
            }

        } catch (Exception e) {
            System.err.println("Errore nel salvataggio della percorrenza: " + e.getMessage());
        }
    }

    public void vidimaBiglietto(Long id, Mezzo mezzo) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            TitoloViaggio trovato = em.find(TitoloViaggio.class, id);

            if (trovato == null) {
                System.out.println("Errore: Biglietto non trovato!");
            } else if (trovato instanceof Biglietto) {
                Biglietto b = (Biglietto) trovato; //Cast per accedere ai metodi di Biglietto

                if (!b.isVidimato()) {
                    b.setDataVidimazione(LocalDate.now());
                    b.setIdMezzo(mezzo);
                    System.out.println("Biglietto vidimato con successo sul mezzo: " + mezzo.getId());
                } else {
                    System.out.println("Attenzione: Biglietto già vidimato il " + b.getDataVidimazione());
                }
            } else {
                System.out.println("Errore: L'ID fornito appartiene a un Abbonamento, non è necessario vidimarlo.");
            }

            t.commit();
        } catch (Exception e) {
            System.err.println("Errore durante la vidimazione: " + e.getMessage());
        }
    }


    //acquisire il numero di biglietti vidimati su un particolare mezzo
    public Long countVidimatiSuMezzo(Mezzo mezzo){
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM TitoloViaggio t " +
                    "WHERE TYPE(t)  = Biglietto " + //JPA va a guardare la colonna tipo_acquisto (il discriminator) nel database e filtra solo le righe mappate come @DiscriminatorValue("biglietto").
                    "AND t.dataVidimazione IS NOT NULL " +
                    "AND t.mezzo = :valore", Long.class); //I conteggi in SQL/JPQL restituiscono sempre un tipo Long
            query.setParameter("valore", mezzo);
            return query.getSingleResult();
        } catch (Exception ex) {
            System.err.println("Errore nel conteggio: " + ex.getMessage());
            return 0L;
        }
    }

    //acquisire il numero di biglietti vidimati in totale in un periodo di tempo
    public Long countVidimatiInPeriodo(LocalDate inizio, LocalDate fine) {
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(t) FROM TitoloViaggio t " +
                            "WHERE TYPE(t) = Biglietto " +
                            "AND t.dataVidimazione BETWEEN :inizio AND :fine", Long.class);

            query.setParameter("inizio", inizio);
            query.setParameter("fine", fine);

            return query.getSingleResult();
        } catch (Exception ex) {
            System.err.println("Errore nel conteggio: " + ex.getMessage());
            return 0L;
        }
    }

    public Abbonamento findUltimoAbbonamento(String numeroTessera) {
        try {
            TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a " +
                    "WHERE a.tessera.numeroTessera = :n " + //Filtra gli abbonamenti in base al numero della tessera
                    "ORDER BY a.dataEmissione DESC",Abbonamento.class ); //Ordina i record in ordine decrescente (l'ultimo in alto)
            query.setParameter("n", numeroTessera);
            query.getFirstResult();
            query.setMaxResults(1);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Nessun abbonamento trovato per questa tessera
        }
    }

    //permettere la verifica rapida della validità di un abbonamento in base al numero di tessera
    public boolean isAbbonamentoValid (String numeroTessera){
        Abbonamento abbonamento = findUltimoAbbonamento(numeroTessera);

        if (!abbonamento.getTesseraId().isValida()){ //Se la tessera non è valida, l'utente deve prima rinnovarla
            return false;
        }

        LocalDate oggi = LocalDate.now();
        LocalDate scadenza;

        if (abbonamento.getTipoAbbonamento() == TipoAbbonamento.SETTIMANALE){
            //Se l'abbonamento è settimanale, la data di scadenza sarà la data di emissione + 7 giorni
            scadenza = abbonamento.getDataEmissione().plusDays(7);
        } else {
            //Se l'abbonamento è mensile, la data di scadenza sarà la data di emissione + 1 mese
            scadenza= abbonamento.getDataEmissione().plusMonths(1);
        }
        return oggi.isBefore(scadenza) || oggi.isEqual(scadenza);
        //L'abbonamento sarà valido se la data di oggi è precedente o uguale alla data di scadenza
    }




}