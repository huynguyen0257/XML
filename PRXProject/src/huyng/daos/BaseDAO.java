package huyng.daos;

import huyng.entities.LaptopEntity;
import huyng.utils.DBHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDAO<T, PK extends Serializable> {
    EntityManager em;
    EntityTransaction et;

    protected Class<T> entityClass;

    public BaseDAO() {
        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != BaseDAO.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }
        this.entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    protected void openConnection(){
        em = DBHelper.getEntityManager();
        et = em.getTransaction();
        et.begin();
    }
    protected void closeConnection(){
        if (em!= null) em.close();
    }

    public T insert(T t){
        try {
            openConnection();
            em.persist(t);
            et.commit();
            return t;
        }finally {
            closeConnection();
        }
    }

    public T findByID(PK id){
        try{
            openConnection();
            T result = em.find(entityClass,id);
            et.commit();
            return result;
        }finally {
            closeConnection();
        }
    }

    public List<T> findAllByNameQuery(String nameQuery){
        try{
            openConnection();
            List<T> result = em.createNamedQuery(nameQuery).getResultList();
            et.commit();
            return result;
        }finally {
            closeConnection();
        }
    }



    protected static String removeUnicode(String string){
        string = string.toLowerCase()
                .replaceAll("[áàảạãắẵẳặằấầậẩẫ]","a")
                .replaceAll("[óòọõỏốồộổỗơớôờợỡở]","o")
                .replaceAll("[éèẹẽẻêệễểềế]","e")
                .replaceAll("[úùụủũứừựữửư]","u")
                .replaceAll("[íìịỉĩ]","i")
                .replaceAll("[đ]","d");
        return string;
    }
}
