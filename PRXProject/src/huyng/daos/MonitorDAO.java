package huyng.daos;

import huyng.entities.MonitorEntity;
import huyng.entities.ProcessorEntity;
import huyng.entities.RamEntity;

import java.util.List;

public class MonitorDAO extends BaseDAO<MonitorEntity, Integer> {
    public MonitorDAO() {
        super();
    }

    public MonitorEntity getBySize(double size){
        MonitorEntity result = null;
        try{
            openConnection();
            List resultList = em.createNamedQuery("Monitor.findBySize")
                    .setParameter("size",size)
                    .getResultList();
            if (resultList.size() != 0) result = (MonitorEntity) resultList.get(0);
        }finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateCount(MonitorEntity entity){
        try{
            openConnection();
            MonitorEntity processor = em.find(MonitorEntity.class,entity.getId());
            processor.setCount(entity.getCount());
            et.commit();
            return true;
        }finally {
            closeConnection();
        }
    }

    public void updateMark(MonitorEntity entity){
        try{
            openConnection();
            MonitorEntity newEntity = em.find(MonitorEntity.class,entity.getId());
            newEntity.setMark(entity.getMark());
            et.commit();
        }finally {
            closeConnection();
        }
    }
}
