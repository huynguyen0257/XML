package huyng.daos;

import huyng.entities.LaptopEntity;

import java.util.List;

public class LaptopDAO extends BaseDAO<LaptopEntity, Integer> {
    public LaptopDAO() {
        super();
    }

    public boolean checkExisted(String model){
        boolean result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findByName")
                    .setParameter("model",model)
                    .getResultList().size() != 0;
            et.commit();
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
