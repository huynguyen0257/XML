package huyng.utils;

import huyng.crawler.PACrawler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    private static final Object LOCK = new Object();
    private static EntityManagerFactory manager;

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (manager == null) {
                try {
                    manager = Persistence.createEntityManagerFactory("NewPersistenceUnit");
                } catch (Exception e) {
//                    ex.printStackTrace();
                    Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                }
            }
        }
        return manager.createEntityManager();
    }
}
