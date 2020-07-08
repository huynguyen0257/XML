package huyng.services;

import huyng.daos.LaptopDAO;
import huyng.daos.ProcessorDAO;
import huyng.entities.LaptopEntity;
import huyng.entities.ProcessorEntity;
import huyng.utils.StringHelper;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaptopService {
    LaptopDAO dao;
    ProcessorDAO processorDAO;

    public LaptopService() {
        this.dao = new LaptopDAO();
        this.processorDAO = new ProcessorDAO();
    }

    public boolean insertLaptop(LaptopEntity entity, String cpu) throws SAXException, JAXBException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, InterruptedException {
        boolean result = false;
        String reformatModel = reformatModel(entity.getModel(), entity.getBrand().getName());
        entity.setModel(reformatModel);
        entity.setRam(reformatRAM(entity.getRam()));
        entity.setName(reformatName(entity.getName()));
        String cpuName = getCPUName(cpu).trim();
        String cpuModel = getCPUModel(cpuName);
        ProcessorEntity p;
        //Check laptop existed
        if (!dao.checkExisted(entity.getModel()) && cpuModel != null){
            if (cpuName.toLowerCase().contains("intel") && !cpuName.toLowerCase().contains("contain modelnumber")) {
                p = processorDAO.getByModel(cpuModel);
                //Check IntelCPU Existed
                if (p != null) {
                    entity.setProcessor(p);
                    result = dao.insert(entity) != null;
                }
            } else if (cpuName.toLowerCase().contains("ryzen")) {
                p = processorDAO.getByModel(cpuModel.split(" ")[1]);
                if (p != null){
                    entity.setProcessor(p);
                    result = dao.insert(entity) != null;
                }else{
                    p = processorDAO.getAMDProcessor(cpuModel.split(" ")[0],cpuModel.split(" ")[1]);
                    ProcessorEntity newProcessor = processorDAO.insert(p);
                    entity.setProcessor(newProcessor);
                    result = dao.insert(entity) != null;
                }
            }
        }
        return result;
    }

    public String getCPUFromXMLString(String xmlString) {
        return StringHelper.getStringByRegex("<cpu>.*?</cpu>", xmlString).get(0).replaceAll("</?cpu>", "");
    }

    /***
     * Help to check laptop existed
     * @param newModel
     * @param brandName
     * @return
     */
    public String reformatModel(String newModel, String brandName) {
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
        } else if (brandName.contains("dell")) {
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
        } else if (brandName.contains("lg")) {
            String regex = "([ .][A-Z][A-Z0-9]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group().replaceAll("[.]", "").trim();
        } else if (brandName.contains("apple")) {
            String regex = "([A-Z][A-Z0-9]+ )";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group();
        } else if (brandName.contains("fujitsu")) {
            String regex = "([ -][A-Z][A-Z0-9]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newModel);
            if (matcher.find()) newModel = matcher.group().replaceAll("-", "").trim();
        } else if (brandName.contains("microsoft")) {
            //do not thing
        }
        return newModel;
    }

    public String getCPUName(String cpu) {
        cpu = cpu.replaceAll("®", "")
                .replaceAll("™", "")
                .replaceAll(" - ", "-")
                .replaceAll(" _ ", "-");
        //Intel Core
        List<String> stringFromRegex = StringHelper.getStringByRegex("((C|c)ore(.|) +|)(i|I|M)[0-9]( |-|\\d|\\w)+(HF|U|K|Y|G1|G7|H|HQ|G4|HK|G)", cpu);
        if (stringFromRegex.size() != 0) {
            if (!stringFromRegex.get(0).toLowerCase().contains("core"))
                cpu = "Intel Core" + stringFromRegex.get(0);
            else cpu = "Intel " + stringFromRegex.get(0);
        } else {
            stringFromRegex = StringHelper.getStringByRegex("(Ryzen(.|) |)(R\\d|\\d)( |-| - )\\d+[U|u|HS]", cpu);
            if (stringFromRegex.size() != 0) {
                cpu = stringFromRegex.get(0);
                if (!cpu.toLowerCase().contains("ryzen")) cpu = "Ryzen " + cpu;
                cpu = cpu.replaceFirst(" R", " ");
            } else {
                cpu += "Not contain ModelNumber";
            }
        }
        return cpu;
    }

    public String getCPUModel(String cpuName) {
        String model = null;
        List<String> fromRegex;
        //Intel CPU
        if (cpuName.toLowerCase().contains("intel")) {
            fromRegex = StringHelper.getStringByRegex("\\d+(HK|HF|U|K|Y|G1|G7|H|HQ|G4|G)", cpuName);
            if (fromRegex.size() != 0) model = fromRegex.get(0);

            //AMD CPU
        } else if (!cpuName.toLowerCase().contains("not contain modelnumber")) {
            fromRegex = StringHelper.getStringByRegex("\\d+[ -]+\\d+(H|U|u)", cpuName);
            if (fromRegex.size() != 0) {
                model = fromRegex.get(0).replaceAll("-"," ").trim().toLowerCase();
            }
        }
        return model;
    }

    public String reformatRAM(String ram) {
        String origan = ram;
        List<String> result = StringHelper.getStringByRegex("([0-9])+( |)G(b|B)",ram);
        if (result.size() != 0 ) ram = result.get(0);
        if (origan.indexOf("2*") == 0) {
            ram = ram.substring(0, ram.indexOf("G"));
            ram = Integer.parseInt(ram)*2 +"Gb";
        }
        return ram;
    }

    public String reformatName(String name){
        return StringHelper.getStringByRegex("[\\d\\w -]+(([(][\\d\\w]+[)])|)",name).get(0).trim();
    }
}
