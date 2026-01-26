package lorenzo.pellegrini;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lorenzo.pellegrini.dao.DaoMezzi;
import lorenzo.pellegrini.entities.Autobus;
import lorenzo.pellegrini.entities.Mezzo;
import lorenzo.pellegrini.entities.Tram;
import lorenzo.pellegrini.enums.StatoAttuale;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildweek1db");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        DaoMezzi dm=new DaoMezzi(em);

        Tram tram1 = new Tram(StatoAttuale.IN_MANUTENZIONE, 200);
        Autobus autobus1 = new Autobus(StatoAttuale.IN_SERVIZIO, 50);
        Tram tram2 = new Tram(StatoAttuale.IN_SERVIZIO, 150);
        Autobus autobus2 = new Autobus(StatoAttuale.IN_SERVIZIO, 40);
        Tram tram3 = new Tram(StatoAttuale.IN_SERVIZIO, 100);
        Autobus autobus3 = new Autobus(StatoAttuale.IN_MANUTENZIONE, 30);
        dm.salvaMezzi(autobus1);
        dm.salvaMezzi(tram1);
        dm.salvaMezzi(autobus2);
        dm.salvaMezzi(tram2);
        dm.salvaMezzi(autobus3);
        dm.salvaMezzi(tram3);






        try {
            System.out.println("Collegameto database riuscito");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }
}