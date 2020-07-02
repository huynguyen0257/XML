package huyng.daos;

import huyng.entities.BrandEntity;
import huyng.utils.DBHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

public class BrandDAO extends BaseDAO<BrandEntity, Integer> {
    public BrandDAO() {
        super();
    }

    public List<BrandEntity> findByName(String name){
        List<BrandEntity> rs = null;
        name = removeUnicode(name);
        name = '%'+name+'%';
        try{
            openConnection();
            rs = em.createNamedQuery("Brand.findByName")
                    .setParameter("name",name)
                    .getResultList();
            et.commit();
            return rs;
        }finally {
            closeConnection();
        }
    }
}
