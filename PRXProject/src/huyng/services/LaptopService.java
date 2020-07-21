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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;

public class LaptopService {
    private final int TIME_INCREASE_NEARBY = 7;
    public enum CompareType {
        POWER,
        COMPACTNESSS,
        ALL
    }

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

    public LaptopEntity getLaptopById(int id) {
        return dao.findByID(id);
    }

    public LaptopEntityList getAdviceLaptops(LaptopEntity currentLaptop, int numberOfAdviceLaptop) {
        Hashtable<LaptopEntity, Double> laptopMark = new Hashtable<>();
        List<LaptopEntity> laptops = dao.findAllByNameQuery("Laptop.findAll");
        laptops.forEach(l -> {
            double ramMark = l.getRam().getMark();
            double processorMark = l.getProcessor().getMark();
            double monitorMark = l.getMonitor().getMark();
            double mark = (ramMark + processorMark + monitorMark + l.getWeightMark()) / 4;
            laptopMark.put(l, mark);
        });

        Hashtable<Double, LaptopEntity> adviceLaptops = new Hashtable<>();
        double currentLaptopMark = laptopMark.get(currentLaptop);
        laptopMark.forEach((laptop, mark) -> {
            if (laptop.getId() != currentLaptop.getId()) {
                double IQRMark = sqrt((currentLaptopMark - mark) * (currentLaptopMark - mark));
                if (adviceLaptops.size() < numberOfAdviceLaptop) {
                    adviceLaptops.put(IQRMark, laptop);
                } else {
                    double maxIQR = Collections.max(Collections.list(adviceLaptops.keys()));
                    if (IQRMark < maxIQR) {
                        adviceLaptops.remove(maxIQR);
                        while (adviceLaptops.containsKey(IQRMark)) {
                            IQRMark += 0.000000001;
                        }
                        adviceLaptops.put(IQRMark, laptop);
                    }
                }
            }
        });
        return new LaptopEntityList(new ArrayList<>(adviceLaptops.values()));
    }

    public LaptopEntityList getWithSuggest(String processorLevel, String ramMemory, String monitorSize, String priceString) {


        int fromPrice = 0;
        int toPrice = 999999999;
        if (!priceString.isEmpty()) {
            fromPrice = Integer.parseInt(!priceString.split("-")[0].equals("0") ? priceString.split("-")[0] + "000000" : "0");
            toPrice = Integer.parseInt(!priceString.split("-")[1].equals("0") ? priceString.split("-")[1] + "000000" : "999999999");
        }
        double minSize = 0;
        double maxSize = 50;
        if (!processorLevel.contains("Ryzen") && !processorLevel.isEmpty()) {
            processorLevel = processorLevel.substring(processorLevel.lastIndexOf("i"));
        }
        if (!monitorSize.isEmpty()) {
            minSize = Double.parseDouble(monitorSize) - 0.5;
            maxSize = Double.parseDouble(monitorSize) + 0.5;
        }
        int minMemory = 0;
        int maxMemory = 50;
        if (!ramMemory.isEmpty()) {
            minMemory = Integer.parseInt(ramMemory) - 2;
            maxMemory = Integer.parseInt(ramMemory) + 2;
        }
//        System.out.println("'" + processorLevel + "'");
//        System.out.println(minMemory);
//        System.out.println(maxMemory);
//        System.out.println(minSize);
//        System.out.println(maxSize);
//        System.out.println(fromPrice);
//        System.out.println(toPrice);
        return getNearBy(processorLevel, minMemory, maxMemory, minSize, maxSize, fromPrice, toPrice, -1);
    }

    private LaptopEntityList getNearBy(String processorLevel, int minMemory, int maxMemory, double minSize, double maxSize, int fromPrice, int toPrice, int count) {
        LaptopEntityList result = new LaptopEntityList(dao.getByRamMonitorProcessorPrice(processorLevel, minMemory, maxMemory, minSize, maxSize, fromPrice, toPrice));
        if (result.getLaptop().size() == 0 && count < TIME_INCREASE_NEARBY){
            count++;
            if (count%2 == 0){//update monitor
                minSize -= 0.5;
                maxSize =+ 0.5;
            }else {//update ram
                minMemory += 2;
                maxMemory -= 2;
            }
            result = getNearBy(processorLevel, minMemory, maxMemory, minSize, maxSize, fromPrice, toPrice, count);
        }
        return result;
    }

    public Hashtable<LaptopEntity, Double> getCompareLaptop(List<Integer> ids, CompareType type) {
        Hashtable<LaptopEntity, Double> compareEntities = new Hashtable<>();
        if (ids != null) {
            ids.forEach(id -> {
                LaptopEntity laptop = dao.findByID(id);
                double mark = 0;
                if (type == CompareType.ALL) {
                    mark = (laptop.getRam().getMark() + laptop.getProcessor().getMark() + laptop.getMonitor().getMark() + laptop.getWeightMark()) / 4;
                } else if (type == CompareType.COMPACTNESSS) {
                    mark = (laptop.getRam().getMark() + laptop.getProcessor().getMark() + laptop.getMonitor().getMark() * 2 + laptop.getWeightMark() * 2) / 6;
                } else {//POWER
                    mark = (laptop.getRam().getMark() * 2 + laptop.getProcessor().getMark() * 2 + laptop.getMonitor().getMark() + laptop.getWeightMark()) / 6;
                }
                compareEntities.put(laptop, mark);
            });
        }
        List<Map.Entry<LaptopEntity, Double>> list = new ArrayList<Map.Entry<LaptopEntity, Double>>(compareEntities.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<LaptopEntity, Double>>() {
            public int compare(Map.Entry<LaptopEntity, Double> entry1, Map.Entry<LaptopEntity, Double> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue,
                (v1, v2) -> v1,
                Hashtable::new));
    }

    public List<LaptopEntity> getAll() {
        return dao.findAllByNameQuery("Laptop.findAll");
    }

    public LaptopEntityList getAllWithPaging(int pageSize, int pageNumber) {
        LaptopEntityList result = new LaptopEntityList();
        result.setLaptop(dao.getAllWithPaging(pageSize, pageNumber));
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        int totalPage = getTotalPage(pageSize, dao.countAll());
        result.setTotalPage(totalPage);
        return result;
    }

    public LaptopEntityList getByBrandWithPaging(int brandId, int pageSize, int pageNumber) {
        LaptopEntityList result = new LaptopEntityList();
        result.setLaptop(dao.getByBrandWithPaging(brandId, pageSize, pageNumber));
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setTotalPage(getTotalPage(pageSize, dao.countByBrandId(brandId)));
        return result;
    }

    public LaptopEntityList getByName(String name){
        return new LaptopEntityList(dao.getByName(name));
    }

    public int getNumberOfLaptop(){
        return dao.countAll();
    }

    public boolean insertLaptop(LaptopEntity entity, String cpu, String ram, String lcd, String realPath) throws SAXException, JAXBException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, InterruptedException {
        boolean result = false;
        entity.setModel(reformatModel(entity.getModel(), entity.getBrand().getName()));
        entity.setWeight(reformatWeight(entity.getWeight()));


        //Check laptop existed
        if (entity.getModel() != null && !dao.checkExisted(entity.getModel())) {
            String cpuName = getCPUName(cpu).trim();
            String cpuModel = getCPUModel(cpuName);

            RamEntity ramEntity = getRamEntityByMemoryString(ram);
            MonitorEntity monitorEntity = getMonitorEntityBySize(lcd);
            ProcessorEntity p = getProcessorByModel(cpuName, cpuModel, realPath);
            if (p != null && ramEntity != null && monitorEntity != null) {
                p.setCount(p.getCount() + 1);
                ramEntity.setCount(ramEntity.getCount() + 1);
                monitorEntity.setCount(monitorEntity.getCount() + 1);
                monitorDAO.updateCount(monitorEntity);
                ramDAO.updateCount(ramEntity);
                processorDAO.updateCount(p);
                entity.setProcessor(p);
                entity.setRam(ramEntity);
                entity.setMonitor(monitorEntity);
                result = dao.insert(entity) != null;
            }
        }
        return result;
    }

    public boolean updateLaptopWegihtMark(LaptopEntity entity) {
        LaptopEntity updateEntity = dao.findByID(entity.getId());
        updateEntity.setWeightMark(entity.getWeightMark());
        return dao.updateWeightMark(updateEntity);
    }



    private int getTotalPage(int pageSize, int objectSize) {
        if ((objectSize % pageSize) == 0) {
            return objectSize / pageSize;
        }
        if (pageSize < objectSize) {
            return objectSize / pageSize + 1;
        }
        return 1;
    }

    private RamEntity getRamEntityByMemoryString(String ram) {
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
        int ramInt = Integer.parseInt(ram.replaceAll("G(b|B)", ""));
        result = ramDAO.getByMemory(ramInt);
        if (result == null) {
            result = ramDAO.insert(new RamEntity(ramInt, 0));
        }
        return result;
    }

    private MonitorEntity getMonitorEntityBySize(String sizeStr) {
        MonitorEntity result = null;
        double size = Double.parseDouble(StringHelper.getStringByRegex("\\d+\\.?\\d", sizeStr).get(0).trim());
        if (size == 1920) return null;
        result = monitorDAO.getBySize(size);
        if (result == null) {
            result = monitorDAO.insert(new MonitorEntity(size, 0));
        }
        return result;
    }

    private ProcessorEntity getProcessorByModel(String name, String model, String realPath) throws SAXException, JAXBException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException {
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
                    p = processorDAO.getAMDProcessor(model.split(" ")[0], model.split(" ")[1], realPath);
                    p.setCount(1);
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
    private String reformatModel(String newModel, String brandName) {
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

    private String getCPUName(String cpu) {
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

    private String getCPUModel(String cpuName) {
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

    private double reformatWeight(double weight) {
        if (weight > 500) return weight / 1000;
        else return weight;
    }
}
