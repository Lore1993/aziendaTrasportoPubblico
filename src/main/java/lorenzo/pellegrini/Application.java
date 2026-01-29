package lorenzo.pellegrini;

import java.util.Scanner;
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
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        Random rndm = new Random();

        //-----------DAO-------------------

        DaoMezzi dm = new DaoMezzi(em);
        UtenteDAO ud = new UtenteDAO(em);
        StatoMezzoDAO sm = new StatoMezzoDAO(em);
        PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
        TitoloViaggioDAO tvd = new TitoloViaggioDAO(em);
        PercorrenzaDAO pd = new PercorrenzaDAO(em);
        TratteDao td = new TratteDao(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        StatoMezzoDAO statoMezzoDAO = new StatoMezzoDAO(em);


        //------------------- MEZZI ----------------------------
        Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, 200, StatoAttuale.IN_SERVIZIO);
        Mezzo mezzo2 = new Mezzo(TipoMezzo.TRAM, 300, StatoAttuale.IN_MANUTENZIONE);
        Mezzo mezzo3 = new Mezzo(TipoMezzo.AUTOBUS, 400, StatoAttuale.IN_SERVIZIO);
        Mezzo mezzo4 = new Mezzo(TipoMezzo.TRAM, 50, StatoAttuale.IN_SERVIZIO);
        Mezzo mezzo5 = new Mezzo(TipoMezzo.TRAM, 60, StatoAttuale.IN_MANUTENZIONE);
        Mezzo mezzo6 = new Mezzo(TipoMezzo.AUTOBUS, 150, StatoAttuale.IN_SERVIZIO);


        System.out.println("------------------- MEZZI ----------------------------");
        System.out.println();
        dm.salvaMezzo(mezzo1);
        dm.salvaMezzo(mezzo2);
        dm.salvaMezzo(mezzo3);
        dm.salvaMezzo(mezzo4);
        dm.salvaMezzo(mezzo5);
        dm.salvaMezzo(mezzo6);
        System.out.println();

        //--------------------- STATO MEZZI -------------------------

        StatoMezzo statoMezzo = new StatoMezzo(LocalDate.now(), StatoAttuale.IN_SERVIZIO, mezzo1);
        sm.save(statoMezzo);

        // -------------- MODIFICHE STATO MEZZI ----------------


        // Cambia stato in manutenzione
        sm.cambiaStato(mezzo1, StatoAttuale.IN_MANUTENZIONE);

        // Stampa stato attuale e storico
        System.out.println("Stato attuale mezzo: " + sm.getStatoAttuale(mezzo1));
        System.out.println("Storico completo mezzo: " + sm.getStoricoMezzo(mezzo1));


//------------------------------ UTENTI ----------------------------------
//
//        Utente utente1 = new Utente("Alessio", "Ceccarini", "aleesio@ceccarini.it");
//        Utente utente2 = new Utente("Alexandra", "Manolache", "Alexandra@Manolache.it");
//        Utente utente3 = new Utente("Vincenzo", "Calvaruso", "vincenzo@calvaruso.it");
//        Utente utente4 = new Utente("Lorenzo", "Pellegrini", "lorenzo@pellegrini.it");
//        Utente utente5 = new Utente("Raul", "Ibiceanu", "raul@ibiceanu.it");

//----------------------------------- TESSERE -------------------------------------------------

//        Tessera tessera1 = new Tessera("TR3737", LocalDate.now(), utente1);
//        Tessera tessera2 = new Tessera("TR3738", LocalDate.now(), utente2);
//        Tessera tessera3 = new Tessera("TR3739", LocalDate.now(), utente3);
//        Tessera tessera4 = new Tessera("TR3741", LocalDate.now(), utente4);
//        Tessera tessera5 = new Tessera("TR3742", LocalDate.now(), utente5);


//-----------------------------------SALVATAGGIO UTENTI/TESSERE -------------------------------------------------
//        utente1.setTessera(tessera1);
//        utente2.setTessera(tessera2);
//        utente3.setTessera(tessera3);
//        utente4.setTessera(tessera4);
//        utente5.setTessera(tessera5);
//
//        System.out.println("------------------- UTENTI ----------------------------");
//        System.out.println();
//        ud.save(utente1);
//        ud.save(utente2);
//        ud.save(utente3);
//        ud.save(utente4);
//        ud.save(utente5);
//        System.out.println();

//----------------------------------- PUNTI VENDITA -------------------------------------------------

        PuntoVendita rivenditore1 = new Rivenditore("Tabaccaio");
        PuntoVendita rivenditore2 = new DistributoreAutomatico("distributore automatico", false);
        PuntoVendita rivenditore3 = new DistributoreAutomatico("distributore automatico", true);
        PuntoVendita rivenditore4 = new Rivenditore("biglietteria");

        System.out.println("------------------- PUNTI VENDITA ----------------------------");
        System.out.println();
        pvd.save(rivenditore1);
        pvd.save(rivenditore2);
        pvd.save(rivenditore3);
        pvd.save(rivenditore4);
        System.out.println();


//----------------------------------- BIGLIETTI/ABBONAMENTI -------------------------------------------------

//        Biglietto biglietto1 = new Biglietto(LocalDate.now(), rivenditore1, mezzo1, null);
//        Biglietto biglietto2 = new Biglietto(LocalDate.now(), rivenditore2, mezzo2, null);
//        Biglietto biglietto3 = new Biglietto(LocalDate.now(), rivenditore3, mezzo3, LocalDate.now().minusDays(1));
//
//        Abbonamento abbonamento1 = new Abbonamento(
//                LocalDate.of(2026, 1, 1), rivenditore1, TipoAbbonamento.MENSILE, tessera1);
//
//
//        System.out.println("------------------- BIGLIETTI/ABBONAMENTI ----------------------------");
//        System.out.println();
//        tvd.save(abbonamento1);
//        tvd.save(biglietto1);
//        tvd.save(biglietto2);
//        tvd.save(biglietto3);
//        System.out.println();

        //---------------------- TRATTE/PERCORRENZE ---------------------------------------------


        Tratta tratta1 = new Tratta("Pomezia", "Roma-centro", 35);
        Tratta tratta2 = new Tratta("stz.Santa M. Novella", "Ponte vecchio", 20);
        Tratta tratta3 = new Tratta("Piazza del Popolo", "Fontana di Trevi", 7);

        System.out.println("------------------- TRATTE ----------------------------");
        System.out.println();
        td.save(tratta1);
        td.save(tratta2);
        td.save(tratta3);
        System.out.println();


        Percorrenza percorrenza1 = new Percorrenza(LocalDate.now(), 42, tratta1, mezzo1);
        Percorrenza percorrenza2 = new Percorrenza(LocalDate.now(), 18, tratta2, mezzo2);
        Percorrenza percorrenza3 = new Percorrenza(LocalDate.now(), 10, tratta3, mezzo3);
        Percorrenza percorrenza4 = new Percorrenza((LocalDate.of(2026, 1, 10)), 19, tratta1, mezzo1);
        Percorrenza percorrenza5 = new Percorrenza(LocalDate.of(2026, 1, 11), 20, tratta1, mezzo1);

        System.out.println("------------------- PERCORRENZE ----------------------------");
        System.out.println();
        pd.save(percorrenza1);
        pd.save(percorrenza2);
        pd.save(percorrenza3);
        pd.save(percorrenza4);
        pd.save(percorrenza5);
        System.out.println();


//        System.out.println("------------------- METODI ----------------------------");
//        System.out.println();
//
//        tvd.vidimaBiglietto(2L, mezzo1);
//
//        long count1 = tvd.countVidimatiSuMezzo(mezzo1);
//        System.out.println("Biglietti vidimati sul mezzo: " + mezzo1.getId() + "-" + mezzo1.getTipoMezzo() + " --> " + +count1);
//
//        long count2 = tvd.countVidimatiSuMezzo(mezzo2);
//        System.out.println("Biglietti vidimati sul mezzo: " + mezzo2.getId() + "-" + mezzo2.getTipoMezzo() + " --> " + +count2);
//
//        long countvid = tvd.countVidimatiInPeriodo(LocalDate.now(), LocalDate.now());
//        System.out.println("Conteggio biglietti vidimati per un certo periodo --> " + countvid);
//        System.out.println();
//
//
//        double avg1 = pd.getTempoMedioEffettvo(1L);
//        System.out.println("Tempo medio effettvo: " + avg1);
//
//        long contaPercorrenze1 = pd.getCountCorseSuTratta(1L);
//        System.out.println("Corse su tratta: " + contaPercorrenze1);
//
//        long corsaMezzoTratta1=pd.corseMezzoSuTratta(1L,1L);
//        System.out.println("Corse mezzo su tratta : "+corsaMezzoTratta1);
//
//        pvd.findAllPuntoVendita().forEach(p -> {
//            System.out.println(p.getId() + " - " + p.getNome());
//        });


        Scanner scanner = new Scanner(System.in);
        int scelta;
        do {
            System.out.println("=== AZIENDA TRASPORTO PUBBLICO ===");
            System.out.println("1. Utente");
            System.out.println("2. Amministratore");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            scelta = scanner.nextInt();
            switch (scelta) {
                case 1 -> MenuUtente(dm, pvd, ud, tesseraDAO, tvd);
                case 2 -> MenuAmministratore(tvd, pd, td, dm, statoMezzoDAO, pvd);
            }
        } while (scelta != 0);


        try {
            System.out.println("Collegameto database riuscito");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void MenuUtente(DaoMezzi mezziDao, PuntoVenditaDAO puntoVenditaDAO,
                                   UtenteDAO utenteDAO, TesseraDAO tesseraDAO, TitoloViaggioDAO titoloViaggioDAO) {
        Scanner sc = new Scanner(System.in);
//        EntityManager em = emf.createEntityManager();
//        DaoMezzi mezziDao = new DaoMezzi(em);
//        PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);
//        UtenteDAO utenteDAO = new UtenteDAO(em);
//        TesseraDAO tesseraDAO = new TesseraDAO(em);
//        TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
        int scelta;
        boolean tornaIndietro = false;
        while (!tornaIndietro) {

            System.out.println("------------------- Menù Utente ----------------------------");
            System.out.println(" 1- Nuovo Utente \n 2- Nuovo Biglietto \n 3- Abbonamento \n 4- Vidima biglietto \n 0- Esci");
            scelta = Integer.parseInt(sc.nextLine());
            switch (scelta) {
                case 1 -> {
                    System.out.println(" Inserisci il nome: ");
                    String name = sc.nextLine();
                    System.out.println(" Inserisci il cognome: ");
                    String surname = sc.nextLine();
                    System.out.println(" Inserisci il email: ");
                    String email = sc.nextLine();
                    Utente utente = new Utente(name, surname, email);
                    Tessera tessera = new Tessera(("TRZ" + (int) (Math.random() * 1000)), LocalDate.now(), utente);
                    utente.setTessera(tessera);
                    utenteDAO.save(utente);
                    System.out.println("Utente " + utente.getNome() + " - " + utente.getCognome() + " salvato con successo! \n Numero Tessera: " + tessera.getNumeroTessera());
                }
                case 2 -> {
                    System.out.println("Seleziona Punto Vendita:");
                    puntoVenditaDAO.findAllPuntoVendita().forEach(p -> {
                        System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio());
                    });
                    Long decisione = Long.valueOf(sc.nextLine());
                    PuntoVendita pv = puntoVenditaDAO.findById(decisione);

                    // CORREZIONE: Controllo se il punto vendita è attivo
                    if (pv != null && pv.isInServizio()) {
                        puntoVenditaDAO.vendiBiglietto(pv);
                    } else {
                        System.out.println("ERRORE: Punto vendita non disponibile o fuori servizio!");
                    }
                }
                case 3 -> {
                    System.out.println("Inserisci il numero della tessera: ");
                    String tessera = sc.nextLine();
                    // CORREZIONE: Controllo validità tessera
                    if (!tesseraDAO.isTesseraValida(tessera)) {
                        System.out.println("ERRORE: Tessera scaduta o inesistente. Rinnovala prima di procedere.");
                        break;
                    }
                    System.out.println("Seleziona Punto Vendita:");
                    puntoVenditaDAO.findAllPuntoVendita().forEach(p -> {
                        System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio());
                    });
                    long decisione = Long.parseLong(sc.nextLine());
                    System.out.println("Seleziona il tipo di abbonamento: 1- Settimanale  ----  2- Mensile");
                    int decisione2 = Integer.parseInt(sc.nextLine());
                    Abbonamento abbonamento1 = new Abbonamento();
                    abbonamento1.setDataEmissione(LocalDate.now());
                    abbonamento1.setPuntoVendita(puntoVenditaDAO.findById(decisione));
                    if (decisione2 == 1) {
                        abbonamento1.setTipoAbbonamento(TipoAbbonamento.SETTIMANALE);
                    } else abbonamento1.setTipoAbbonamento(TipoAbbonamento.MENSILE);
                    abbonamento1.setTesseraId(tesseraDAO.findByNumeroTessera(tessera));
                    titoloViaggioDAO.save(abbonamento1);
                    System.out.println("Abbonamento creato con successo con id: " + abbonamento1.getId());
                }
                case 4 -> {
                    System.out.print("ID Biglietto: ");
                    long idbiglietto = Long.parseLong(sc.nextLine());
                    System.out.println("Seleziona Mezzo:");
                    mezziDao.trovaMezziPerStatoAttuale(StatoAttuale.IN_SERVIZIO).forEach(m -> {
                        System.out.println(m.getId() + " - " + m.getTipoMezzo());
                    });
                    int decisione3 = Integer.parseInt(sc.nextLine());
                    titoloViaggioDAO.vidimaBiglietto(idbiglietto, mezziDao.trovaMezzoPerId(decisione3));
                }
                case 0 -> tornaIndietro = true;
                default -> System.out.println("errore");
            }
        }
    }

    private static void MenuAmministratore(TitoloViaggioDAO tvd, PercorrenzaDAO pd, TratteDao td, DaoMezzi md, StatoMezzoDAO stmd, PuntoVenditaDAO pvd) {
        Scanner sc = new Scanner(System.in);
        int scelta;
        boolean tornaIndietro = false;
        while (!tornaIndietro) {

            System.out.println("------------------- Pannello Admin ----------------------------");
            System.out.println(" 1- Crea nuovo Punto Vendita  \n 2- Crea nuovo Mezzo " +
                    "\n 3- Crea nuova Tratta \n 4- Crea nuova Percorrenza " +
                    "\n 5- Media tempo percorrenza tratte \n 6- Biglietti vidimati in un periodo di tempo " +
                    " \n 7- Biglietti vidimati su un mezzo   \n 8- Conteggio corse totali per tratta " +
                    " \n 9- Vendite totali per punto vendita \n 10- Storico mezzo  \n 0- Torna al menù principale");
            scelta = Integer.parseInt(sc.nextLine());
            switch (scelta) {
                case 1 -> {
                    System.out.println("Il nuovo punto vendita è un :\n 1- Rivenditore \n 2- Distributore Automatico");
                    long punto = Long.parseLong(sc.nextLine());
                    if (punto == 2){
                        System.out.println("Inserisci il nome del distributore");
                        String nomeDist = sc.nextLine();
                        System.out.println("Il distributore automatico è in funzione? \n 1- Si \n 2- No");
                        Long funziona = Long.valueOf(sc.nextLine());
                        boolean attivo = false;
                        if(funziona == 1) attivo = true;
                        PuntoVendita nuovoPunto = new DistributoreAutomatico(nomeDist,attivo);
                        nuovoPunto.setInServizio(attivo);
                        pvd.save(nuovoPunto);
                    }else {
                        System.out.println("Inserisci il nome del rivenditore");
                        String nomeDist = sc.nextLine();
                        PuntoVendita nuovoRiv = new Rivenditore(nomeDist);
                        pvd.save(nuovoRiv);
                    }
                }
                case 2 -> {
                    System.out.println("Di che tipo è il mezzo? \n 1- Autobus \n 2- Tram");
                    Long tipo = Long.valueOf(sc.nextLine());
                    System.out.println("Il mezzo è in funzione?  \n 1- Si \n 2- No");
                    Long funziona = Long.valueOf(sc.nextLine());
                    System.out.println("Inserisci la capienza del mezzo: ");
                    int capienza = Integer.parseInt(sc.nextLine());
                    if (tipo == 1){
                        if (funziona == 1)  {
                            Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, capienza, StatoAttuale.IN_SERVIZIO);
                            md.salvaMezzo(mezzo1);
                            stmd.save(new StatoMezzo(LocalDate.now(), StatoAttuale.IN_SERVIZIO, mezzo1));
                        }else {
                            Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, capienza, StatoAttuale.IN_MANUTENZIONE);
                            md.salvaMezzo(mezzo1);
                            stmd.save(new StatoMezzo(LocalDate.now(), StatoAttuale.IN_MANUTENZIONE, mezzo1));
                        }
                    } else {
                        if (funziona == 1)  {
                            Mezzo mezzo1 = new Mezzo(TipoMezzo.TRAM, capienza, StatoAttuale.IN_SERVIZIO);
                            md.salvaMezzo(mezzo1);
                            stmd.save(new StatoMezzo(LocalDate.now(), StatoAttuale.IN_SERVIZIO, mezzo1));
                        }else {
                            Mezzo mezzo1 = new Mezzo(TipoMezzo.TRAM, capienza, StatoAttuale.IN_MANUTENZIONE);
                            md.salvaMezzo(mezzo1);
                            stmd.save(new StatoMezzo(LocalDate.now(), StatoAttuale.IN_MANUTENZIONE, mezzo1));
                        }
                        System.out.println("Mezzo e relativo storico salvati correttamente.");
                    }
                }
                case 3 -> {
                    System.out.println("Inserisci il luogo di partenza: ");
                    String partenza = sc.nextLine();
                    System.out.println("Inserisci il luogo di destinazione: ");
                    String destinazione = sc.nextLine();
                    System.out.println("Inserisci tempo medio stimato in minuti (interi): ");
                    int minuti = Integer.parseInt(sc.nextLine());
                    Tratta tratta = new Tratta(partenza,destinazione,minuti);
                    td.save(tratta);
                }
                case 4 -> {
                    System.out.println("Inserisci la data della corsa (YYYY-MM-DD): ");
                    LocalDate data = LocalDate.parse(sc.nextLine());
                    System.out.println("Inserisci tempo effettivo in minuti (interi): ");
                    int minuti = Integer.parseInt(sc.nextLine());
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    System.out.println("Inserisci l'id del mezzo: ");
                    Long idMezzo = Long.valueOf(sc.nextLine());
                    td.findAllTratte().forEach(t -> System.out.println(t.getId() + " - " + t.getPartenza() + " - " + t.getCapolinea()));
                    System.out.println("Inserisci l'id della tratta: ");
                    long idTratta = Long.parseLong(sc.nextLine());

                    Percorrenza percorrenza1 = new Percorrenza(data,minuti,td.findById(idTratta),md.trovaMezzoPerId(idMezzo));
                    pd.save(percorrenza1);
                }
                case 5 -> {
                    td.findAllTratte().forEach(t -> System.out.println(t.getId() + " - " + t.getPartenza() + " - " + t.getCapolinea()));
                    System.out.println("Inserisci l'id della tratta: ");
                    long idTratta = Long.parseLong(sc.nextLine());
                    double avg1 = pd.getTempoMedioEffettvo(idTratta);
                    System.out.println("Tempo medio reale registrato: " + avg1 + " min");
                }

                case 6 -> {
                    System.out.println("Inserisci data d'inizio (YYYY-MM-DD)");
                    LocalDate in = LocalDate.parse(sc.nextLine());
                    System.out.println("Inserisci data di fine (YYYY-MM-DD)");
                    LocalDate out = LocalDate.parse(sc.nextLine());
                    long countvid = tvd.countVidimatiInPeriodo(in, out);
                    System.out.println("Conteggio biglietti vidimati per un certo periodo --> " + countvid);
                }
                case 7 -> {
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    Long idMezzo = Long.valueOf(sc.nextLine());
                    long count1 = tvd.countVidimatiSuMezzo( md.trovaMezzoPerId(idMezzo));
                   System.out.println("Biglietti vidimati sul mezzo: " + idMezzo + " --> " + +count1);
                }
                case 8 -> {
                    td.findAllTratte().forEach(t -> System.out.println(t.getId() + " - " + t.getPartenza() + " - " + t.getCapolinea()));
                    System.out.println("Inserisci l'id della tratta: ");
                    long idTratta = Long.parseLong(sc.nextLine());
                    long contaPercorrenze1 = pd.getCountCorseSuTratta(idTratta);
                    System.out.println("Corse su tratta con id: " + idTratta + " --> " + contaPercorrenze1);
                }
                case 9 ->{
                    System.out.println("Seleziona Punto Vendita:");
                    pvd.findAllPuntoVendita().forEach(p -> {
                        System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio());
                    });
                    long decisione = Long.parseLong(sc.nextLine());
                    System.out.println("Totale vendite per punto vendita con id: " + decisione + " --> " +  pvd.countTitoliPerPuntoVendita(decisione) );
                }
                case 10 -> {
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    Long idMezzo = Long.valueOf(sc.nextLine());
                    stmd.getStoricoMezzo( md.trovaMezzoPerId(idMezzo)).forEach(m -> System.out.println(m.getId() + " - " + m.getMezzo().getTipoMezzo() +
                            " --> " + m.getTipoStato() + " From " + m.getDataInizio() + " To " + m.getDataFine() ));
                }
                case 0 -> tornaIndietro = true;
                default -> System.out.println("errore");
            }
        }


    }
}