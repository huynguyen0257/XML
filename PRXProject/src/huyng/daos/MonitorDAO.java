package huyng.daos;

import huyng.entities.MonitorEntity;

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
}
