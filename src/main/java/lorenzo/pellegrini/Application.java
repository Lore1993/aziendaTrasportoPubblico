package lorenzo.pellegrini;

import java.util.InputMismatchException;
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

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildweek1db");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        //-----------DAO-------------------
        DaoMezzi dm = new DaoMezzi(em);
        UtenteDAO ud = new UtenteDAO(em);
        PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
        TitoloViaggioDAO tvd = new TitoloViaggioDAO(em);
        PercorrenzaDAO pd = new PercorrenzaDAO(em);
        TratteDao td = new TratteDao(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        StatoMezzoDAO statoMezzoDAO = new StatoMezzoDAO(em);


        PopolaDb(dm,pvd,td,pd);

        int scelta;
        do {
            System.out.println("=== AZIENDA TRASPORTO PUBBLICO ===");
            System.out.println("1. Utente");
            System.out.println("2. Amministratore");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            try {
            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 0-> System.out.println("Chiusura applicazione in corso");
                case 1 -> MenuUtente(dm, pvd, ud, tesseraDAO, tvd);
                case 2 -> MenuAmministratore(tvd, pd, td, dm, statoMezzoDAO, pvd);
                default -> System.out.println("Comando non valido");
            }
            }catch (InputMismatchException e) {
               System.out.println("comando non valido");
                scanner.nextLine();
                scelta = -1;
            }
        } while (scelta != 0);

        try {
            System.out.println("Collegameto database riuscito");
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void MenuUtente(DaoMezzi mezziDao, PuntoVenditaDAO puntoVenditaDAO,
                                   UtenteDAO utenteDAO, TesseraDAO tesseraDAO, TitoloViaggioDAO titoloViaggioDAO) {
        Scanner sc = new Scanner(System.in);
        int scelta;
        boolean tornaIndietro = false;
        while (!tornaIndietro) {

            System.out.println("------------------- Menù Utente ----------------------------");
            System.out.println("""
                     1- Nuovo Utente\s
                     2- Nuovo Biglietto\s
                     3- Abbonamento \
                    
                     4- Vidima biglietto\s
                     5- Verifica validità abbonamento\s
                     0- Esci\
                    """);
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
                    puntoVenditaDAO.findAllPuntoVendita().forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio()));
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
                    puntoVenditaDAO.findAllPuntoVendita().forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio()));
                    long decisione = Long.parseLong(sc.nextLine());
                    PuntoVendita pv = puntoVenditaDAO.findById(decisione);

                    // CORREZIONE: Controllo se il punto vendita è attivo
                    if (pv != null && pv.isInServizio()) {
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
                    } else {
                        System.out.println("ERRORE: Punto vendita non disponibile o fuori servizio!");
                    }
                }
                case 4 -> {
                    System.out.print("ID Biglietto: ");
                    long idbiglietto = Long.parseLong(sc.nextLine());
                    System.out.println("Seleziona Mezzo:");
                    mezziDao.trovaMezziPerStatoAttuale(StatoAttuale.IN_SERVIZIO).forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    int decisione3 = Integer.parseInt(sc.nextLine());
                    titoloViaggioDAO.vidimaBiglietto(idbiglietto, mezziDao.trovaMezzoPerId(decisione3));
                }
                case 5 ->{
                    System.out.println("Inserisci il numero della tessera per il controllo: ");
                    String nt = sc.nextLine();
                    if (tesseraDAO.isTesseraValida(nt)){
                        Abbonamento t = titoloViaggioDAO.findUltimoAbbonamento(nt);
                      if (t.getTipoAbbonamento() == TipoAbbonamento.MENSILE){
                          System.out.println("Abbonamento valido fino al: " + t.getDataEmissione().plusMonths(1));
                      }else {
                          System.out.println("Abbonamento valido fino al: " + t.getDataEmissione().plusWeeks(1));
                      }
                    } else System.out.println("Abbonamento scaduto o non trovato!");
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
            System.out.println("""
                     1- Crea nuovo Punto Vendita \s
                     2- Crea nuovo Mezzo \
                    
                     3- Crea nuova Tratta\s
                     4- Crea nuova Percorrenza \
                    
                     5- Media tempo percorrenza tratte\s
                     6- Biglietti vidimati in un periodo di tempo \
                    \s
                     7- Biglietti vidimati su un mezzo  \s
                     8- Conteggio corse totali per tratta \
                    \s
                     9- Vendite totali per punto vendita\s
                     10- Cambia stato distributore \
                    
                     11- Report Mezzo/Tratta\s
                     12- Cambia stato mezzo \s
                     13- Storico mezzo \s
                     0- Torna al menù principale""");
            scelta = Integer.parseInt(sc.nextLine());
            switch (scelta) {
                case 1 -> {
                    System.out.println("Il nuovo punto vendita è un :\n 1- Rivenditore \n 2- Distributore Automatico");
                    long punto = Long.parseLong(sc.nextLine());
                    if (punto == 2){
                        System.out.println("Inserisci il nome del distributore");
                        String nomeDist = sc.nextLine();
                        System.out.println("Il distributore automatico è in funzione? \n 1- Si \n 2- No");
                        long funziona = Long.parseLong(sc.nextLine());
                        boolean attivo = funziona == 1;
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
                    long tipo = Long.parseLong(sc.nextLine());
                    System.out.println("Il mezzo è in funzione?  \n 1- Si \n 2- No");
                    long funziona = Long.parseLong(sc.nextLine());
                    System.out.println("Inserisci la capienza del mezzo: ");
                    int capienza = Integer.parseInt(sc.nextLine());
                    if (tipo == 1){
                        Mezzo mezzo1;
                        if (funziona == 1)  {
                            mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, capienza, StatoAttuale.IN_SERVIZIO);
                        }else {
                            mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, capienza, StatoAttuale.IN_MANUTENZIONE);
                        }
                        md.salvaMezzo(mezzo1);
                    } else {
                        Mezzo mezzo1;
                        if (funziona == 1)  {
                            mezzo1 = new Mezzo(TipoMezzo.TRAM, capienza, StatoAttuale.IN_SERVIZIO);
                        }else {
                            mezzo1 = new Mezzo(TipoMezzo.TRAM, capienza, StatoAttuale.IN_MANUTENZIONE);
                        }
                        md.salvaMezzo(mezzo1);
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
                    long idMezzo = Long.parseLong(sc.nextLine());
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
                    long idMezzo = Long.parseLong(sc.nextLine());
                    long count1 = tvd.countVidimatiSuMezzo( md.trovaMezzoPerId(idMezzo));
                   System.out.println("Biglietti vidimati sul mezzo: " + idMezzo + " --> " + count1);
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
                    pvd.findAllPuntoVendita().forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio()));
                    long decisione = Long.parseLong(sc.nextLine());
                    System.out.println("Totale vendite per punto vendita con id: " + decisione + " --> " +  pvd.countTitoliPerPuntoVendita(decisione) );
                }
                case 10 -> {
                    System.out.println("Seleziona Punto Vendita:");
                    pvd.findAllPuntoVendita().forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " In servizio: " + p.isInServizio()));
                    long decisione = Long.parseLong(sc.nextLine());
                    System.out.println("Inserisci il nuovo stato: \n 1- Attivo \n 2- Fuori Servizio");
                    long inFunzione = Long.parseLong(sc.nextLine());
                    pvd.impostaStatoDistributore(decisione,(inFunzione == 1));
                }
                case 11 -> {
                    td.findAllTratte().forEach(t -> System.out.println(t.getId() + " - " + t.getPartenza() + " - " + t.getCapolinea()));
                    System.out.println("Inserisci l'id della tratta: ");
                    long idTratta = Long.parseLong(sc.nextLine());
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    System.out.println("Inserisci l'id del mezzo: ");
                    long idMezzo = Long.parseLong(sc.nextLine());
                    long numeroCorse = pd.corseMezzoSuTratta(idMezzo,idTratta);
                    Double tempoMedio = pd.getTempoMedioMezzoSuTratta(idMezzo,idTratta);
                    System.out.println("Il mezzo " + idMezzo + " ha percorso la tratta " + idTratta + " per " + numeroCorse +" volte");
                    System.out.println("Tempo medio registrato: " + tempoMedio + " minuti");
                }
                case 12 -> {
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    System.out.println("Inserisci l'id del mezzo: ");
                    long idMezzo = Long.parseLong(sc.nextLine());
                    if ( md.trovaMezzoPerId(idMezzo).getStatoAttuale().getTipoStato() == StatoAttuale.IN_SERVIZIO) {
                        stmd.cambiaStato(md.trovaMezzoPerId(idMezzo), StatoAttuale.IN_MANUTENZIONE);
                    } else {
                        stmd.cambiaStato(md.trovaMezzoPerId(idMezzo), StatoAttuale.IN_SERVIZIO);
                    }
                    System.out.println("Stato cambiato con successo!");
                }
                case 13 -> {
                    md.trovaTuttiIMezzi().forEach(m -> System.out.println(m.getId() + " - " + m.getTipoMezzo()));
                    long idMezzo = Long.parseLong(sc.nextLine());
                    stmd.getStoricoMezzo( md.trovaMezzoPerId(idMezzo)).forEach(m -> System.out.println(m.getId() + " - " + m.getMezzo().getTipoMezzo() +
                            " --> " + m.getTipoStato() + " From " + m.getDataInizio() + " To " + m.getDataFine() ));

                }
                case 0 -> tornaIndietro = true;
                default -> System.out.println("errore");
            }
        }


    }

    private static void PopolaDb(DaoMezzi dm,PuntoVenditaDAO pvd,TratteDao td,PercorrenzaDAO pd){
       //Creo mezzi
        Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, 200, StatoAttuale.IN_SERVIZIO);
        Mezzo mezzo2 = new Mezzo(TipoMezzo.TRAM, 300, StatoAttuale.IN_SERVIZIO);
        dm.salvaMezzo(mezzo1);
        dm.salvaMezzo(mezzo2);

        //Creo punti vendita
        PuntoVendita rivenditore1 = new Rivenditore("Tabaccaio Rossi");
        PuntoVendita rivenditore2 = new DistributoreAutomatico("distributore automatico Q8", false);
        rivenditore2.setInServizio(false);
        PuntoVendita rivenditore3 = new DistributoreAutomatico("distributore automatico Autogrill", true);
        pvd.save(rivenditore1);
        pvd.save(rivenditore2);
        pvd.save(rivenditore3);

        //Creo Tratte
        Tratta tratta1 = new Tratta("Pomezia", "Roma-centro", 35);
        Tratta tratta2 = new Tratta("stz.Santa M. Novella", "Ponte vecchio", 20);
        td.save(tratta1);
        td.save(tratta2);

        //Creo Percorrenze
        Percorrenza percorrenza1 = new Percorrenza(LocalDate.now(), 42, tratta1, mezzo1);
        Percorrenza percorrenza2 = new Percorrenza(LocalDate.now(), 18, tratta2, mezzo2);
        Percorrenza percorrenza3 = new Percorrenza((LocalDate.of(2026, 1, 10)), 19, tratta1, mezzo1);
        Percorrenza percorrenza4 = new Percorrenza(LocalDate.of(2026, 1, 11), 20, tratta1, mezzo1);
        pd.save(percorrenza1);
        pd.save(percorrenza2);
        pd.save(percorrenza3);
        pd.save(percorrenza4);

        System.out.println("Popolamento del DataBase avvenuto con successo!");

    }
}