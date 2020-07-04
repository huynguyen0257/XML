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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws JAXBException {
        String regex = "([ -][A-Z][A-Z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        String[] model = getStringTest();
        for (int i = 0; i < model.length; i++) {
            String newModel = model[i];
            System.out.println("********* Oriranal : " + newModel + " **********");


            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group().replaceAll("-","").trim();
            System.out.println(newModel);
        }
    }

    public static String[] getStringTest() {
        String text = "U747-FPC07427DK\n" +
                "E749 L00U749VN00000113\n" +
                "E559 L00E559VN00000074\n" +
                "E549 L00E549VN00000110\n" +
                "U937-FPC04866DK\n" +
                "E549 L00E549VN00000111";
        String[] result = text.split("\n");
        return result;
    }
}
