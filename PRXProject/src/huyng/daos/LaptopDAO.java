package huyng.daos;

import huyng.entities.LaptopEntity;
import huyng.entities.MonitorEntity;

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

    public List<LaptopEntity> getAllWithPaging(int pageSize, int pageNumber){
        List result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findAll")
                    .setMaxResults(pageSize)
                    .setFirstResult(pageSize * (pageNumber != 0 ? pageNumber - 1 : 0))
                    .getResultList();
        }finally {
            closeConnection();
        }
        return result;
    }
    public List<LaptopEntity> getByBrandWithPaging(int brandId,int pageSize, int pageNumber){
        List result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findByBrandId")
                    .setParameter("brandId",brandId)
                    .setMaxResults(pageSize)
                    .setFirstResult(pageSize * (pageNumber != 0 ? pageNumber - 1 : 0))
                    .getResultList();
        }finally {
            closeConnection();
        }
        return result;

    }

    public List<LaptopEntity> getByRamMonitorProcessorPrice(String processorLevel, int minMemory, int maxMemory, double minSize, double maxSize, int fromPrice, int toPrice){
        List result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findBySuggest")
                    .setParameter("processorLevel",'%'+processorLevel+'%')
                    .setParameter("minMemory",minMemory)
                    .setParameter("maxMemory",maxMemory)
                    .setParameter("maxSize",maxSize)
                    .setParameter("minSize",minSize)
                    .setParameter("fromPrice",fromPrice)
                    .setParameter("toPrice",toPrice)
                    .setMaxResults(5)
                    .getResultList();
        }finally {
            closeConnection();
        }
        return result;
    }

    public List<LaptopEntity> getByName(String name){
        List result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findByName")
                    .setParameter("name",'%' + name + '%')
                    .setMaxResults(10)
                    .getResultList();
        }finally {
            closeConnection();
        }
        return result;
    }

    public int countAll(){
        try{
            openConnection();
            int result = Integer.parseInt(em.createNamedQuery("Laptop.countAll").getResultList().get(0).toString());
            et.commit();
            return result;
        }finally {
            closeConnection();
        }
    }
    public int countByBrandId(int brandId){
        try{
            openConnection();
            int result = Integer.parseInt(em.createNamedQuery("Laptop.countByBrand")
                    .setParameter("brandId",brandId)
                    .getResultList()
                    .get(0).toString());
            et.commit();
            return result;
        }finally {
            closeConnection();
        }
    }

    public boolean updateWeightMark(LaptopEntity entity){
        try{
            openConnection();
            LaptopEntity laptop = em.find(LaptopEntity.class,entity.getId());
            laptop.setWeightMark(entity.getWeightMark());
            et.commit();
            return true;
        }catch (Exception e){
            return false;
        } finally {
            closeConnection();
        }
    }
}
