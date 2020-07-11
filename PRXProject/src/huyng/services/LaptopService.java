package huyng.services;

import huyng.daos.*;
import huyng.entities.*;
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
    BrandDAO brandDAO;
    RamDAO ramDAO;
    MonitorDAO monitorDAO;

    public LaptopService() {
        this.dao = new LaptopDAO();
        this.processorDAO = new ProcessorDAO();
        this.brandDAO = new BrandDAO();
        this.ramDAO = new RamDAO();
        this.monitorDAO = new MonitorDAO();
    }

    public List<LaptopEntity> getAll() {
        return dao.testApp();
    }

    public List<LaptopEntity> getByBrandName(String brandName) {
        List<LaptopEntity> result = null;
        List<BrandEntity> brands = brandDAO.findByName(brandName);
        if (brands != null) result = (List<LaptopEntity>) brands.get(0).getLaptops();
        return result;
    }

    public boolean insertLaptop(LaptopEntity entity, String cpu, String ram, String lcd) throws SAXException, JAXBException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, InterruptedException {
        boolean result = false;
        String reformatModel = reformatModel(entity.getModel(), entity.getBrand().getName());
        entity.setModel(reformatModel);


        //Check laptop existed
        if (entity.getModel() != null && !dao.checkExisted(entity.getModel())) {
            entity.setRam(getRamEntityByMemoryString(ram));
            entity.setMonitor(getMonitorEntityBySize(lcd));
            entity.setName(reformatName(entity.getName()));

            String cpuName = getCPUName(cpu).trim();
            String cpuModel = getCPUModel(cpuName);
            ProcessorEntity p = getProcessorByModel(cpuName,cpuModel);
            if (p != null){
                p.setCount(p.getCount()+1);
                processorDAO.update(p);
                entity.setProcessor(p);
                result = dao.insert(entity) != null;
            }
        }
        return result;
    }

    public RamEntity getRamEntityByMemoryString(String ram) {
        RamEntity result = null;
        //Format ramString
        String origan = ram;
        List<String> stringByRegex = StringHelper.getStringByRegex("([0-9])+( |)G(b|B)", ram);
        if (stringByRegex.size() != 0) ram = stringByRegex.get(0);
        if (origan.indexOf("2*") == 0) {
            ram = ram.substring(0, ram.indexOf("G"));
            ram = Integer.parseInt(ram) * 2 + "Gb";
        }

        //Return Ram
        int ramInt = Integer.parseInt(ram.replaceAll("G(b|B)",""));
        result = ramDAO.getByMemory(ramInt);
        if (result == null) {
            result = ramDAO.insert(new RamEntity(ramInt));
        }
        return result;
    }

    public MonitorEntity getMonitorEntityBySize(String sizeStr) {
        MonitorEntity result = null;
        double size =  Double.parseDouble(StringHelper.getStringByRegex("\\d+\\.?\\d", sizeStr).get(0).trim());

        result = monitorDAO.getBySize(size);
        if (result == null) {
            result = monitorDAO.insert(new MonitorEntity(size));
        }
        return result;
    }

    public ProcessorEntity getProcessorByModel(String name, String model) throws SAXException, JAXBException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException {
        ProcessorEntity p;
        //Check laptop existed
        if (model != null) {
            if (name.toLowerCase().contains("intel") && !name.toLowerCase().contains("contain modelnumber")) {
                p = processorDAO.getByModel(model);
                //Check IntelCPU Existed
                if (p != null) {
                    return p;
                }
            } else if (name.toLowerCase().contains("ryzen")) {
                p = processorDAO.getByModel(model.split(" ")[1]);
                if (p != null) {
                    return p;
                } else {
                    p = processorDAO.getAMDProcessor(model.split(" ")[0], model.split(" ")[1]);
                    ProcessorEntity newProcessor = processorDAO.insert(p);
                    return newProcessor;
                }
            }
        }
        return null;
    }


    /***
     * Help to check laptop existed
     * @param newModel
     * @param brandName
     * @return newModel with formatted - null if not contain modelNumber
     */
    public String reformatModel(String newModel, String brandName) {
        newModel = newModel.toUpperCase();
        if (newModel.equals("G5 5590 | USA US011")
                || newModel.contains("THINKPAD X1 C7")
                || newModel.equals("THINKBOOK 15")
                || newModel.equals("G3 3590")
                || newModel.equals("N5593")
                || newModel.equals("V5490 | DN160")
                || newModel.equals("V5490 | DN164")
                || newModel.equals("V5490 DN211")
                || newModel.equals("3593A")
                || newModel.equals("")
                || newModel.contains("70211826 - BLACK")) {
            newModel = null;
        }
        if (newModel.indexOf('(') != -1) newModel = newModel.substring(0, newModel.indexOf('('));
        if (newModel.indexOf('|') != -1) newModel = newModel.substring(0, newModel.indexOf('|'));
        newModel = newModel.replaceAll("  ", " ")
                .trim();
        brandName = brandName.toLowerCase();
        if (brandName.contains("asus") && newModel.lastIndexOf("-") != -1) {
            newModel = newModel.substring(newModel.lastIndexOf("-") + 1);
        } else if (brandName.contains("acer")) {
            String regex = "(AS|) ?(PH|SF|PT|AN|A|T)\\d+[- ]?[\\d\\w]+[- ]?[\\d\\w]+";
            List<String> stringfromRegex = StringHelper.getStringByRegex(regex, newModel);
            if (newModel.contains("42 R5Z6")) {
                newModel = "42 R5Z6";
            } else if (stringfromRegex.size() != 0) {
                newModel = stringfromRegex.get(0).replaceAll(" NH", "").trim();
            }
        } else if (brandName.contains("lenovo") && newModel.lastIndexOf(" ") != -1) {

            newModel = newModel.replaceAll("3-", " ").trim();
            if (newModel.lastIndexOf("PA") < newModel.lastIndexOf(' ')) {
                newModel = newModel.substring(newModel.lastIndexOf(" ") + 1);
            } else {
                String[] split = newModel.split(" ");
                newModel = split[split.length - 2] + " " + split[split.length - 1];
            }
        } else if (brandName.contains("hp") && newModel.lastIndexOf(" ") != -1) {
            newModel = newModel.substring(newModel.lastIndexOf(" ") + 1);
        } else if (brandName.contains("msi")) {
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
            newModel = newModel
                    .replaceAll(" -", "")
                    .replaceAll("-", " ")
                    .replaceAll("  ", " ")
                    .replaceAll(" USA", "-USA")
                    .replaceAll("[|] USA ", "")
                    .replaceAll("SEAL", "")
                    .replaceAll("GREY", "")
                    .replaceAll("FHD", "")
                    .replaceAll("PRO", "")
                    .replaceAll("REFURBISHED", "")
                    .replaceAll("OUTLET NEW", "")
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
            newModel = null;
        }
        return newModel.toUpperCase();
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
                model = fromRegex.get(0).replaceAll("-", " ").trim().toLowerCase();
            }
        }
        return model;
    }

    public String reformatName(String name) {
        return StringHelper.getStringByRegex("[\\d\\w -]+(([(][\\d\\w]+[)])|)",name).get(0).trim();
    }
}
