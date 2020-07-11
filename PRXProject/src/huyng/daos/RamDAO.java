package huyng.daos;

import huyng.entities.RamEntity;

import java.util.List;

public class RamDAO extends BaseDAO<RamEntity, Integer> {
    public RamDAO() {
        super();
    }

    public RamEntity getByMemory(int memory){
        RamEntity result = null;
        try{
            openConnection();
            List resultList = em.createNamedQuery("Ram.findByMemory")
                    .setParameter("memory",memory)
                    .getResultList();
            if (resultList.size() != 0) result = (RamEntity) resultList.get(0);
        }finally {
            closeConnection();
        }
        return result;
    }
}
