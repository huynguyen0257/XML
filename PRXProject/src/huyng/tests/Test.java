package huyng.tests;

import huyng.daos.BrandDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.utils.DBHelper;
import huyng.utils.JAXBHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws JAXBException {
//        JAXBHelper.generateJaxbPackage();
        BrandDAO dao = new BrandDAO();
        BrandEntity entity = new BrandEntity();
        entity.setName("Nguyễn Gia Huy");
        dao.insert(entity);
//        BrandEntity a = dao.findByID(1);
//        System.out.println(a);
        List<BrandEntity> rs = dao.findAllByNameQuery("Brand.findAll");
        rs.forEach((b)->{
            System.out.println(b.toString());
        });
    }

    public static String removeUnicode(String string){
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
