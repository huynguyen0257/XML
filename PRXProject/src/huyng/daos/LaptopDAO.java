package huyng.daos;

import huyng.entities.LaptopEntity;

import java.util.List;

public class LaptopDAO extends BaseDAO<LaptopEntity, Integer> {
    public boolean checkExisted(String model) {
        boolean result;
        try {
            openConnection();
            result = em.createNamedQuery("Laptop.findByModel")
                    .setParameter("model", model)
                    .getResultList().size() != 0;
            et.commit();
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<LaptopEntity> testApp(){
        List<LaptopEntity> result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findAll")
                    .setParameter("name","Lenovo ThinkPad E14")
                    .getResultList();
        }finally {
            closeConnection();
        }
        return result;
    }
}
