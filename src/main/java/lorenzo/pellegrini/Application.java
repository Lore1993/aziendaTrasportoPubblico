package lorenzo.pellegrini;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lorenzo.pellegrini.dao.*;
import lorenzo.pellegrini.entities.*;
import lorenzo.pellegrini.enums.StatoAttuale;
import lorenzo.pellegrini.enums.TipoAbbonamento;
import lorenzo.pellegrini.enums.TipoMezzo;

import java.time.LocalDate;
import java.util.Random;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildweek1db");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        Random rndm = new Random();

        //-----------DAO-------------------

        DaoMezzi dm=new DaoMezzi(em);
        UtenteDAO ud=new UtenteDAO(em);
        StatoMezzoDAO sm=new StatoMezzoDAO(em);
        PuntoVenditaDAO pvd=new PuntoVenditaDAO(em);
        TitoloViaggioDAO tvd=new TitoloViaggioDAO(em);
        PercorrenzaDAO pd=new PercorrenzaDAO(em);

        //------------------- MEZZI ----------------------------
        Mezzo mezzo1 = new Mezzo(200, TipoMezzo.AUTOBUS);
        Mezzo mezzo2 = new Mezzo(300, TipoMezzo.TRAM);
        Mezzo mezzo3 = new Mezzo(400, TipoMezzo.TRAM);
        Mezzo mezzo4 = new Mezzo(50, TipoMezzo.AUTOBUS);
        Mezzo mezzo5 = new Mezzo(60, TipoMezzo.AUTOBUS);
        Mezzo mezzo6 = new Mezzo(150, TipoMezzo.TRAM);

        dm.salvaMezzi(mezzo1);
        dm.salvaMezzi(mezzo2);
        dm.salvaMezzi(mezzo3);
        dm.salvaMezzi(mezzo4);
        dm.salvaMezzi(mezzo5);
        dm.salvaMezzi(mezzo6);

        //--------------------- STATO MEZZI -------------------------

        StatoMezzo statoMezzo = new StatoMezzo(LocalDate.now(), StatoAttuale.IN_SERVIZIO,mezzo1);
        sm.save(statoMezzo);

//------------------------------ UTENTI ----------------------------------

        Utente utente1 = new Utente("Alessio", "Ceccarini", "aleesio@ceccarini.it");
        Utente utente2 = new Utente("Alexandra", "Manolache", "Alexandra@Manolache.it");
        Utente utente3 = new Utente("Vincenzo", "Calvaruso", "vincenzo@calvaruso.it");
        Utente utente4 = new Utente("Lorenzo", "Pellegrini", "lorenzo@pellegrini.it");
        Utente utente5 = new Utente("Raul", "Ibiceanu", "raul@ibiceanu.it");

//----------------------------------- TESSERE -------------------------------------------------

        Tessera tessera1 = new Tessera("TR3737", LocalDate.now(), utente1);
        Tessera tessera2 = new Tessera("TR3738", LocalDate.now(), utente2);
        Tessera tessera3 = new Tessera("TR3739", LocalDate.now(), utente3);
        Tessera tessera4 = new Tessera("TR3741", LocalDate.now(), utente4);
        Tessera tessera5 = new Tessera("TR3742", LocalDate.now(), utente5);


//-----------------------------------SALVATAGGIO UTENTI/TESSERE -------------------------------------------------
        utente1.setTessera(tessera1);
        utente2.setTessera(tessera2);
        utente3.setTessera(tessera3);
        utente4.setTessera(tessera4);
        utente5.setTessera(tessera5);

       ud.save(utente1);
       ud.save(utente2);
       ud.save(utente3);
       ud.save(utente4);
       ud.save(utente5);

//----------------------------------- PUNTI VENDITA -------------------------------------------------

        PuntoVendita rivenditore1 = new Rivenditore("Tabaccaio");
        PuntoVendita rivenditore2 = new DistributoreAutomatico("distributore automatico",false);
        PuntoVendita rivenditore3=new DistributoreAutomatico("distributore automatico",true);
        PuntoVendita rivenditore4 = new Rivenditore("biglietteria");

        pvd.save(rivenditore1);
        pvd.save(rivenditore2);
        pvd.save(rivenditore3);
        pvd.save(rivenditore4);


//----------------------------------- BIGLIETTI/ABBONAMENTI -------------------------------------------------

        Biglietto biglietto1 = new Biglietto(LocalDate.now(), rivenditore1, mezzo1, null);
        Biglietto biglietto2=new Biglietto(LocalDate.now(), rivenditore2, mezzo2,null);
        Biglietto biglietto3=new Biglietto(LocalDate.now(), rivenditore3, mezzo3, LocalDate.now().minusDays(1));

        Abbonamento abbonamento1 = new Abbonamento(
                LocalDate.of(2026, 1, 1), rivenditore1, TipoAbbonamento.MENSILE, tessera1);


        tvd.save(abbonamento1);
        tvd.save(biglietto1);
        tvd.save(biglietto2);
        tvd.save(biglietto3);

//----------------------------------- METODI TITOLI DI VIAGGIO -------------------------------------------------

        tvd.vidimaBiglietto(2L,mezzo1);
        tvd.vidimaBiglietto(3L,mezzo1);

        long count1=tvd.countVidimatiSuMezzo(mezzo1);
        System.out.println(count1);

        long count2=tvd.countVidimatiSuMezzo(mezzo2);
        System.out.println(count2);

        long countvid = tvd.countVidimatiInPeriodo(LocalDate.now(), LocalDate.now());
        System.out.println("conteggio vidimati in periodo--------->"+countvid);

        //---------------------- TRATTE/PEROCRRENZE ---------------------------------------------


        Tratta tratta1 = new Tratta("Pomezia", "Roma-centro", 35);
        Tratta tratta2 = new Tratta("stz.Santa M. Novella", "Ponte vecchio", 20);
        Tratta tratta3 = new Tratta("Piazza del Popolo", "Fontana di Trevi", 7);



        Percorrenza percorrenza1 = new Percorrenza(LocalDate.now(),42,tratta1,mezzo1);
        Percorrenza percorrenza2=new Percorrenza(LocalDate.now(),18,tratta2,mezzo2);
        Percorrenza percorrenza3=new Percorrenza(LocalDate.now(),10,tratta3,mezzo3);

        pd.save(percorrenza1);
        pd.save(percorrenza2);
        pd.save(percorrenza3);











        try {
            System.out.println("Collegameto database riuscito");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }}
    }