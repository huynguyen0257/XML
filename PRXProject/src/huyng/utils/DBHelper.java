package huyng.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBHelper {
    private static final Object LOCK = new Object();
    private static EntityManagerFactory manager;

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (manager == null) {
                try {
                    manager = Persistence.createEntityManagerFactory("NewPersistenceUnit");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return manager.createEntityManager();
    }
}
