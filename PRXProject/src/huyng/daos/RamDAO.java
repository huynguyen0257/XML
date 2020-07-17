package huyng.daos;

import huyng.entities.ProcessorEntity;
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

    public boolean updateCount(RamEntity entity){
        try{
            openConnection();
            RamEntity processor = em.find(RamEntity.class,entity.getId());
            processor.setCount(entity.getCount());
            et.commit();
            return true;
        }finally {
            closeConnection();
        }
    }

    public void updateMark(RamEntity entity){
        try{
            openConnection();
            RamEntity newEntity = em.find(RamEntity.class,entity.getId());
            newEntity.setMark(entity.getMark());
            et.commit();
        }finally {
            closeConnection();
        }
    }
}
