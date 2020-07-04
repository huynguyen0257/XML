package huyng.daos;

import huyng.entities.LaptopEntity;
import huyng.crawler.MainThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaptopDAO extends BaseDAO<LaptopEntity, Integer> {
    public LaptopDAO() {
        super();
    }

    public static String reformatModel(String newModel, String brandName) {
        if (newModel.indexOf('(') != -1) newModel = newModel.substring(0, newModel.indexOf('('));
        if (newModel.indexOf('|') != -1) newModel = newModel.substring(0, newModel.indexOf('|'));
        newModel = newModel.replaceAll("  ", " ")
                .trim();
        brandName = brandName.toLowerCase();
        if (brandName.contains("asus") && newModel.lastIndexOf("-") != -1)
            newModel = newModel.substring(newModel.lastIndexOf("-") + 1);
        else if (brandName.contains("acer")) {
            String regex = "[A-Z]([A-Z0-9- ])+ ";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find() && !newModel.contains("AS A515-55-55HG")) {
                newModel = matcher.group(0);
            }
        } else if (brandName.contains("lenovo") && newModel.lastIndexOf(" ") != -1) {
            newModel = newModel.replaceAll("3-", " ");
            if (newModel.indexOf("PA") == -1) {
                newModel = newModel.substring(newModel.lastIndexOf(" ") + 1);
            } else {
                String[] split = newModel.split(" ");
                newModel = split[split.length - 2] + " " + split[split.length - 1];
            }
        } else if (brandName.contains("hp") && newModel.lastIndexOf(" ") != -1)
            newModel = newModel.substring(newModel.lastIndexOf(" ") + 1);
        else if (brandName.contains("msi")) {
            newModel = newModel.replaceAll("[A-Z]([a-z])+", "").trim();
            if (newModel.indexOf("-") != -1)
                newModel = newModel.replaceAll("-", " ");
            String[] split = newModel.split(" ");
            if (split.length >= 3) {
                newModel = split[split.length - 2] + " " + split[split.length - 1];
            }
        }
        else if (brandName.contains("dell")){
            if (newModel.indexOf("/") != -1) {
                newModel = newModel.substring(0, newModel.indexOf("/"));
            }
            newModel = newModel.replaceAll("[A-Z]([a-z])+", "")
                    .replaceAll(" -", "")
                    .replaceAll(" USA", "-USA")
                    .replaceAll("SEAL", "")
                    .trim();
            String[] split = newModel.split(" ");
            newModel = split[split.length - 1];
        }else if (brandName.contains("lg")){
            String regex = "([ .][A-Z][A-Z0-9]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group().replaceAll("[.]","").trim();
        }else if (brandName.contains("apple")){
            String regex = "([A-Z][A-Z0-9]+ )";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group();
        }else if (brandName.contains("fujitsu")){
            String regex = "([ -][A-Z][A-Z0-9]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group().replaceAll("-","").trim();
        }else if (brandName.contains("microsoft")){
            //do not thing
        }
        return newModel;
    }

    public boolean checkExisted(String model) {
        boolean result;
        try{
            openConnection();
            result = em.createNamedQuery("Laptop.findByName")
                    .setParameter("model",model)
                    .getResultList().size() != 0;
            et.commit();
        }finally {
            closeConnection();
        }
        return result;
    }
}
