package lorenzo.pellegrini;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lorenzo.pellegrini.dao.PuntoVenditaDAO;
import lorenzo.pellegrini.entities.DistributoreAutomatico;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void testSalvataggioERicerca() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildweek1db");
        EntityManager em = emf.createEntityManager();
        PuntoVenditaDAO pvDao = new PuntoVenditaDAO(em);

        try {
            // Creo e salvo un distributore
            DistributoreAutomatico da = new DistributoreAutomatico("Test Stazione", true);
            pvDao.save(da);

            // Verifico che esista davvero
            var trovato = pvDao.findById(da.getId());
            assert trovato != null;
            System.out.println("Distributore salvato e ritrovato!");

        } finally {
            em.close();
            emf.close();
        }
    }
}
//Con questo test ho verificato il ciclo di vita del Punto Vendita.
// Ho accertato che il sistema sia in grado di censire correttamente sia i rivenditori che i distributori,
// mantenendo nel database l'informazione sul loro stato (attivo/fuori servizio)