package lorenzo.pellegrini;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lorenzo.pellegrini.dao.UtenteDAO;
import lorenzo.pellegrini.entities.Utente;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildweek1db");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();


        try {
            System.out.println("Collegameto database riuscito");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }}
    }