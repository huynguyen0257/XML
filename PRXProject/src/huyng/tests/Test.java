package huyng.tests;

import huyng.daos.BrandDAO;
import huyng.daos.LaptopDAO;
import huyng.daos.ProcessorDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.entities.ProcessorEntity;
import huyng.services.LaptopService;
import huyng.utils.DBHelper;
import huyng.utils.JAXBHelper;
import huyng.utils.StringHelper;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws JAXBException, ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        String[] model = getStringTest();
//        LaptopService service = new LaptopService();
//        ProcessorDAO dao = new ProcessorDAO();
//        List<ProcessorEntity> processors = dao.findAllByNameQuery("Processor.findAll");
//        processors.forEach(p ->{
//            System.out.println("ProcessorName : " +p.getName());
//            p.getLaptops().forEach(l ->{
//                System.out.println(l.toString());
//            });
//        });
        for (int i = 0; i < model.length; i++) {
            textString(model[i]);
        }
    }

    public static void textString(String text){
//        System.out.println(text.indexOf(':'));
//        System.out.println(text.indexOf('('));
        System.out.println(StringHelper.getStringByRegex("[\\d\\w -]+(([(][\\d\\w]+[)])|)",text).get(0).trim());
    }

    public static List<String> getStringByRegex(String regex, String string) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        int count = 1;
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    public static String[] getStringTest() {
        String text = "Laptop Asus Zenbook UX334FLC-A4096T (i5-10210U/8GB/512GB SSD/13.3FHD/MX250 2GB/Win10/ Blue/SCR_PAD/Túi)\n" +
//                "Laptop Asus Zenbook UX581GV-H2029T (i7-9750H/32GB/1TB SSD/15.6UHD/RTX2060 6GB DDR6/Win10/Silver)\n" +
//                "Laptop Asus D509DA-EJ116T (Ryzen 3-3200U/4GB/1TB HDD/15.6FHD/AMD Radeon/Win10/Silver)\n" +
//                "Laptop Asus Gaming Scar GU502GV-AZ079T (i7-9750H/16GB/512GB SSD/15.6FHD/RTX2060 6GB DDR6/Win10/Black Metal/RGB/ Balo/Chuột)\n" +
//                "Laptop Asus Gaming GX502GW-AZ129T (i7-9750H/16GB/1TB SSD/15.6FHD/RTX2070 8GB DDR6/Win10/Black/Balo/Chuột)\n" +
//                "Laptop Asus UX534FT-A9168T (i5-10210U/8GB/512GB SSD/15.6FHD/GTX1650 MAX Q 4GB/Win10/ Blue/Túi Sleeve)\n" +
//                "Lenovo ThinkPad X1 Carbon C7 GEN 7 : i7-10510U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD IPS | FreeDos | Black\n" +
//                "Lenovo Legion Y540-15IRH (81SY00FAVN) : i7-9750H | 8GB RAM | 128GB SSD + 1TB HDD | GTX 1650 4GB | 15.6 FHD IPS | Win10\n" +
//                "Lenovo Ideapad S540-15IML (81NG004PVN) i3-10110U | 4GB RAM | 512GB SSD PCIe | UHD Graphics 630 | 15.6 FHD IPS | Win10 | Grey\n" +
//                "Lenovo IdeaPad S540-15IML : i7-10510U | 8GB RAM | 512GB SSD | Geforce MX250 2GB + UHD Graphics 630 | 15.6 IPS FHD | Finger | Backlit Key | FreeDos | Mineral Gray, Abyss Blue\n" +
//                "Lenovo ThinkPad E14 ( 20RAS01000) : i5-10210U | 4GB RAM | 256GB SSD | UHD Graphics 620 | 14” FHD IPS | Finger | FreeDos\n" +
//                "Lenovo Thinkbook 15 : i5-1035G1 | 4GB RAM | 1TB HDD | AMD R630 2GB + Intel Graphics HD 620 | 15.6 FHD | Backlit Key | FreeDos | Silver\n" +
//                "Lenovo Legion 5 15IMH05 (82AU004XVN) : i5-10300H | 8GB RAM | 512GB SSD | GTX 1650 4GB + UHD Graphics | 15.6 FHD IPS 120Hz | WIN 10 | Phantom Black\n" +
//                "Lenovo Legion Y545 : i7-9750H | 16GB RAM | 1TB HDD + 512GB SSD PCIe | GTX 1660Ti 6GB | 15.6 FHD IPS 144Hz | Win10\n" +
//                "Lenovo Legion 7 15IMH05 (81YT001QVN) : i7-10750H | 32GB RAM | 1TB SSD | RTX 2060 6GB + UHD Graphics | 15.6 FHD IPS 144Hz | RGB Perkey | WIN 10 | Slate Grey\n" +
//                "Lenovo IdeaPad Gaming 3 15IMH05 (81Y40067VN) : i7-10750H | 8GB RAM | 512GB SSD | GTX 1650 4GB + UHD Graphics | 15.6 FHD IPS | WIN 10 | Onyx Black\n" +
//                "Lenovo IdeaPad Gaming 3 15IMH05 (81Y4006SVN) : i5-10300H | 8GB RAM | 512GB SSD | GTX 1650 4GB + UHD Graphics | 15.6 FHD IPS | WIN 10 | Onyx Black\n" +
//                "Lenovo IdeaPad S540-14IML : i7-10510U | 8GB RAM | 512GB SSD | Geforce MX250 2GB + UHD Graphics 630 | 14.0 FHD IPS | Finger | Backlit Key | FreeDos | Mineral Gray, Abyss Blue\n" +
//                "Lenovo Ideapad 3 14IIL05 : i3-1005G1 | 4GB RAM | 1TB HDD | UHD Graphics 630 | 14.0 FHD IPS | FreeDos | Black, Platinum Gray\n" +
//                "Lenovo ThinkPad E14 ( 20RAS0KX00) : i5-10210U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 620 | 14” FHD IPS | Finger | FreeDos\n" +
//                "Lenovo Legion 5 15IMH05 (82AU004YVN) : i7-10750H | 8GB RAM | 512GB SSD | GTX 1650 4GB + UHD Graphics | 15.6 FHD IPS 120Hz | WIN 10 | Phantom Black\n" +
//                "Lenovo Legion Y540-15IRH (81SY004WVN) : i5-9300H | 8GB RAM | 128GB SSD + 1TB HDD | GTX 1650 4GB | 15.6 FHD IPS | Win10\n" +
//                "Laptop Acer Aspire A514-53 3821 NX.HUSSV.001 (Core i3-1005G1/4Gb/256Gb SSD/ 14.0 FHD/VGA ON/Win10/Silver)\n" +
//                "Laptop Acer Aspire A315 54K 37B0 NX.HEESV.00D (i3-8130U/4Gb/256Gb SSD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
//                "Laptop Acer Nitro series AAN515 54 51X1 NH.Q5ASV.011 (Core i5-9300H/8Gb/256Gb SSD/15.6' FHD/GTX1050-3GB/Win10/Black)\n" +
//                "Laptop Acer Gaming Predator Triton 500 PT515-52-72U2 NH.Q6WSV.001 (Core i7-10875H/32Gb/1Tb SSD/15.6''FHD-300Hz/RTX2080-8Gb/Win10/Black)\n" +
//                "Laptop Acer Nitro series AN515-54-76RK NH.Q59SV.023 (Core i7-9750H/8Gb/512Gb SSD/15.6' FHD/GTX1650 4Gb/Win10/Black)\n" +
//                "Laptop Acer Nitro series AN515-54-71UP NH.Q5ASV.008 (Core i7-8750H/8Gb/256Gb SSD/15.6' FHD/GTX1050 3GB5/Win10/Black)\n" +
//                "Laptop Acer Aspire A315 55G 59BC NX.HNSSV.003 (I5-10210U/ 4Gb/256Gb SSD/ 15.6FHD/MX230 2Gb/ Win10/Black)\n" +
//                "Laptop Acer Aspire A315 23G R33Y NX.HVSSV.001 (Ryzen 5 3500u/8Gb/512Gb SSD/ 15.6 FHD/AMD Radeon 625 2GB/ Win10/Silver)\n" +
//                "Laptop Acer Swift 3 SF315-52-50T9 NX.GZBSV.002 (Core i5-8250U/8Gb/256Gb SSD/15.6'FHD/VGA ON/Windows 10/Gold)\n" +
//                "Laptop Acer Aspire A315 23 R0ML NX.HVUSV.004 (Ryzen 3- 3250u/4Gb/512Gb SSD/ 15.6 FHD/VGA ON/ Win10/Silver)\n" +
//                "Laptop Acer Swift 3 SF314 41 R4J1 NX.HFDSV.001 (Ryzen 3- 3200U/4Gb/256Gb SSD/ 14.0' FHD/ AMD Radeon Vega 3/Win10/Silver)\n" +
//                "Laptop Acer Aspire A315-42 R4XD NX.HF9SV.008 (Ryzen5-3500U/8Gb/512Gb SSD/ 15.6' FHD/VGA ON/ Win10/Black)\n" +
//                "Laptop Acer Aspire A315 23 R8BA NX.HVUSV.001 (Ryzen 3- 3250u/4Gb/256Gb SSD/ 15.6 FHD/VGA ON/ Win10/Silver)\n" +
//                "Laptop Acer Aspire A315 56 37DV NX.HS5SV.001 (i3 1005G1/4Gb/256Gb SSD/ 15.6 FHD/VGA ON/Win10/Black)\n" +
//                "Laptop Acer Swift 5 SF514-53T-51EX NX.H7KSV.001 (Core i5-8265U/8Gb/256Gb SSD/14.0' FHD/Touch/VGA ON/Win10/Grey)\n" +
//                "ACER NITRO 5 AN515-55-58A7 (NH.Q7RSV.002) : I5-10300H | 8GB RAM | 512GB SSD | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS | WIN 10\n" +
//                "Acer Swift 3 SF314-58-55RJ (NX.HPMSV.006) : i5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 | Silver\n" +
//                "Acer Aspire 3 A315-23G-R33Y (NX.HVSSV.001) : R5-3500U | 4GB RAM | 512GB SSD | Radeon R625 2GB + Radeon™ Vega 8 Graphics | 15.6 FHD | WIN 10\n" +
//                "Acer Aspire 5 A514-53-3821 (NX.HUSSV.001) : i3-1005G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 \n" +
//                "Acer Aspire 3 A315-23-R0ML (NX.HVUSV.004) : R3-3250U | 4GB RAM | 512GB SSD | AMD Radeon Graphics | 15.0 FHD | WIN 10\n" +
//                " Acer Swift 7 SF714-52T-7134 (NX.H98SV.002) : i7-8500Y | 16GB RAM | 512GB SSD | Intel HD Graphics 615 | 14.0FHD IPS Touch | WIN 10 | Finger | Black, White\n" +
//                "Acer Aspire 3 A315-23-R8BA (NX.HVUSV.001) : R3-3250U | 4GB RAM | 256GB SSD | AMD Radeon Graphics | 15.0 FHD | WIN 10\n" +
//                "Acer Aspire 5 A515-55-55HG (NX.HSMSV.004) : i5-1035G1 | 8GB RAM | 512GB SSD | UHD Graphics 630 | 15.6 FHD IPS | WIN10 | Silver\n" +
//                "Laptop LG Gram 15Z90N-V.AR55A5 (i5-1035G7/8GB/512GB SSD/15FHD/VGA ON/WIN 10/Dark Silver/LED_KB)\n" +
//                "Laptop LG Gram 15Z980-G.AH55A5 (i5-8250U/8GB/512Gb SSD/15.6FHD/VGA ON/Win10/Grey)\n" +
//                "Laptop LG Gram 17Z90N-V.AH75A5 (i7-1065G7/8GB/512GB SSD/17WQXGA(2560x1600)/VGA ON/WIN 10/Dark Silver/LED_KB)\n" +
//                "Laptop LG Gram 14ZD90N-V.AX55A5 (i5-1035G7/8GB/512GB SSD/14FHD/VGA ON/Dos/Dark Silver/LED_KB)\n" +
//                "Laptop LG Gram 14ZD90N-V.AX53A5 (i5-1035G7/8GB/256GB SSD/14FHD/VGA ON/Dos/White/LED_KB)\n" +
//                "Laptop LG Gram 15ZD90N-V.AX56A5 (i5-1035G7/8GB/512GB SSD/15FHD/VGA ON/Dos/White/LED_KB)\n" +
//                "Laptop LG Gram 14Z90N-V.AR52A5 (i5-1035G7/8GB/256GB SSD/14FHD/VGA ON/WIN 10/Dark Silver/LED_KB)\n" +
//                "Acer Nitro 5 AN515-43-R9FD (NH.Q6ZSV.003) : R5-3550H | 8GB RAM | 512GB SSD | RX Vega 8 Graphics + Geforce GTX 1650 4GB | 15.6 FHD IPS | WIN 10 | Black\n" +
//                "Acer Aspire 7 A715-41G-R8KQ (NH.Q8DSV.001) : R5-3550H | 8GB RAM | 256GB SSD | RX Vega 8 Graphics + Geforce GTX 1650 4GB | 15.6 FHD IPS | WIN 10 | Black\n" +
//                "Acer Aspire 5 A515-54G-56JG (NX.HVGSV.002) : i5-10210U | 8GB RAM | 512GB SSD | Geforce MX350 2GB + UHD Graphics 630 | 15.6 FHD IPS | WIN10 | Silver\n" +
//                "Acer Aspire 5 A515-55-55JA (NX.HSMSV.003) : i5-1035G1 | 4GB RAM | 512GB SSD | UHD Graphics 630 | 15.6 FHD IPS | WIN10 | Silver\n" +
//                "Acer Swift 3 SF314-57-54B2 (NX.HJKSV.001) : i5-1035G1 | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 | Pink\n" +
//                "ACER PREDATOR HELIOS 300 PH315-52-78MG (NH.Q53SV.009) : i7-9750H | 8GB RAM | 512GB SSD | GTX 1660Ti 6GB + UHD Graphics 630 | 15.6FHD IPS 144hz | WIN 10 | RGB | BLACK\n" +
//                "ACER NITRO 5 AN515-55-73VQ (NH.Q7RSV.001) : i7-10750H | 8GB RAM | 512GB SSD | GTX 1650 4GB + Intel UHD Graphics 630 | 15.6 FHD IPS | WIN 10 | BLACK\n" +
//                "Acer Aspire 5 A514-53-50JA (NX.HUSSV.002) : i5-1035G1 | 4GB DDR4 | 256GB SSD | UHD Graphics 630 |14.0 FHD | WIN 10 | Silver\n" +
//                "Acer Aspire 3 A315-56-59XY (NX.HS5SV.003) : i5-1035G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD | WIN 10\n" +
//                "Acer Nitro 5 AN515-43-R4VJ (NH.Q6ZSV.004) : R7-3750H | 8GB RAM | 512GB SSD | RX Vega 8 Graphics + Geforce GTX 1650 4GB | 15.6 FHD IPS | WIN 10 | Black\n" +
//                "Acer Swift 5 SF514-54T-55TT (NX.HLGSV.002) : i5-1035G1 | 8GB RAM | 512GB SSD | Intel® Iris® Plus Graphics | 14.0FHD IPS TOUCH | WIN 10 | WHITE\n" +
//                "Acer Aspire 5 A514-53-50P9 (NX.HUSSV.004) : i5-1035G1 | 8GB DDR4 | 512GB SSD | UHD Graphics 630 | 14.0 FHD | WIN 10 | Silver\n" +
//                "ACER NITRO 5 AN515-55-5304 (NH.Q7NSV.002) : I5-10300H | 8GB RAM | 512GB SSD | GTX 1650TI 4GB + UHD Graphics 630 | 15.6 FHD IPS | WIN 10\n" +
//                "ACER NITRO 5 AN515-55-70AX (NH.Q7NSV.001) : i7-10750H | 8GB RAM | 512GB SSD | GTX 1650Ti 4GB + Intel UHD Graphics 630 | 15.6 FHD IPS | WIN 10 | BLACK\n" +
//                "Acer Aspire 5 A514-53-346U (NX.HUSSV.005) : i3-1005G1 | 4GB RAM | 512GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 | Silver\n" +
//                "Acer Swift 3 SF314-57G-53T1 (NX.HJESV.001) : i5-1035G1 | 8GB RAM | 512GB SSD | GeForce MX250 2GB + UHD Graphics 630 | 14 FHD IPS | WIN 10 | Gray\n" +
//                "Acer Swift 3 SF314-57-52GB (NX.HJFSV.001) : i5-1035G1 | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 | Gray\n" +
//                "Laptop HP Omen Gaming 15-dh0169TX 8ZR37PA (i9-9880H/16GB/512GB SSD+32GB Optane/15.6FHD-240Hz-300nit/RTX2080 8GB/DVDR Ext/Win 10/Black)\n" +
//                "Laptop HP ProBook 430 G7 9GQ07PA (i3-10110/4GB/256GB SSD/13.3HD/VGA ON/WIN 10/Silver/LED_KB)\n" +
//                "Laptop HP ProBook 440 G7 9MV53PA (i5-10210U/4Gb/512GB SSD/14FHD/VGA ON/DOS/Silver)\n" +
//                "Laptop HP ProBook 450 G7 9GQ40PA (i5-10210U/8Gb/256GB SSD/15.6FHD/VGA ON/Win 10/Silver/LEB_KB)\n" +
//                "Laptop HP EliteBook 1050 G1 5JJ65PA (i5 8300H/RAM 16Gb/512Gb SSD/15.6FHD/GTX1050 Max Q 4GB/Dos/Silver)\n" +
//                "Laptop HP ProBook 430 G7 9GQ06PA (i5-10210/8GB/256GB SSD/13.3FHD/VGA ON/DOS/Silver)\n" +
//                "Laptop HP ProBook 450 G6 6FG83PA (i7-8565U/8Gb/256GB SSD/ 15.6FHD/MX130 2GB/ Dos/Silver)\n" +
//                "Acer Swift 3 SF314-42-R5Z6 (NX.HSESV.001) : R5-4500U | 8GB RAM | 512GSSD | AMD Radeon Graphics | 14.0 FHD IPS | WIN 10 | Silver\n" +
//                "Acer Aspire 3 A315-56-37DV (NX.HS5SV.001) :  i3-1005G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD | WIN10 | Black\n" +
//                "Acer Swift 5 SF514-54T-793C (NX.HLGSV.001) : i7-1065G7 | 8GB RAM | 512GB SSD | Intel® Iris® Plus Graphics | 14.0FHD IPS TOUCH | WIN 10 | WHITE\n" +
//                "ACER PREDATOR HELIOS 300 PH315-52-75R6 (NH.Q54SV.003) : i7-9750H | 16GB RAM | 512GB SSD | RTX 2060 6GB + UHD Graphics 630 | 15.6FHD IPS 144hz | WIN 10 | RGB | BLACK\n" +
//                "Acer Swift 3 SF314-58-39BZ (NX.HPMSV.007) : i3-10110U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14 FHD IPS | WIN 10 | Silver\n" +
//                "Acer Aspire 3 A315-55G-504M (NX.HNSSV.006) : i5-10210U | 4GB RAM | 512GB SSD | Geforce MX230 2GB + UHD Graphics 630 | 15.6 FHD | WIN 10\n" +
//                " Acer Aspire 5 A515-55-37HD (NX.HSMSV.006) : i3-1005G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD IPS | WIN 10\n" +
//                "Laptop HP Envy x360-ar116AU 9DS89PA (Ryzen 7-3700U/8Gb/512Gb SSD/13.3FHD Touch/AMD Radeon/Win10/Black)\n" +
//                "Laptop HP ProBook 430 G7 9GQ05PA (i5-10210/4GB/256GB SSD/13.3FHD/VGA ON/Win 10/Silver)\n" +
//                "Laptop HP ProBook 430 G7 9GQ02PA (i5-10210/8GB/512GB SSD/13.3FHD/VGA ON/DOS/Silver)\n" +
//                "Laptop HP Pavilion 14-ce3018TU 8QN89PA (Gold)\n" +
//                "Laptop HP ProBook 450 G7 9GQ27PA (i7-10510U/8GB/512GB SSD/15.6FHD/Nvidia MX250-2GB/DOS/Silver)\n" +
//                "MSI GF65 Thin 9SD-070VN I5-9300H | 8GB RAM | 512GB SSD PCIe | GTX 1660Ti 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | Win10 | Black\n" +
//                "MSI GE75 Raider 9SF-1014VN : I7-9750H | 16GB RAM | 1TB HDD + 512GB SSD PCle | RTX 2070 8GB + UHD Graphics 630 | 17.3 FHD 240Hz | Per key RGB | Win10\n" +
//                "MSI GP65 Leopard 9SD-224VN i7-9750H | 16GB | 512GB SSD PCIe | GTX 1660Ti 6GB | 15.6 FHD 144Hz | Win10 | PerKey RGB\n" +
//                "MSI Modern 14 A10RB-888VN : I7-10510U | 8GB RAM | 512GB SSD PCIe | Geforce MX250 2GB + UHD Graphics 630 | 14.0 FHD IPS | Win10 | Gray\n" +
//                "MSI GE65 Raider 9SE-223VN : I7-9750H | 16GB RAM | 1TB HDD + 512GB SSD PCle | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD 240Hz | Per key RGB | Win10\n" +
//                "MSI GE65 Raider 9SF-222VN : I7-9750H | 16GB RAM | 1TB SSD PCle | RTX 2070 8GB + UHD Graphics 630 | 15.6 FHD 240Hz | Per key RGB | Win10\n" +
//                "MSI GS65 Stealth 9SD-1409VN : I5-9300H | 8GB RAM | 512GB SSD PCIe | GTX 1660Ti 6GB + UHD Graphics 630 | 15.6 FHD 144Hz | Per Key RGB | Win10\n" +
//                "MSI GE66 Raider 10SF-285 : i7-10875H | 16GB RAM | 1TB SSD | RTX 2070 8GB | 15.6 FHD IPS 240Hz | RGB | Win10 | Dragonshield Limited Edition\n" +
//                "MSI GE75 Raider 9SF-806VN : I7-9750H | 16GB RAM | 512 SSD PCle + 1TB HDD | RTX 2070 8GB + UHD Graphics 630 | 17.3 FHD 144Hz | Win 10 | RGB\n" +
//                "MSI GE75 Raider 10SFS-076VN : I9-10980HK | 16GB RAM | 1TB HDD + 512GB SSD PCle | RTX 2070 8GB Super + UHD Graphics 630 | 17.3 FHD 240Hz | Per key RGB | Win10\n" +
//                "MSI Modern 14 A10M-692VN : I5-10210U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 14.0 FHD IPS | Win10 \n" +
//                "MSI Prestige 15 A10SC-222VN : I7-10710U | 16GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS | Ledkey | Win10\n" +
//                "MSI Modern 14 A10RAS-1041VN : I7-10510U | 8GB RAM | 512GB SSD PCIe | Geforce MX330 2GB + UHD Graphics 630 | 14.0 FHD IPS | Win10 | Gray\n" +
//                "MSI GL65 10SDK-242VN : i7-10750H | 16GB RAM | 512GB SSD PCIe | GTX1660Ti 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | PerKey RGB | WIN10\n" +
//                "MSI Modern 15 A10M-068VN : I5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 |15.6 FHD | WIN 10 | Space Gray\n" +
//                "MSI GL65 10SER-235VN : i7-10750H | 16GB RAM | 1TB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Perkey RGB | WIN 10\n" +
//                "MSI GF63 Thin 9SC-1031VN: I7-9750H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS | Win 10 | Black\n" +
//                "MSI GF63 Thin 10SCX-292VN : i5-10300H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB Max Q + UHD Graphics 630 | 15.6 FHD IPS | Win10 | Black\n" +
//                "MSI GF75 Thin 10SCXR-248VN : I7-10750H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 17.3 FHD IPS 144Hz | Win10 | Black\n" +
//                "MSI GS66 Stealth 10SE-213VN : i7-10750H | 16GB RAM | 512GB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 240Hz | Perkey RGB | WIN 10\n" +
//                "MSI Alpha 15 A3DDK-212VN : R7-3750H | 8GB RAM | 512GB SSD PCIe | AMD RX 5500M 4GB + Vega 10 Graphics | 15.6 FHD IPS 120Hz | Win10 | Per Key RGB\n" +
//                "MSI Prestige 14 A10RB-028VN : I7-10510U | 16GB RAM | 512GB SSD PCIe | Geforce MX250 2GB + UHD Graphics 630 | 14.0 FHD IPS | Win10\n" +
//                "MSI GE75 Raider 9SF-1019VN : I7-9750H | 16GB RAM | 1TB SSD PCle | RTX 2060 6GB + UHD Graphics 630 | 17.3 FHD 240Hz | Per key RGB | Win10\n" +
//                "MSI Modern 14 A10M-1028VN : I5-10210U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 14.0 FHD IPS | Win10 | Gray\n" +
//                "MSI Modern 14 A10M-693VN : I7-10510U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 14.0 FHD IPS | Win10 \n" +
//                "MSI GF75 Thin 10SCXR-038VN : I7-10750H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 17.3 FHD IPS 120Hz | Win10 | Black\n" +
//                "MSI GF65 Thin 10SER-622VN : i7-10750H | 8GB RAM | 512GB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Win10 | Black\n" +
//                "MSI GF63 Thin 10SCXR-074VN : i7-10750H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB Max Q + UHD Graphics 630 | 15.6 FHD IPS | Win10 | Black\n" +
//                "MSI Prestige 15 A10SC-402VN : I7-10710U | 32GB RAM | 1TB SSD PCIe | GTX 1650 4GB MAX-Q + UHD Graphics 630 | 15.6 UHD 4K | Ledkey | Win10\n" +
//                "Laptop Apple Macbook Pro MV912 SA/A 512Gb (2019) (Space Gray)- Touch Bar\n" +
//                "Laptop Apple Macbook Pro MV932 512Gb (2019) (Silver)- Touch Bar\n" +
//                "Laptop Acer Nitro series AN515 55 58A7 NH.Q7RSV.002(Core i5-10300H/8Gb/512Gb SSD/15.6 FHD/GTX1650 4GB/Win10/Black) - NEW 2020\n" +
//                "Laptop Acer Aspire A315 56 59XY NX.HS5SV.003 (I5-1035G1/ 4Gb/256Gb SSD/ 15.6/VGA ON/ Win10/Black)\n" +
//                "Laptop Acer Aspire A315 54K 36X5 NX.HEESV.00J (i3-8130U/4Gb/256Gb SSD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
//                "Laptop Acer Nitro series AN515-54-71HS NH.Q59SV.018 (Core i7-9750H/8Gb/256Gb SSD/15.6' FHD/GTX1650-4Gb/Win10/Black)\n" +
//                "Laptop Acer Swift 3 SF314 58 39BZ NX.HPMSV.007 Core i3 10110U/8Gb/ 512Gb SSD/ 14.0'FHD/ VGA ON/Win10/Silver/Vỏ nhôm.\n" +
//                "Laptop Acer Aspire A514 53 50P9 NX.HUSSV.004 (I5 1035G1/ 8Gb/512Gb SSD/ 14.0 FHD/VGA ON/Win10/Silver/vỏ nhôm)\n" +
//                "Laptop Acer Swift 3 SF314 57 54B2 NX.HJKSV.001(I5-1035G1/ 8Gb/ 512Gb SSD/ 14.0 FHD/VGA ON/ Win10/Millennial Pink/Vỏ nhôm)\n" +
//                "Laptop Acer Aspire A514-53 346U NX.HUSSV.005 (Core i3-1005G1/4Gb/512Gb SSD/ 14.0 FHD/VGA ON/Win10/Silver)\n" +
//                "Laptop Acer Swift 3 SF314 42 R5Z6 NX.HSESV.001 (Ryzen5 4500U/8Gb/512Gb SSD/ 14.0 FHD/ AMD Radeon Vega 3/ Win10/Silver/nhôm)\n" +
//                "Laptop Acer Swift 3 SF314 57 52GB NX.HJFSV.001(I5-1035G1/ 8Gb/ 512Gb SSD/ 15.6 FHD/VGA ON/ Win10/Grey/Vỏ nhôm)\n" +
//                "Laptop Acer Gaming Predator Triton 500 PT515-52-75FR NH.Q6YSV.002 (Core i7-10875H/32Gb/512Gb SSD/15.6''FHD-144Hz/RTX2070-8Gb/Win10/Black)\n" +
//                "Laptop Acer Nitro series AN515 55 73VQ NH.Q7RSV.001(Core i7-10750H/8Gb/512Gb SSD/15.6 FHD/GTX1650 6Gb/Win10/Black) - NEW 2020\n" +
//                "Laptop Acer Nitro series AAN515 54 595D NH.Q59SV.025 (Core i5-9300H/8Gb/512Gb SSD/15.6' FHD/GTX1650-4Gb/Win10/Black)\n" +
//                "Laptop Acer Swift 3 SF314 56 50AZ NX.H4CSV.008 (Core i5-8265U/8Gb/256Gb SSD/14.0FHD/VGA ON/Win10/Silver/Vỏ nhôm)\n" +
//                "Laptop Acer Swift 3 SF314 41 R8G9 NX.HFDSV.003 (Ryzen7-3700U/8Gb/512Gb SSD/ 14.0 FHD/ AMD Radeon Vega 3/ Win10/Silver/nhôm)\n" +
//                "Laptop Acer Aspire A315 54 368N NX.HM2SV.004 (i3-10110U/8Gb/512Gb SSD/ 15.6 FHD/VGA ON/Win10/Black)\n" +
//                "Laptop Acer Swift 5 SF514-53T-58PN NX.H7HSV.001  (Core i5-8265U/8Gb/ 256Gb SSD/14.0'FHD/Touch/VGA ON/Win10/ Xanh/vỏ nhôm)\n" +
//                "Laptop Acer Aspire A514-51-525E NX.H6VSV.002 (Core i5-8265U/4Gb/1Tb HDD/ 14.0' FHD/VGA ON/ DOS/Silver)\n" +
//                "Laptop Acer Swift 3 SF314 41 R8VS NX.HFDSV.002 (Ryzen5-3500U/4Gb/256Gb SSD/ 14.0' FHD/ AMD Radeon Vega 3/ Win10/Silver)\n" +
//                "Laptop Acer Swift 3 SF314 57G 53T1 NX.HJESV.001 (I5-1035G1/ 8Gb/ 512Gb SSD/ 14.0' FHD/MX250-2GB/ Win10/Grey/Vỏ nhôm)\n" +
//                "Laptop Acer Nitro series AN515 55 70AX NH.Q7NSV.001 (Core i7-10750H/8Gb/512Gb SSD/15.6 FHD/GTX1650TI 6Gb/Win10/Black) - NEW 2020\n" +
//                "Laptop Acer Aspire A515-53-330E NX.H6CSV.001 (Core i3-8145U/4Gb/1Tb HDD/15.6' FHD/VGA ON/DOS/Silver)\n" +
//                "Laptop Acer Aspire A515 54G 56JG NX.HVGSV.002 (I5 1035G1/ 8Gb/512Gb SSD/ 15.6 FHD/MX350-2Gb/Win10/Silver)\n" +
//                "Laptop Acer Aspire A514-52-516K NX.HMHSV.002 (I5-10210U/4Gb/256Gb SSD/ 14.0' FHD/VGA ON/Win10/Silver)\n" +
//                "Laptop Acer Aspire 7 A715 41G R8KQ NH.Q8DSV.001 (Ryzen 5 3550H/ 8Gb/256Gb SSD/ 15.6 FHD/ Nvidia GTX1650 4Gb DDR6/ Win10/Black)\n" +
//                "Laptop Acer Aspire A515-53-3153 NX.H6BSV.005 (Core i3-8145U/4Gb/1Tb HDD/ 15.6' FHD/VGA ON/Win10/Silver)\n" +
//                "Laptop Acer Swift 3 SF314 58 55RJ NX.HPMSV.006 Core i5 10210U/8Gb/ 512Gb SSD/ 14.0'FHD/ VGA ON/Win10/Silver/Vỏ nhôm.\n" +
//                "Laptop Acer Swift 3 SF314 56G 78QS NX.HAQSV.001 (Core i7-8565U/8Gb/512Gb SSD/14.0 FHD/MX250-2Gb/Win10/Silver/Vỏ nhôm)\n" +
//                "Laptop Acer Aspire A515 55 55HG NX.HSMSV.004 (I5 1035G1/ 8Gb/512Gb SSD/ 15.6 FHD/VGA ON/Win10/Silver)\n" +
//                "Laptop Acer Nitro series AN515-54-779S NH.Q5BSV.009(Core i7-9750H/8Gb/512Gb SSD/15.6 FHD/GTX1660TI 6Gb/Win10/Black)\n" +
//                "Laptop Acer Gaming Predator Triton 500 PT515-52-78PN NH.Q6XSV.001 (Core i7-10875H/32Gb/1Tb SSD/15.6''FHD-300Hz/RTX2070-8Gb/Win10/Black)\n" +
//                "Laptop Acer Swift 3 SF314-54-58KB NX.GXZSV.002 (Core i5-8250U/4Gb/256Gb SSD/14.0' FHD/VGA ON/Windows 10/Silver)\n" +
//                "Laptop Acer Nitro series AN515 55 5304 NH.Q7NSV.002 (Core i5-10300H/8Gb/512Gb SSD/15.6 FHD/GTX1650TI-4GB/Win10/Black) - NEW 2020\n" +
//                "Laptop Acer Nitro series AN515 43 R9FD NH.Q6ZSV.003 (Ryzen5-3550H/8Gb/512Gb SSD/15.6FHD/GTX1650-4Gb/Win10/Black)\n" +
//                "Laptop Acer Aspire A514 53 50JA NX.HUSSV.002 (I5 1035G1/ 4Gb/256Gb SSD/ 14.0 FHD/VGA ON/Win10/Silver/vỏ nhôm)\n" +
//                "Laptop Microsoft Laptop 3 i7/512Gb (Black)- Cảm biến ánh sáng\n" +
                "Laptop Microsoft Laptop 3 i5/256Gb (Black)- Cảm biến ánh sáng\n" +
                "Laptop Asus Zenbook UX334FAC-A4059T (i5-10210U/8GB/512Gb SSD/13.3FHD/VGA ON/Win10/Blue/SCR_PAD/Túi)\n" +
                "MSI Bravo 15 A4DCR-052VN : AMD Ryzen 5-4600H | 8GB RAM | 256GB SSD PCIe | AMD RX5300M 3GB | 15.6 FHD IPS | Win10\n" +
                "MSI GS65 Stealth 9SE-1000VN : I7-9750H | 16GB RAM | 512GB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD 240Hz | Win 10 | RGB\n" +
                "MSI GF63 Thin 10SCSR-076VN : i5-9300H | 8GB RAM | 512GB SSD PCIe | GTX 1650Ti 4GB Max Q + UHD Graphics 630 | 15.6 FHD IPS 120Hz | Win10 | Black\n" +
                "MSI GF65 Thin 10SDR-623VN : i5-10300H | 8GB RAM | 512GB SSD PCIe | GTX 1660Ti 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Win10 | Black\n" +
                "MSI GF75 Thin 10SCXR-208VN : I7-10750H | 8GB RAM | 512GB SSD PCIe | GTX 1650Ti 4GB + UHD Graphics 630 | 17.3 FHD IPS 144Hz | Win10 | Black\n" +
                "MSI GF63 Thin 9SC-1030VN : I5-9300H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS | Win10 \n" +
                "MSI GF75 Thin 9SC-477VN : I7-9750H | 8GB RAM | 512GB SSD PCIe | GTX 1650 4GB + UHD Graphics 630 | 17.3 FHD IPS 120Hz | Win10 | Black\n" +
                "MSI GF63 Thin 10SCSR-077VN : i7-10750H | 8GB RAM | 512GB SSD PCIe | GTX 1650Ti 4GB Max Q + UHD Graphics 630 | 15.6 FHD IPS 120Hz | Win10 | Black\n" +
                "GT76 Titan DT 9SG-097VN : i9-9900K | 64GB RAM | 1TB SSD PCle ( Super Raid 4 ) + 1TB HDD | RTX 2080 8GB + UHD Graphics 630 | 17.3 FHD 240Hz | Win 10 | RGB\n" +
                "MSI Modern 14 A10M-1053VN : I5-10210U | 4GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 14.0 FHD IPS | Win10 | Silver\n" +
                "Laptop Asus TUF Gaming FA506IV-HN202T (Ryzen 7 4800H/16GB/1TB SSD/15.6FHD-144Hz/GTX2060 TI 6GB/Win10/Grey/RGB_KB)\n" +
                "Laptop Asus A512FA-EJ1281T (i5-10210U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus Vivobook S533FA-BQ011T (i5-10210U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Black)\n" +
                "Laptop Asus Zenbook Flip UX362FA-EL206T Xanh (i7-8565U/16GB/512GB SSD/13.3FHD Touch/VGA ON/Win10/Blue/Túi/Bút)\n" +
                "Laptop Fujitsu U937-FPC04866DK Core i5 7200U 2.5Ghz-3Mb/ 8Gb/ 256Gb SSD/ 13.3Inch FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Fujitsu LIFEBOOK U749,i5-8265U(1.6GHz/6MB),8GB DDR4,512GB SSD M.2 SATA,14 FHD,FPR,4Cell 50WH,No OS,1Y WTY_L00U749VN00000113 \n" +
                "Laptop Asus Vivobook A412DA-EK346T (Ryzen 3-3200U/4GB/512GB SSD/14FHD/AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus Zenbook Duo UX481FL-BM048T (i5-10210U/8GB/512GB SSD/14FHD/MX250 2GB/Win10/ Blue/SCR_PAD/Pen/Túi)\n" +
                "Laptop Asus Gaming FA706II-H7125T (Ryzen 5-4600H/8GB/512GB SSD/17.3FHD, 120Hz/GTX1650 TI 4GB/Win10/Gun Metal/Balo)\n" +
                "Laptop Asus S431FA-EB077T (I7-8565U/8GB/512GB SSD/14FHD/VGA ON/WIN10/Silver)\n" +
                "Laptop Asus Gaming FX505DU-AL070T (Ryzen 7-3750H/8GB/512GB SSD/15.6FHD/GTX1660 TI 6GB/Win10/Gun Metal)\n" +
                "Laptop Asus Zenbook UX434FAC-A6116T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Silver/ScreenPad)\n" +
                "Laptop Asus Vivobook S533JQ-BQ024T (i7-1065G7/16GB/512GB SSD/15.6FHD/Win10/MX350 2GB/White)\n" +
                "Laptop Fujitsu U747-FPC07427DK Core i5 7200U 2.5Ghz-3Mb/ 8Gb/ 256Gb SSD/ 14.0' FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Fujitsu LIFEBOOK E549,i7-8565U(1.8GHz/8MB),8GB DDR4,512GB SSD M.2 SATA,14 HD,FPR,4Cell 50WH,No OS,1Y WTY_L00E549VN00000111\n" +
                "Laptop Fujitsu LIFEBOOK E559,i5-8265U(1.6GHz/6MB),4GB DDR4,256GB SSD M.2 SATA,15.6 HD,4Cell 50WH,No OS,1Y WTY_L00E559VN00000074 \n" +
                "Laptop Fujitsu LIFEBOOK E549,i5-8265U(1.6GHz/6MB),4GB DDR4,256GB SSD M.2 SATA,14 HD,4Cell 50WH,No OS,1Y WTY_L00E549VN00000110\n" +
                "Laptop Asus UX333FA-A4115T (i7-8565U/8GB/512GB SSD/13.3FHD/VGA ON/Win10/NumPad/Silver)\n" +
                "Laptop Asus Vivobook X509FJ-EJ155T (i5-8265U/4GB/512GB SSD/15.6FHD/MX230 2GB5/Win10/Silver)\n" +
                "Laptop Asus UX333FN-A4124T (i5-8265U/8GB/512GB SSD/13.3FHD/MX150 2GB/Win10/NumPad/Blue)\n" +
                "Laptop Asus Vivobook X509JP-EJ013T (i5-1035G1/4GB/512GB SSD/15.6FHD/MX330 2GB/Win10/Finger Print/Silver)\n" +
                "Laptop Asus Zenbook UX434FLC-A6143T (i5-10210U/8GB/512GB SSD/14FHD/MX250 2GB/Win10/Blue/ScreenPad)\n" +
                "Laptop Asus TUF Gaming FA506II-AL016T (Ryzen 7 4800H/8GB/512GB SSD/15.6FHD-144Hz/GTX1650 TI 4GB/Win10/Grey/RGB_KB)\n" +
                "Laptop Asus Vivobook S533JQ-BQ015T (i5-10210U/8GB/512GB SSD/15.6FHD/MX350 2GB DDR5/Win10/White)\n" +
                "Laptop Asus Gaming FX505DD-AL186T (Ryzen 5 3550H/8GB/512GB SSD/15.6FHD/GTX1050 3GB/Win10/Gun Metal)\n" +
                "Laptop Dell Latitude 5400 42LT540001 (Core i5 8265U/ 4Gb/ 500Gb HDD/ 14.0 FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Inspiron 3480L P89G003 (Core i5-8265U/4Gb/1Tb HDD/Radeon R5 520-2Gb/ 14.0' FHD/Win10/Silver)\n" +
                "Laptop Dell Inspiron 3493A P89G007N93A (I5-1035G1/4Gb/1Tb HDD/ 14.0FHD/MX230 2Gb/Win10/Silver)\n" +
                "Laptop Asus Vivobook S433FA-EB053T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Black/NumPad)\n" +
                "Laptop Dell Latitude 5400 L5400I714WP Core i5 8665U 1.9Ghz up to 4.8Ghz-8Mb/ 8Gb/ 256Gb SSD/ 14.0' FHD/VGA ON/ Windows 10 Pro/Black)\n" +
                "Laptop Dell Vostro 5581-70194501 (Core i5-8265U/4Gb/1Tb HDD/15.6' FHD/VGA ON/Win10/Urban Grey/vỏ nhôm)\n" +
                "Laptop Asus Gaming G531G_N-VAZ160T (Core i7-9750H/16GB/512GB SSD/15.6FHD/GTX2060 6Gb DDR6/ Win10/Black/Balo/Chuột)\n" +
                "Laptop Dell Latitude 3400 70188730 (Core i3-8145U/8Gb/256Gb SSD/ 14.0/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Latitude 7400 42LT740001 (Core i5-8365U/ 8Gb/ 256Gb SSD/ 14.0 FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Latitude 3400 L3400I5HDD (Core i5-8265U/4Gb bus 2400/HDD 1Tb/14.0'/VGA ON/Dos/Black)\n" +
                "Laptop Dell Latitude 5400 42LT540003 (Core i5 8265U/ 4Gb/ 1Tb HDD/ 14.0 FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Latitude 5400 L5400I714DF Core i7 8665U 1.9Ghz up to 4.8Ghz-8Mb/ 8Gb/ 256Gb SSD/ 14.0' FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Zenbook Flip UM462DA-AI091T (Ryzen 5-3500U/8GB/512GB SSD/14FHD Touch/AMD Radeon/Win10/Grey/Túi/Pen)\n" +
                "Laptop Asus Vivobook X409FA-EK199T (i5-8265U/4GB/1TB HDD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus UX461UA-E1147T (i5-8250U/4GB/256Gb SSD/14FHD Touch/VGA ON/Win10/Gold/Pen)\n" +
                "Laptop Asus Vivobook X509FA-EJ199T (i3-8145U/4GB/1TB HDD/15.6FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus Vivobook X509JA-EJ020T (i5-1035G1/4GB/1TB HDD/15.6 FHD/VGA ON/Win10/Finger Print/Silver)\n" +
                "Laptop Asus A512DA-EJ829T (Ryzen 3-3200U/4GB/512GB SSD/15.6FHD/AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus Vivobook P4103FA-EB226T (i5-8265U/8GB/512GB SSD/14.0FHD/VGA ON/WIN10/Silver)\n" +
                "Laptop Asus Vivobook S531FA-BQ104T (i5-8265U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Dell Inspiron 5491 C1JW81 (I7-10510U/ 8Gb/512Gb SSD/ 14.0'' FHD/Touch/MX230 2GB/ Win10/Silver/vỏ nhôm)\n" +
                "Laptop Dell Latitude 3400 3400I5HDD8G (Core i5-8265U/8Gb/HDD 1Tb/14.0'/VGA ON/Dos/Black)\n" +
                "Laptop Dell XPS 15-7590 70196707 (Core i7-9750H/16Gb/512Gb SSD/15.6' FHD/GTX1650 4Gb/ Win10+Offi365/Silver/Vỏ nhôm)\n" +
                "Laptop Dell Inspiron 3493 N4I5136W (I5-1035G1/ 4Gb/1Tb HDD/ 14.0' FHD/VGA ON/ Win10/Black)\n" +
                "Laptop Asus Gaming G731-VEV089T (i7-9750H/16GB/512GB SSD/17.3FHD/RTX2060 6Gb DDR6/Win10/Black/Balo)\n" +
                "Laptop Asus Zenbook UX434FL-A6212T (i5-10210U/8GB/512GB SSD/14FHD/MX250 2GB/Win10/Silver/ScreenPad)\n" +
                "Laptop Asus UX333FA-A4118T (i5-8265U/8GB/512GB SSD/13.3FHD/VGA ON/ WIN 10/Num_Pad/Blue/Túi)\n" +
                "Laptop Asus Gaming FA706IU-H7133T (Ryzen 7-4800H/8GB/512GB SSD/17.3FHD, 120Hz/GTX1660 TI 6GB/Win10/Gun Metal/Balo)\n" +
                "Laptop Asus Vivobook A412FA-EK155T (i3-8145U/4GB/1TB HDD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus Zenbook UX434FLC-A6173T (i7-10510U/8GB/512GB SSD/14FHD/MX250 2GB/Win10/Blue/ScreenPad)\n" +
                "Laptop Asus Vivobook S530FA-BQ431T (i3-8145U/4GB/256GB SSD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop Asus X509FJ-EJ153T (i5-8265U/4GB/1TB HDD/15.6FHD/MX230 2GB5/Win10/Silver)\n" +
                "Laptop Asus Vivobook S531FA-BQ105T (i5-8265U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Xanh coban)\n" +
                "Laptop Asus D409DA-EK093T (Ryzen 5-3500U/4Gb/1TB HDD/14FHD/ AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus TUF Gaming FA506IU-AL127T (Ryzen 7 4800H/8GB/512GB SSD/15.6FHD-144Hz/GTX1660 TI 6GB/Win10/Black/RGB_KB)\n" +
                "Laptop Asus Vivobook S530FN-BQ128T (Gold)- Siêu mỏng, FingerPrint\n" +
                "Laptop Asus Vivobook S533FA-BQ025T (i5-10210U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Green)\n" +
                "Laptop Asus Vivobook M533IA-BQ164T (Ryzen 7-4700U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/White)\n" +
                "Laptop Asus Vivobook X409FA-EK098T (i3-8145U/4GB/1TB HDD/14FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Asus Zenbook UX534FTC-A9169T (i5-10210U/8GB/512GB SSD/15.6FHD/GTX1650 4GD5/Win10/Silver/SCR_PAD/Túi)\n" +
                "Laptop Asus Vivobook A412FA-EK156T (i3-8145U/4GB/HDD 1TB/14FHD/VGA ON/Win10/Blue)\n" +
                "Laptop Asus Zenbook Duo UX481FL-BM049T (i7-10510U/16GB/512GB SSD/14FHD/MX250 2GB/Win10/ Blue/SCR_PAD/Pen/Túi)\n" +
                "Laptop Asus Gaming G531GT-AL007T (i5-9300H/8GB/512GB SSD/15.6FHD/GTX1650 4GB/Win10/Black)\n" +
                "Laptop Asus Zenbook UX534FTC-AA189T (i7-10510U/16GB/1TB SSD/15.6FHD/GTX1650 Max Q 4GB/Win10/Blue/ScreenPad)\n" +
                "Laptop Asus Vivobook S433FA-EB054T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Red/NumPad)\n" +
                "Laptop Asus Gaming ROG Strix SCAR G532L-VAZ044T (i7-10875H/16GB/1TB SSD/15.6FHD, 240Hz/RTX2060 6GB DDR6/Win10/Black/NumPad/Balo/Chuột)\n" +
                "Laptop MSI Gaming GF75 Thin 10SCSR 208VN (i7-10750H/8GB/512GB SSD/17.3FHD, 144Hz/GTX1650 TI 4GB DDR6/Win10/Black)\n" +
                "Laptop MSI Gaming GF63 Thin 9SCSR 076VN (I5-9300H/8GB/512GB SSD/15.6FHD, 120Ghz/NVIDIA GTX1650 TI MAX Q 4GB/Win 10/Black)\n" +
                "Laptop MSI GE75 Raider 9SE 688VN (i7 9750H/16GB/1Tb SSD/17.3FHD/RTX2060 6Gb DDR6/Win10/Black)/Balo)\n" +
                "Laptop MSI GF75 Thin 8SC 025VN (i7-8750H/8GB/256GB SSD/17.3FHD/GTX1650 4GB/Win10/Black)\n" +
                "Laptop MSI Gaming GL65 Leopard 10SEK 235VN (I7 10750H/16GB/1TB SSD/15.6FHD, 144Hz/RTX2060 6GB DDR6/Win 10/Black/Balo)\n" +
                "Laptop MSI GF63 Thin 10SCXR 292VN (i5-10300H/8GB/512GB SSD/15.6FHD, 60Hz/GTX1650 Max Q 4GB DDR6/Win10/Black)\n" +
                "Laptop Asus Gaming G731GT-H7114T (i7-9750H/8GB/500GB SSD/17.3FHD/GTX1650 4GB/Win10/Black)\n" +
                "Laptop Asus Vivobook A412FA-EK223T (i3-8145U/4GB/512GB SSD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus TUF Gaming FA506IH-AL018T (Ryzen 5 4600H/8GB/512GB SSD/15.6FHD-144Hz/GTX1650 4GB DDR5/Win10/Black/RGB_KB)\n" +
                "Laptop Asus Gaming ROG Strix G712L-UEV075T (i7-10750H/16GB/512GB SSD/17.3FHD, 144Hz/GTX1660 TI 6GB DDR6/Win10/Black/Balo)\n" +
                "Laptop Asus Zenbook UX434FAC-A6064T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Blue/ScreenPad)\n" +
                "Laptop Asus Expertbook B9450FA-BM0324T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Black/NumberPad/Pin 24h/Siêu nhẹ/Túi)\n" +
                "Laptop MSI GF63 Thin 9SC 071VN (i5-9300H/8GB/256GB SSD/15.6FHD/GTX1650 MAX Q 4GB/Win10/Black)\n" +
                "Laptop MSI GF63 Thin 9RCX 645VN (i7-9750H/8GB/512GB SSD/15.6FHD/GTX1050 TI 4GB/Win10/Black)\n" +
                "Laptop MSI Gaming GF75 Thin 9RCX-432VN (i5-9300H/8GB/256GB SSD/17.3FHD/GTX1050 TI 4GB/Win10/Black)\n" +
                "Laptop MSI Gaming Prestige 15 A10SC 222VN (I7-10710U/16GB/512GB SSD/15.6 FHD/GTX 1650 Max-Q 4GB GDDR5/ Win10/4cell/Grey/Túi Sleeve)\n" +
                "Laptop MSI Gaming GF63 Thin 9SC 400VN (I5-9300H/8GB/256GB SSD/15.6FHD/NVIDIA GTX1650 MAX Q 4GB/Win 10/Black)\n" +
                "Laptop Asus Expertbook B9450FA-BM0616R (i7-10510U/16GB/1TB SSD/14FHD/VGA ON/Win10Pro/Black/NumberPad/Pin 24h/Siêu nhẹ/Túi)\n" +
                "Laptop MSI GE63 8SE 280VN Raider (i7-8750H/16GB/1Tb HDD+256Gb SSD/15.6FHD/RTX2060 6Gb DDR6/Win10/Black)\n" +
                "Laptop Asus Gaming FX705DT-H7138T (Ryzen 7-3750H/8GB/512GB SSD/17.3FHD/GTX1650 4GB/Win10/Gun Metal/Balo)\n" +
                "Laptop MSI GF63 Thin 9RCX 646VN (i5-9300H/8GB/512GB SSD/15.6FHD/GTX1050 TI 4GB/Win10/Black)\n" +
                "Laptop MSI GE66 Raider 10SF 044VN (I7-10750H/16GB/1TB SSD/15.6FHD, 240Hz /RTX2070 8GB DDR6/Win10/Black/Balo)\n" +
                "Laptop Asus A512FL-EJ165T (i7-8565U/8GB/1TB HDD/15.6FHD/MX250 2GB/Win10/Silver)\n" +
                "Laptop MSI Gaming GF63 Thin 9SCXR 075VN (I5-9300H/8GB/512GB SSD/15.6FHD-60Hz/GTX1650 MAX Q 4GB/Win 10/Black)\n" +
                "Laptop MSI GF63 8RD 242VN (i5-8300H/8GB/1TB HDD/15.6FHD/GTX1050 TI 4GB/Win10/Black)\n" +
                "Laptop MSI GT75 Titan 8RF 231VN (i7-8750H/32GB/1TB HDD+128Gb SSD/17.3FHD/GTX1070 8GB/Win10/Black/Balo)\n" +
                "Laptop MSI P65 8RF 488VN (i7-8750U/16GB/512GB SSD/15.6FHD/GTX1070 8GB/Win10/Silver)\n" +
                "Laptop Asus Gaming ROG Strix G712L-VEV055T (i7-10750H/16GB/512GB SSD/17.3FHD, 144Hz/RTX2060 6GB DDR6/Win10/Black/Balo)\n" +
                "Laptop Asus Vivobook M533IA-BQ162T (Ryzen 5-4500U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/Black)\n" +
                "Laptop Asus Vivobook M533IA-BQ165T (Ryzen 7-4700U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/White)\n" +
                "Laptop Asus Vivobook Flip TP412FA-EC123T (i5-8265U/4GB/512GB SSD/14FHD Touch/VGA ON/Win10/Blue)\n" +
                "Laptop Asus Vivobook M533IA-BQ132T (Ryzen 5-4500U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/White)\n" +
                "Laptop Asus D409DA-EK151T (Ryzen 3-3200U/4Gb/256GB SSD/14FHD/ AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus Vivobook A412DA-EK144T (Ryzen 5-3500U/8GB/512GB SSD/14FHD/AMD Radeon/Win10/Silver)\n" +
                "Laptop AsusPro P1440FA-FA0420T (i3-8145U/4GB/256GB SSD/14FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Asus Vivobook A412FA-EK1187T (i3-10110U/4GB/256GB SSD/14FHD/VGA ON/Win10/Blue)\n" +
                "Laptop Asus A512DA-EJ406T (Ryzen 5-3500U/8GB/512GB SSD/15.6FHD/AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus TUF Gaming FA506II-AL012T (Ryzen 5 4600H/8GB/512GB SSD/15.6FHD-144Hz/GTX1650 TI 4GB/Win10/Black/RGB_KB)\n" +
                "Laptop Asus Vivobook A412FA-EK734T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus UX333FN-A4125T (Silver)- NumPad\n" +
                "Laptop Asus Zenbook Flip UX362FA-EL205T Xanh (i5-8265U/8GB/512GB SSD/13.3FHD/VGA ON/Win10/Blue/Túi/Bút)\n" +
                "Laptop Asus Vivobook S533FA-BQ026T (i5-10210U/8GB/512GB SSD/15.6FHD/VGA ON/Win10/White)\n" +
                "Laptop Asus Vivobook S333JA-EG003T (i5-1035G1/8GB/512GB SSD/13.3FHD/VGA ON/Win10/White/NumPad)\n" +
                "Laptop Asus S530FA-BQ185T (i3-8145U/4GB/1TB HDD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop Asus Vivobook S433FA-EB052T (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/Win10/White/NumPad)\n" +
                "Laptop Asus Vivobook X509JA-EJ021T (i5-1035G1/4GB/512GB SSD/15.6 FHD/VGA ON/Win10/Finger Print/Silver)\n" +
                "Laptop Asus Vivobook X509JA-EJ427T (i3-1005G1/4GB/512GB SSD/15.6FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus D509DA-EJ286T (Ryzen 5-3500U/4GB/256GB SSD/15.6FHD/AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus Vivobook S433FA-EB437T (i7-10510U/16GB/512GB SSD/14FHD/VGA ON/Win10/White/NumPad)\n" +
                "Laptop Asus Vivobook X409JA-EK199T (i5-1035G1/4GB/512GB SSD/14FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Asus Vivobook X409FA-EK469T (i3-8145U/4GB/256GB SSD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Asus Gaming FX505DT-AL003T (Ryzen 7-3750H/8GB/512GB SSD/15.6FHD/GTX1650 4GB/Win10/Gun Metal)\n" +
                "Laptop Asus A512DA-EJ422T (Ryzen 5-3500U/8GB/512GB SSD/15.6FHD/AMD Radeon/Win10/Grey)\n" +
                "Laptop Asus Vivobook S530FA-BQ186T (i3-8145U/4GB/1TB HDD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Asus D409DA-EK152T (Ryzen 5-3500U/4Gb/256GB SSD/14FHD/ AMD Radeon/Win10/Silver)\n" +
                "Laptop Asus Vivobook A412FA-EK287T (i3-8145U/4GB/512GB SSD/14FHD/VGA ON/Win10/Blue)\n" +
                "Laptop Asus Gaming FX505DT-AL118T (Ryzen 5 3550H/8GB/512GB SSD/15.6FHD-120Hz/GTX1650 4GB/Win10/Gun Metal)\n" +
                "Laptop HP Pavilion 15-cs3014TU 8QP20PA (i5-1035G1/4Gb/256GB SSD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP 15s-du1040TX 8RE77PA (i7-10510U/8GB/512GB SSD/15.6/MX130 2GB/Win 10/Silver)\n" +
                "Laptop HP 348 G7 9PG98PA (i5-10210U/8Gb/SSD 256Gb/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP 15s-fq1021TU 8VY74PA (i5-1035G1/8Gb/512GB SSD/15.6/VGA ON/Win 10/Silver)\n" +
                "Laptop HP Pavilion 15-cs3011TU 8QN96PA (i5-1035G1/8Gb/512GB SSD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop HP Pavilion 14-ce3013TU 8QN72PA (i3-1005G1/4Gb/256GB SSD/14FHD/VGA ON/Win10/Silver)\n" +
                "Laptop HP Pavilion 15-cs3116TX 9AV24PA (i5-1035G1/4Gb/256GB SSD/15.6FHD/MX250 2GB/Win10/Gold)\n" +
                "Laptop HP Envy 13-ba0046TU 171M7PA (i5-1035G4/8Gb/512GB SSD/13.3FHD/VGA ON/Win10+Office Home vàamp; Student/Gold)\n" +
                "Laptop HP 15s-fq1022TU 8VY75PA (i7-1065G7/8Gb/512GB SSD/15.6FHD/VGA ON/Win 10/Silver)\n" +
                "Laptop HP 348 G7 9PG85PA (i3-10110U/4GB/256GB SSD/14/VGA ON/Dos/Silver)\n" +
                "Laptop HP Pavilion 14-ce3026TU 8WH93PA (i5-1035G1/8Gb/512GB SSD/14FHD/ VGA ON/Win10/Gold)\n" +
                "Laptop HP Pavilion 14-ce2038TU 6YZ21PA (i5-8265U/4Gb/1Tb HDD/14FHD/VGA ON/Win10/Pink)\n" +
                "Laptop HP Pavilion 15-cs3061TX 8RE83PA (i5-1035G1/8Gb/512GB SSD/15.6FHD/MX250 2GB/Win10/Grey)\n" +
                "Laptop HP 15s-fq1106TU 193Q2PA (i3-1005G1/4GB/256GB SSD/15.6/VGA ON/DOS/Silver)\n" +
                "Laptop HP ProBook 450 G7 9GQ30PA (i7-10510U/8GB/512GB SSD/15.6FHD/VGA ON/DOS/Silver/LEB_KB)\n" +
                "Laptop HP 250 G7 15H25PA (i3-8130U/4GB/256GB SSD/15.6/VGA ON/DOS/Grey)\n" +
                "Laptop HP 15s-du1037TX 8RK37PA (i5-10210U/8Gb/512GB SSD/15.6/MX130 2GB/Win 10/Silver)\n" +
                "Laptop HP Envy 13-aq0025TU 6ZF33PA (i5-8265U/8Gb/128Gb SSD/13.3FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP Pavilion 15-cs2032TU 6YZ04PA (i3-8145U/4Gb/1TB HDD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop HP Envy 13-aq1023TU 8QN84PA (i7-10510U/8Gb/512Gb SSD/13.3FHD/VGA ON/Win10/Gold/LED_KB)\n" +
                "Laptop HP Pavilion 15-cs3063TX 8RK42PA (i7-1065G7/8Gb/512GB SSD/15.6FHD/MX250 2GB/Win10/Gold)\n" +
                "Laptop HP Pavilion 15-cs3015TU 8QP15PA (i5-1035G1/4Gb/256GB SSD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop HP Pavilion 15-cs3010TU 8QN78PA (i3-1005G1/4Gb/256GB SSD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop HP 15s-fq1017TU 8VY69PA (i5-1035G1/4GB/512GB SSD/15.6/VGA ON/Win 10/Silver)\n" +
                "Laptop HP 240 G7 3K075PA (i3-8130U/4Gb/256GB SSD/14/VGA ON/WIN10/Grey)\n" +
                "Laptop HP Pavilion 14-ce3014TU 8QP03PA (i3-1005G1/4Gb/256GB SSD/14FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP Envy 13-aq1022TU 8QN69PA (i5-10210U/8Gb/512Gb SSD/13.3FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP 14s-dk0117AU 8TS51PA (Ryzen 3-3200U/4GB/256GB SSD/14 HD/AMD Radeon/Win10/Silver)\n" +
                "Laptop HP Pavilion 14-ce3029TU 8WH94PA (i5-1035G1/8Gb/512GB SSD/14FHD/VGA ON/Win10/Pink)\n" +
                "Laptop HP Pavilion 15-cs3008TU 8QP02PA (i3-1005G1/4Gb/256GB SSD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP 14s-dq1022TU 8QN41PA (i7-1065G7/8Gb/512GB SSD/14/VGA ON/Win 10/Silver)\n" +
                "Laptop HP 15s-fq1107TU 193Q3PA (i3-1005G1/4GB/256GB SSD/15.6/VGA ON/Win10/Silver)\n" +
                "Laptop HP Pavilion 15-cs3060TX 8RJ61PA (i5-1035G1/8Gb/512GB SSD/15.6FHD/MX250 2GB/Win10/Gold)\n" +
                "Laptop HP Pavilion Gaming 15-dk0233TX 8DS86PA (i7-9750H/8Gb/512Gb SSD/15.6FHD/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop HP ProBook 450 G6 5YM80PA (i5-8265U/8Gb/1Tb HDD/ 15.6/VGA ON/ Dos/Silver)\n" +
                "Laptop HP Pavilion 14-e2036TU 6YZ19PA (i3-8145U/4Gb/500Gb HDD/14FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP 348 G7 9PG79PA (i3-8130U/4Gb/256Gb SSD/14/VGA ON/Dos/Silver)\n" +
                "Laptop HP Envy 13-aq1057TX 8XS68PA (i7-10510U/8GB/512GB SSD/13.3FHD/Nvidia MX250-2GB/Win10/Vân gỗ/LED_KB)\n" +
                "Laptop HP Pavilion x360 14-dw0062TU 19D53PA (i5-1035G1/8GB/512GB SSD/14FHD TouchScreen/VGA ON/Win10+Office Home vàamp; Student/Gold/Pen)\n" +
                "Laptop HP Pavilion x360 14-dh1139TU 8QP77PA (i7-10510U/8GB/512GB SSD/14FHD TouchScreen/VGA ON/Win10/Gold)\n" +
                "Laptop HP ProBook 445R G6 9VC64PA Ryzen 5 3500U/4Gb/256GB SSD/14FHD/ AMD Radeon Graphics/ Dos/Silver)\n" +
                "Laptop HP 14s-dq1065TU 9TZ44PA (i5-1035G1/8Gb/512GB SSD/14/VGA ON/Win 10/Silver)\n" +
                "Laptop HP ProBook 440 G7 9GQ22PA (i5-10210U/4Gb/256GB SSD/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP ProBook 430 G7 9GQ10PA (i3-10110/4GB/256GB SSD/13.3HD/VGA ON/DOS/Silver/LED_KB)\n" +
                "Laptop HP ProBook 440 G7 9GQ14PA (i5-10210U/8Gb/512GB SSD/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP ProBook 445 G6 6XP98PA Ryzen 5 2500U 2.0Ghz-2Mb/4Gb/1Tb HDD/14FHD/ AMD Radeon Vega Graphics/ Dos/Silver)\n" +
                "Laptop HP ProBook 450 G7 9MV54PA (i5-10210U/4GB/512GB SSD/15.6FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP Envy x360-ar0071AU 6ZF30PA (Ryzen 5-3500U/8Gb/256Gb SSD/13.3FHD Touch/AMD Radeon/Win10/Black)\n" +
                "Laptop Dell Vostro 5490 V4I5106W (I5-10210U/ 8Gb/256Gb SSD/ 14.0'' FHD/ VGA ON/ Win10/ Urban Gray/vỏ nhôm)\n" +
                "Laptop Dell Vostro 3590 GRMGK3 (I5-10210U/ RAM 8Gb/256Gb SSD/ 15.6 FHD/ DVDW/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Vostro 5590 70197465 (I5-10210U/ 8Gb/ 256Gb SSD/ 15.6' FHD/ VGA ON/ Win10/ Urban Grey/vỏ nhôm)\n" +
                "Laptop HP 348 G7 9PH01PA (i5-10210U/8GB/512GB SSD/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP Pavilion 14-ce2037TU 6YZ13PA (i3-8145U/4Gb/1Tb HDD/14.0FHD/VGA ON/Win10/Pink)\n" +
                "Laptop Dell Vostro 3590B P75F010 (I5-10210U/ RAM 8Gb/256Gb SSD/ 15.6 FHD/ DVDW/AMD Radeon 610 2Gb/ Win10/Black)\n" +
                "Laptop Dell Vostro 3490 2N1R82 (I5-10210U/8Gb/256Gb SSD/ 14.0FHD/Radeon 610-2Gb/ Finger Print/ Win10/Black)\n" +
                "Laptop Dell Inspiron 3593D P75F013 (i5 1035G1/ Ram 4Gb/512Gb SSD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Gaming G3 3590 N5I5518W (Core i5-9300H/8Gb/512Gb SSD/15.6' FHD/GTX1650-4Gb/Win10/Black)\n" +
                "Laptop Dell Inspiron 5491 C9TI7007W (I7-10510U/ 8Gb/256Gb SSD/ 14.0' FHD/Touch/ VGA ON/ Win10/Grey/Vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5584Y P85F001 (Core i7-8565U/8Gb/1Tb HDD + SSD 128Gb/15.6' FHD/MX130 4Gb/Win10/Silver)\n" +
                "Laptop Dell Vostro 3590 V5I3101W (I3-10110U/4Gb/256Gb SSD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Inspiron 3493 N4I5122W/WA (I5-1035G1/8Gb/256Gb SSD/ 14.0 FHD/VGA ON/ Win10/Silver)\n" +
                "Laptop HP 348 G7 9PG95PA (i5-10210U/4Gb/512GB SSD/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP Pavilion Gaming 15-dk0231TX 8DS89PA (i5-9300H/8Gb/1Tb HDD/15.6FHD/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop Dell Vostro 5490B P116G001V90B (Core i5-10210U/8Gb/ SSD 256Gb/14.0''FHD/MX230-2Gb/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Dell Vostro 3490 70211829 (I3-10110U/4Gb/256Gb SSD/ 14.0FHD/VGA ON/ Finger Print/ Win10/Black)\n" +
                "Laptop Dell Inspiron 5391 70197461 (I7-10510U/ 8Gb onmain/ 512Gb SSD/ 13.3Inch FHD/ Nvidia Geforce MX250 2GB GDDR5/ Win10/Silver)\n" +
                "Laptop Dell Inspiron 7391 N3TI5008W (I5-10210U/ 8Gb/ 512Gb SSD/ 13.3Inch FHD Touch + Pen/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Inspiron 3493 N4I7131W (I7-1065G7/ 8Gb/512Gb SSD/ 14.0FHD/MX230-2Gb/ Win10/Silver)\n" +
                "Laptop Dell Inspiron 5593 7WGNV1 (I5-1035G1/8Gb/512Gb SSD/ 15.6 FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Dell Inspiron 5490 FMKJV1 (I5-10210U/ 8Gb/512Gb SSD/ 14.0'' FHD/MX230 2GB/ Win10/Silver/Vỏ nhôm)\n" +
                "Laptop Dell Inspiron 3593 70211826 (i7-1065G7/8Gb/512Gb SSD/ 15.6FHD/MX230-2GB/ Win10/Black)\n" +
                "Laptop Dell Inspiron 5593 70196703 (i3-1005G1/4Gb/128Gb SSD/ 15.6'FHD/VGA ON/ Win10/Silver)\n" +
                "Laptop Dell Vostro 5590A P88F001N90A (I7-10510U/ 8Gb/ 256Gb SSD/ 15.6 FHD/MX250 2GB/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 3593 70211828 (i7-1065G7/8Gb/512Gb SSD/ 15.6FHD/MX230-2GB/ Win10/Silver)\n" +
                "Laptop Dell Inspiron 5391 N3I3001W (I3-10110U/4Gb/128Gb SSD/ 13.3Inch FHD/VGA ON/ Windows 10/IceLilac)\n" +
                "Laptop HP Pavilion 15-cs2033TU 6YZ14PA (i5-8265U/4Gb/1TB HDD/15.6FHD/VGA ON/Win10/Grey)\n" +
                "Laptop HP Pavilion x360 14-dh0104TU 6ZF32PA (i5-8265U/4Gb/1Tb HDD/14FHD TouchScreen/VGA ON/Win10/Gold)\n" +
                "Laptop HP ProBook 430 G7 9GQ01PA (i7-10510/8GB/512GB SSD/13.3FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP 348 G7 9PG86PA (i3-10110U/4GB/256GB SSD/14/VGA ON/WIN10/Silver)\n" +
                "Laptop HP ProBook 440 G7 9MV57PA (i7-10510U/8GB/256GB SSD/14 FHD/VGA ON/DOS/Silver)\n" +
                "HP Pavilion 14-ce2034TU 6YZ17PA : i3-8145U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | WIN 10 | Silver\n" +
                "Laptop HP Pavilion 14-ce3015TU 8QN68PA (i3-1005G1/4Gb/1Tb HDD/14FHD/VGA ON/Win10/Gold)\n" +
                "HP 15-da1031TX : I5-8265U | 4GB RAM | 1TB HDD | GeForce MX110 2GB + UHD Graphics 620 | 15.6 inch FHD | Win 10 | Silver \n" +
                "Laptop HP 15s-du0038TX 6ZF72PA (i5 8265U/4Gb/1Tb HDD/ 15.6/MX130 2GB/DVDSM/Win10/Silver)\n" +
                "Hp ProBook 450 G7 9GQ34PA : I5-10210U | 8GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD | Dos | Finger\n" +
                "HP Pavilion 14-ce1016TU (5JR57PA) : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | Win10 | White\n" +
                "HP Pavilion x360 14-dh1138TU : i5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD Touch | Win10 \n" +
                "HP Pavilion 14-ce3037TU 8ZR43PA : I5-1035G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD | Win10 | Silver\n" +
                "HP 348 G7 9PH09PA : I7-10510U | 8GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD IPS |  Free DOS | Silver | Finger\n" +
                "HP Probook 440 G6 (5YM63PA) : i3-8145U | 4GB RAM | 500GB HDD | UHD Graphics 620 | 14.0 HD | Finger | Dos\n" +
                "HP Pavilion 14-ce2039TU 6YZ15PA : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | Win10 | Silver\n" +
                "HP 348 G7 9PG92PA : I3-10110U | 4GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD | WIN10 | Silver | Fingerprint\n" +
                "HP Pavilion 15- cs3012TU 8QN30PA : I5-1035G1 | 8GB RAM | 512GB SSD PCIe | UHD Graphics 630 | 15.6 FHD | WIN10 | Gold \n" +
                "HP ProBook 440 G7 9GQ16PA : I5-10210U | 8GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD | Finger | FreeDos \n" +
                "HP 348 G7 9PH06PA : I5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD | WIN10 | Silver | Fingerprint\n" +
                "HP ProBook 440 G7 9GQ24PA : I3-10110U | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 HD | Finger | FreeDos \n" +
                "HP 348 G7 9PG93PA : I5-10210U | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD IPS |  FreeDOS | Silver | Finger\n" +
                "HP 14s-dq1065TU 9TZ44PA : i5-1035G1 | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 HD | Win10 | Silver\n" +
                "HP Pavilion 14-ce3019TU 8QP00PA : I5-1035G1 | 4GB RAM | 1TB HDD | UHD Graphics 630 | 14.0 FHD | Win10\n" +
                "HP ProBook 450 G7 9GQ38PA : I5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 15.6 FHD | Finger | FreeDos\n" +
                "HP ProBook 450 G7 9GQ43PA : I5-10210U | 4GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD | Finger | FreeDos\n" +
                "HP 14s-dq1020TU 8QN33PA : i5-1035G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 HD | Win10 | Silver \n" +
                "HP 348 G7 9PH13PA : I7-10510U | 8GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD | WIN10 | Silver | Fingerprint\n" +
                "HP 348 G7 9PH16PA : I7-10510U | 8GB RAM | 512GB SSD PCIe | UHD Graphics 630 | 14.0 FHD | Freedos | Silver | Fingerprint\n" +
                "HP ENVY 13-aq1047TU 8XS69PA : i7-10510U | 8GB RAM | 512GB SSD PCIe | UHD Graphics 630 | 13.3 FHD IPS | Win10 | Black Vân Gỗ\n" +
                "HP Envy 13-aq1021TU : I5-10210U | 8GB RAM | 256GB SSD | UHD Graphics 620 | 13.3 FHD | Win10 | Gold \n" +
                "HP 348 G7 9PH23PA : I7-10510U | 8GB RAM | 512GB SSD | Radeon Graphics R530 + UHD Graphics 630 | 14.0 FHD | Win10 | Silver | Fingerprint\n" +
                "HP ProBook 450 G7 9TN61PA : i7-10510U | 8GB RAM | 512GB SSD | GeForce MX250 2GB + UHD Graphics 630 | 15.6 FHD | Finger | Win10\n" +
                "HP 348 G7 9PH19PA : I7-10510U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD | WIN10 | Silver | Fingerprint\n" +
                "HP ProBook 450 G7 9GQ32PA : i7-10510U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 15.6 FHD | Finger | Win10\n" +
                " HP Envy 13-aq1048TU : I5-10210U | 8GB RAM | 512GB SSD PCIe | UHD Graphics 620 | 13.3 FHD | Win10 | Black Vân Gỗ\n" +
                "Laptop HP EliteBook 745 G5 5ZU71PA (Ryzen 7-2700U/8Gb/512Gb SSD/14FHD/AMD Radeon/Win10 Pro/Silver)\n" +
                "Laptop Dell Inspiron 5593 N5I5461W (I5-1035G1/ 8Gb/ 512Gb SSD/ 15.6' FHD/ MX230-2GB/ Win10/Silver)\n" +
                "Laptop Dell Inspiron 3593 70205743 (Core i5 1035G1/4Gb/256Gb SSD/ 15.6 FHD/MX230 2Gb/ Win10/Black)\n" +
                "Laptop Dell Vostro 5590 HYXT91 (I5-10210U/ 8Gb/1Tb HDD +128Gb SSD/ 15.6' FHD/ VGA Nvidia MX230 2GB GDDR5/ Win10/ Urban Grey/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5490 70196706 (I7-10510U/ 8Gb/512Gb SSD/ 14.0'' FHD/ MX230-2Gb/ Win10/Silver/vỏ nhôm)\n" +
                "Laptop HP Pavilion x360 14-dh0103TU 6ZF24PA (i3-8145U/4Gb/1Tb HDD/14 TouchScreen/VGA ON/Win10/Gold/Pen)\n" +
                "Laptop HP ProBook 450 G7 9GQ39PA (i3-10110U/4GB/256GB SSD/15.6/VGA ON/Dos/Silver/LEB_KB)\n" +
                "Laptop Dell Vostro 3590 GRMGK2 (I7-10510U/ RAM 8Gb/256Gb SSD/ 15.6' FHD/ Radeon 610 2GB DDR5/ DVDRW/ Windows 10/Black)\n" +
                "Laptop Dell Inspiron 3593 70205744 (Core i5 1035G1/4Gb/256Gb SSD/ 15.6 FHD/MX230 2Gb/Win10/Silver)\n" +
                "Laptop Dell Gaming G3 3590 N5I517WF (Core i5-9300H/8Gb/256Gb SSD/15.6 FHD/GTX1050 3GB/Win10/Black)\n" +
                "Laptop Dell Vostro 3490 70207360 (I5-10210U/ RAM 8Gb/256Gb SSD/14.0FHD/VGA ON/Finger Print/ Win10/Black)\n" +
                "Laptop Dell Inspiron 7591 N5I5591W (Core i5-9300H/8Gb/256Gb SSD/15.6 FHD/GTX1050-3Gb/Win10/Silver\n" +
                "Laptop Dell Vostro 5490 V4I3101W (I3-10110U/4Gb/ SSD 128Gb/ 14.0' FHD/VGA ON/ Win10/ Urban Gray/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5593 N5I5513W  (I5-1035G1/ 8Gb/ 256Gb SSD/ 15.6' FHD/ MX230 2GB/ Win10/Silver)\n" +
                "Laptop HP EliteBook x360 1040 G6 6QH36AV (i7-8565U/16Gb/512Gb SSD/14FHD Touch/VGA ON/Win10 Pro/Silver/Pen)\n" +
                "Laptop HP 14s-cf1040TU 7PU14PA (i5-8265U/4Gb/1Tb HDD/14/VGA ON/Win10/Silver)\n" +
                "Laptop HP Omen Gaming 15-dh0172TX 8ZR42PA (i7-9750H/16GB/512GB SSD+1TB HDD/15.6FHD-240Hz-300nit/RTX2070 8GB/DVDR Ext/Win 10/Black)\n" +
                "Laptop HP Pavilion 15-cs3012TU 8QP30PA (i5-1035G1/8Gb/512GB SSD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP ProBook 450 G7 9GQ26PA (i7-10510U/16GB/512GB SSD/15.6FHD/Nvidia MX250-2GB/WIN 10/Silver/LEB_KB)\n" +
                "Laptop HP ProBook 445R G6 9VC65PA Ryzen 5 3500U/8Gb/512GB SSD/14FHD/ AMD Radeon Graphics/Win 10/Silver)\n" +
                "Laptop Dell Inspiron 3593C P75F013N93C (i3 1005G1/ 4Gb/256Gb SSD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
                "Laptop HP Pavilion 14-ce2041TU 6ZT94PA (i5-8265U/4Gb/1Tb HDD/14FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP 348 G5 7CS43PA (i7-8565U/8Gb/1TB HDD/14FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP Pavilion 15-cs2031TU 6YZ03PA (i3-8145U/4Gb/1Tb HDD/15.6FHD/VGA ON/Win10/Gold)\n" +
                "Laptop HP Pavilion Gaming 15-ec0050AX 9AV28PA (Ryzen 5 3550H/8Gb/1Tb HDD+128GB SSD/15.6FHD, 144Hz/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop HP Pavilion Gaming 15-dk0001TX 7HR11PA (i5-9300H/8Gb/1Tb HDD+128GB SSD/15.6FHD/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop HP ProBook 455 G6 6XA87PA (Ryzen 5-2500U/8Gb/1Tb HDD/14FHD/AMD Radeon/ Dos/Silver)\n" +
                "Laptop HP 348 G7 9PH21PA (I7-10510U/8GB/SSD 512GB/14FHD/Radeon 530-2GB/DOS/Silver)\n" +
                "Laptop HP 348 G5 7CS45PA (i7-8565U/8Gb/1TB HDD + 128GB SSD/14FHD/Radeon 530 2GB/DOS/Silver)\n" +
                "Laptop HP Pavilion Gaming 15-dk0000TX 7HR10PA (i5-9300H/8Gb/256GB SSD/15.6FHD/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop HP Elitebook 830 G6 7QR67PA (i7-8565U/RAM 16Gb/512Gb SSD/13.3FHD/VGA ON/Win10 Pro/Silver)\n" +
                "Laptop HP EliteBook 735 G5 5ZU72PA (Ryzen 5-2500U/8Gb/256GB SSD/13.3FHD/AMD Radeon/Win10 Pro/Silver)\n" +
                "Laptop HP Spectre x360 Convertible w0181TU 8YQ35PA (i7-1065G7/16GB/512GB SSD+32GB Optane/13.3UHD Touch/VGA ON/Win10/Pen/Bao da/Xanh biển)\n" +
                "Laptop Dell Gaming G3 3590 70191515(Core i7-9750H/8Gb/512Gb SSD/15.6' FHD/GTX1660 TI 6GB/Win10/Black)\n" +
                "Laptop HP EliteBook 745 G5 5ZU69PA (Ryzen 5-2500U/8Gb/256GB SSD/14FHD/AMD Radeon/Win10 Pro/Silver)\n" +
                "Laptop HP 15s-du0126TU 1V888PA (i3-8130U/4GB/256GB SSD/15.6/VGA ON/Win10/Silver)\n" +
                "Laptop HP Pavilion Gaming 15-dk0232TX 8DS85PA (i7-9750H/8Gb/1Tb HDD/15.6FHD/GTX1650 4GB/Win 10/Black)\n" +
                "Laptop HP ProBook 440 G7 9GQ11PA (i7-10510U/16GB/512GB SSD/14FHD/VGA ON/WIN 10/Silver)\n" +
                "Laptop HP ProBook 430 G7 9GQ08PA (i5-10210/4GB/256GB SSD/13.3FHD/VGA ON/DOS/Silver)\n" +
                "Laptop HP Pavilion 15-cs3119TX 9FN16PA (i5-1035G1/4Gb/256GB SSD/15.6FHD/MX250 2GB/Win10/Grey)\n" +
                "Laptop HP 14s-dq1100TU 193U0PA (i3-1005G1/4GB/256GB SSD/14/VGA ON/Win10/Silver)\n" +
                "Laptop HP Pavilion x360 14-dw0060TU 195M8PA (i3-1005G1/4GB/256GB SSD/14FHD TouchScreen/VGA ON/Win10+Office Home vàamp; Student/Gold/Pen)\n" +
                "Laptop Dell Vostro 5490A P116G001 (Core i5-10210U/8Gb/256Gb SSD/14.0''FHD/MX230-2Gb/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 3593A P75F013N93A (i3 1005G1/ 4Gb/1Tb HDD/ 15.6 FHD/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Inspiron 3593B P75F013N93 (i5 1035G1/ 4Gb/1Tb HDD/ 15.6' FHD/VGA ON/  Win10/Black)\n" +
                "Laptop Dell Vostro 5581-70175950 (Core i5-8265U/4Gb/1Tb HDD/15.6' FHD/VGA ON/Win10+ Off365/Urban Grey/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5491 C1JW82 (I7-10510U/ 8Gb/512Gb SSD/ 14.0 FHD/Touch/MX230-2GB5/Win10/Silver/Vỏ nhôm/Bút)\n" +
                "Laptop Dell Gaming G3 3590 70203973 (Core i7-9750H/8Gb/512Gb SSD/15.6 FHD/GTX1660 TI 6GB/Win10/Black)\n" +
                "Laptop Dell Latitude 3400 L3400I5SSD4G (Core i5-8265U/4Gb bus 2400/SSD 256Gb/14.0'/VGA ON/Dos/Black)\n" +
                "Laptop Dell Gaming G5 5590M P82F001 (Core i5-9300H/8Gb/1Tb HDD + SSD 128Gb/15.6' FHD/GTX1650-4Gb/Win10/Black)\n" +
                "Laptop Dell Inspiron 7490 N4I5106W (I5-10210U/ 8Gb/ 512Gb SSD/ 14.0'' FHD/ MX250 2GB/ Win10/Silver/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5584 CXGR01 (Core i5-8265U/8Gb/1Tb HDD/15.6' FHD/VGA ON/Win10/Silver)\n" +
                "Laptop Dell XPS 15-7590 70196711 (Core i9-9980HK/32Gb/1Tb SSD/ 15.6'' 4K/Touch/GTX1650 4Gb/ Win10 + Off365/Silver/vỏ nhôm)\n" +
                "Laptop Dell Inspiron 5491 N4TI5024W (I5-10210U/ 8Gb/SSD 512Gb/ 14.0'' FHD/Touch/MX230 2GB/ Win10/Silver/Vỏ nhôm)\n" +
                "Laptop Dell Inspiron 7391 P113G001T91A (I7-10510U/8Gb/512Gb SSD/13.3''FHD/Touch/Pen/VGA ON/ Win10/Black/Vỏ nhôm)\n" +
                "Laptop Dell Latitude 5490 70205623 (Core i5 8350U/ 8Gb/ 256Gb SSD/ 14.0HD/VGA ON/SMARTCARD/DOS/Black)\n" +
                "Laptop Dell Inspiron 3593 70197460 (i7-1065G7/8Gb/512Gb SSD/ 15.6'FHD/MX230-2GB/ Win10/Silver)\n" +
                "Laptop Dell XPS 15-7590 70196708 (Core i7-9750H/16Gb/ 512Gb SSD/ 15.6' - 4K/Touch/GTX1650 4Gb/Win10 + Off365/Silver/vỏ nhôm)\n" +
                "Laptop Dell Latitude 3400 L3400I5SSD (Core i5-8265U/8Gb/SSD 256Gb/14.0/VGA ON/Dos/Black)\n" +
                "Laptop Dell XPS 13 7390 04PDV1 (I7-10510U/16Gb/ 512Gb SSD/13.3'' UHD/Touch/VGA ON/ Win10+Off365/Silver/vỏ nhôm)\n" +
                "Laptop Apple Macbook Pro MPXR2 128Gb (2017) (Silver)\n" +
                "Laptop Dell XPS 13 7390 70197462 (I5-10210U/8Gb/256Gb SSD/13.3''FHD/VGA ON/Win10/Silver/vỏ nhôm)\n" +
                "Laptop Dell Vostro 3590 GRMGK1 (I5-10210U/4Gb/1Tb HDD/15.6'' FHD/DVDW/VGA ON/ Win10/Black)\n" +
                "Laptop Dell Latitude 7400 70194805 (Core i7 8665U/ 8Gb/ 256Gb SSD/ 14.0 FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Vostro 3580I P75F010 (Core i5-8265U/4Gb/1Tb HDD/ 15.6' FHD/Radeon 520 2GB/Win10/Black)\n" +
                "Laptop Dell Gaming G5 5590 4F4Y41(Core i7-9750H/8Gb/1Tb HDD +256Gb SSD/15.6' FHD/GTX1650 4Gb/Win10/Black)\n" +
                "Laptop Dell Latitude 5400 70194817 (Core i5 8365U/ 8Gb/ 256Gb SSD/ 14.0 FHD/VGA ON/ DOS/Black)\n" +
                "Laptop Dell Inspiron 7591 KJ2G41(Core i7-9750H/8Gb/256Gb SSD/15.6' FHD/GTX1050 3Gb/Win10/Silver)\n" +
                "Laptop Dell Inspiron 7490 6RKVN1 (i7-10510U/16Gb/512Gb SSD/MX250-2Gb/14''FHD/Win10/Vỏ nhôm/Silver)\n" +
                "Laptop Dell Gaming G3 3590 N5I5517W (Core i5-9300H/8Gb/256Gb SSD/15.6' FHD/GTX1050 3GB/Win10/Black)\n" +
                "Laptop Dell Inspiron 3493 WTW3M2 (Core i3 1005G1/ RAM 4Gb/ 256Gb SSD/ 14.0Inch FHD/ VGA ON/ Win10/ Black)\n" +
                "Laptop Dell Gaming G5 5590 4F4Y43(Core i7-9750H/8Gb/1Tb HDD +256Gb SSD/15.6 FHD/GTX1660TI 4Gb/Win10/Black)\n" +
                "Laptop Dell Vostro 5490 70197464 (I7-10510U/ 8Gb/512Gb SSD/ 14.0' FHD/ MX 250 2Gb/ Win10/ Urban gray/vỏ nhôm)\n" +
                "Laptop Apple Macbook Pro MVVM2 SA/A 1Tb (2019) (Silver)- Touch Bar\n" +
                "Laptop Apple Macbook Pro MVVL2 SA/A 512Gb (2019) (Silver)- Touch Bar\n" +
                "Laptop Apple Macbook Pro MVVK2 SA/A 1Tb (2019) (Gray)- Touch Bar\n" +
                "Laptop Apple Macbook Pro MVVJ2 SA/A 512Gb (2019) (Gray)- Touch Bar\n" +
                "Laptop Apple Macbook Pro MR9U2 256Gb (2018) (Silver)- Touch bar\n" +
                "Laptop Apple Macbook Pro MV902 SA/A 256Gb (2019) (Space Gray)- Touch Bar\n" +
                "Laptop Apple Macbook Pro MR9Q2 256Gb (2018) (Space Gray)- Touch bar\n" +
                "Laptop Apple Macbook Pro MPXQ2 128Gb (2017) (Space Gray)\n" +
                "Laptop Apple Macbook Pro MV922 SA/A 256Gb (2019) (Silver)- Touch Bar\n" +
                "Laptop Lenovo Thinkpad T14S GEN 1 20T0S01P00(Core i5-10210U/8Gb/512Gb SSD/14.0 FHD/VGA ON/Windows 10 Pro/Black)\n" +
                "Laptop Lenovo Thinkpad E15 20RDS0DU00 (Core i7-10510U/8Gb/512Gb HDD/15.6 FHD/RX640-2Gb/Finger Print/Dos/Black)\n" +
                "Laptop Lenovo Ideapad S340 15IWL 81N800RSVN (Core i3-8145U/4GB/1Tb HDD 15.6' FHD/VGA ON/Win10/Black/vỏ nhôm)\n" +
                "Laptop Lenovo Yoga S730-13IWL-81J0008TVN (Core i7-8565U/ 8Gb/ 512GB SSD/13.3Inch FHD/VGA ON/Win10/FINGERPRINT/Silver)\n" +
                "Laptop Lenovo Thinkbook 14S IML 20RS004AVN(Core i7 10510U/16Gb/512Gb SSD/14.0FHD/VGA ON/Win10/ Grey/nhôm)\n" +
                "Laptop Lenovo Thinkbook 13s IML 20RR004TVN (Core i5 10210U/8Gb/512Gb SSD/13.3FHD/Radeon 630 2Gb/Win10/Grey)\n" +
                "Laptop Lenovo Thinkpad T490S 20NYSBHC00 (Core i7-8665U/16Gb/512Gb SSD/14.0 FHD/VGA ON/ Dos/Black)\n" +
                "Laptop Lenovo Thinkpad E590 20NBS07000 PA (Core i5-8265U/8Gb/1Tb + 120Gb SSD/ 15.6/VGA ON/ Dos/Black)\n" +
                "Laptop Lenovo Thinkpad X1 Carbon 7 20R1S00100 (Core i5-10210U/8Gb/256Gb SSD/14.0 QHD/VGA ON/Win10 Pro/Black)\n" +
                "Laptop Lenovo Thinkbook 15 IML 20RW008WVN(Core i3 10110U/4Gb/256Gb SSD/15.6FHD/VGA ON/DOS/ Grey)\n" +
                "Laptop Lenovo Thinkpad E590 20NBS00100 PA (Core i5-8265U/8Gb/1Tb HDD + 120Gb SSD/ 15.6/ RX 550X 2G/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad L390 20NRS0NC00 (Core i7-8565U/8Gb/256Gb SSD/13.3FHD/VGA OND/Finger Print/Dos/Black)\n" +
                "Laptop Lenovo Thinkbook 13s IWL 20R900DJVN(Core i7-8565U/8Gb/256Gb SSD/13.3'FHD/VGA ON/Win10/ Grey)\n" +
                "Laptop Lenovo Thinkpad T14S GEN 1 20T0S01N00(Core i5-10210U/8Gb/512Gb SSD/14.0 FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad L13 20R30025VA (Core i7-10510U/4Gb/256Gb SSD/ 13.3FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad X1 Carbon 7 20R1S01N00 (Core i7-10510U/8Gb/256Gb SSD/14.0 QHD/VGA ON/Win10 Pro/Black)\n" +
                "Laptop Lenovo Ideapad 5 14IIL05 81YH00ENVN(Core i5-1035G1/ 8Gb/512Gb SSD/14.0 FHD/VGA ON/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Lenovo Thinkbook 13s IML 20RR00B8VN (Core i5 10210U/8Gb/512Gb SSD/13.3FHD/VGA ON/Dos/Grey/vỏ nhôm)\n" +
                "Laptop Lenovo Thinkpad L13 20R30023VA (Core i5-10210U/4Gb/256Gb SSD/ 13.3FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Ideapad S340 14IIL 81VV003TVN (Core i3-1005G1/4Gb/512Gb SSD/14.0 FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Lenovo Ideapad C340 14API 81N6007JVN (Ryzen5-3500U/8Gb/256Gb SSD/14.0 HD/Touch/Xoay/VGA ON/Win10/Silver)\n" +
                "Laptop Lenovo Thinkpad L390 20NRS00100 PA(Core i5-8265U/8Gb/256Gb SSD/ 13.3FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad T14S GEN 1 20T0S01R00 (Core i7-10510U/8Gb/512Gb SSD/14.0 FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad X390 20SDS0PD00 (Core i7-10510U/16Gb/512Gb SSD/ 13.3FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Thinkpad E490S 20NGS01K00 (Core i5-8265U/8Gb/256Gb SSD/14.0' FHD/VGA ON/Finger Print/ Dos/Black)\n" +
                "Laptop Lenovo Thinkpad L380 20M5S01200 (Core i5-8250U/4Gb/256Gb SSD/13.3'FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Ideapad S340 15API 81NC00G8VN (Ryzen 5 3500U/8Gb/512Gb SSD/ 15.6 FHD/VGA ON/ Win10/Grey)\n" +
                "Asus ZenBook UX334FAC-A4060T : i5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 620 | 13.3 FHD IPS | Screen Pad | Win10 | Silver\n" +
                "Asus Vivobook S431FL-EB171T : I5-10210U | 8GB RAM | 512G SSD PCIe + OP 32GB | Geforce MX250 2GB + UHD Graphics 630 | 14.0 FHD | Win10 | Finger | Silver\n" +
                "ASUS X509FA-EJ101T : I5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 630 | 15.6 FHD | Finger | WIN10 | Silver\n" +
                "Asus Vivobook A512FA-EJ099T : I3-8145U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Blue\n" +
                "Asus TUF Gaming FX505DY-AL060T : Ryzen 5-3550H | 8GB RAM | 512GB PCIe SSD | Radeon RX560X 4GB | 15.6 FHD 120Hz | Win10\n" +
                "Asus Vivobook S431FA-EB525T : I5-10210U | 8GB RAM | 512G SSD PCIe | UHD Graphics 630 | 14.0 FHD | Win10 | Finger | Pink\n" +
                "Asus Vivobook A412FA-EK378T : i3-8145U | 4GB RAM | 256GB SSD PCle | UHD Graphics 620 | 14.0 FHD | Win10 | Finger | Blue\n" +
                "Asus Vivobook S531FL-BQ420T : i5-10210U | 8GB RAM | 512GB SSD PCIe | GeForce MX250 2GB| 15.6 FHD | Win10 | Finger | Silver \n" +
                "Asus Vivobook A512FA-EJ117T : I3-8145U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Silver\n" +
                "Asus D509DA-EJ286T : R5-3500U | 4GB RAM | 256GB SSD | AMD Radeon Vega 8 | 15.6 FHD | Win10 | Silver\n" +
                "Asus D570DD-E4028T : R5-3500U | 8GB RAM | 256GB SSD | GTX 1050 4GB + Radeon Vega 8 Graphics | 15.6 FHD | WIN10\n" +
                "Asus TUF FX705DD-AU100T : RYZEN 5-3550H | 8GB RAM | SSD 512GB PCIe | GTX 1050 3GB | 17.3 FHD IPS | Win10\n" +
                "Asus Vivobook S430FA-EB075T : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.1 FHD IPS | Win10 | Finger | Grey \n" +
                "Asus Vivobook A512FL-EJ163T : i5-8265U | 8GB RAM | 1TB HDD | Geforce MX250 2GB + UHD Graphics 620 | 15.6 FHD | Win10 | Finger | Silver \n" +
                "ASUS X509FJ-EJ158T : I7-8565U | 4GB RAM | 512GB PCIe SSD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Finger | WIN10 | Silver\n" +
                "Asus F571GT-BQ266T : i7-9750H | 8GB RAM | 512GB SSD + 32GB Optane |  GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD | Win10 | Finger \n" +
                "Asus X505ZA-EJ563T : AMD Ryzen 5-2500U | 4GB RAM | 1TB HDD | Radeon Vega 8 | 15.6 FHD | Win10 | Gold\n" +
                "Asus Vivobook A512FL-EJ440T : i5-8265U | 8GB RAM | 512GB SSD PCIe | UHD Graphics 620 | 15.6 FHD | Win10 | Finger | Silver\n" +
                "Asus F571GD-BQ319T : i5-9300H | 8GB RAM | 512GB PCIe SSD | GeForce GTX 1050 4GB + UHD Graphics 630 | 15.6 FHD | Win10 | Finger \n" +
                "Asus Vivobook A512FA-EJ1170T : I3-8145U | 4GB RAM | 512GB SSD PCle | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Silver\n" +
                "Asus Vivobook A412FJ-EK388T : i7-10510U | 8GB RAM | 512GB SSD | Geforce MX230 2GB + UHD Graphics 630 | 14.0 FHD | Win10 | Finger | Silver\n" +
                "Asus Vivobook S14 S433FA-EB052T : i5-10210U | 8GB RAM | 512GB SSD | UHD Graphics 630 | 14.0 FHD IPS | WIN 10 | White, Black, Red\n" +
                "Asus Vivobook S330FA-EY116T : i5-8265U | 8GB RAM | 512GB SSD | UHD Graphics 620 | 13.3 FHD | Win 10 | Finger | Gold\n" +
                "Asus Vivobook S431FA-EB163T : I5-10210U | 8GB RAM | 512G SSD PCIe + OP 32GB | UHD Graphics 630 | 14.0 FHD | Win10 | Finger | Silver\n" +
                "Asus Vivobook X409FA-EK056T : i3-8145U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | Win10 | Silver \n" +
                "Asus Vivobook A412FA-EK380T : i3-8145U | 4GB RAM | 512GB SSD PCle | UHD Graphics 620 | 14.0 FHD | Win10 | Finger | Silver\n" +
                "Asus Vivobook X409FA-EK100T : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | Win10 | Gray\n" +
                "ASUS PRO P3540FA-BR0539 : I3-8450U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 15.6 FHD | Finger | FreeDos \n" +
                "Asus Vivobook A512DA-EJ418T : R7-3700U | 8GB RAM | 512GB SSD PCIe | Radeon Vega 10 | 15.6 FHD | Win 10 | Finger | Silver \n" +
                "Asus Vivobook A512FL-EJ569T :  i5-10210U | 8GB RAM | 512GB SSD PCIe | Geforce MX250 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Finger | Silver \n" +
                "Asus Vivobook A512FA-EJ570T : I3-8145U | 4GB RAM | 256GB SSD PCle | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Blue\n" +
                "Asus Vivobook A512FA-EJ837T : I3-8145U | 4GB RAM | 512GB SSD PCle | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Blue\n" +
                "Asus Vivobook A512FA-EJ571T : I3-8145U | 4GB RAM | 256GB SSD PCle | UHD Graphics 620 | 15.6 FHD | Win 10 | Finger | Silver\n" +
                "Asus Vivobook A412FA-EK377T : i3-8145U | 4GB RAM | 256GB SSD PCle | UHD Graphics 620 | 14.0 FHD | Win10 | Finger | Silver\n" +
                "Asus ROG Zephyrus M GU502GU-AZ090T : i7-9750H | 16GB RAM | 512GB SSD PCIe | GeForce GTX 1660Ti 6GB |15.6 FHD IPS 240Hz | Win10 | RGB Perkey | Black Metal\n" +
                "Asus ROG Strix G G531GT-AL017T : i7-9750H | 8GB RAM | 512GB PCIe SSD | GeForce GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | Win10\n" +
                "Asus ROG Strix Zephyrus S GX502GV-AZ061T : i7-9750H | 16GB RAM | 512GB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 240Hz | RGB Perkey | WIN 10\n" +
                "ASUS ROG ZEPHYRUS G14 GA401II-HE152T : RYZEN 7-4800HS | 16GB RAM | 512GB SSD | GTX 1650TI 4GB MaxQ + AMD Radeon™ Graphics | 14 FHD 120Hz IPS | WIN 10\n" +
                "Asus ROG Strix SCAR III G531GW-AZ082R : i9-9880H | 32GB RAM | 1TB SSD PCIe | GeForce RTX 2070 6GB + UHD Graphics 630 | 15.6 FHD IPS 240Hz/3ms | Win 10\n" +
                "Asus ROG Strix G G531G VAL052T : i7-9750H | 8GB RAM | 512GB PCIe SSD | GeForce RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | RGB | Win10\n" +
                "Asus ROG Strix G G531-UAL064T : i5-9300H | 8GB RAM | 512GB PCIe SSD | GeForce GTX 1660Ti 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | Win 10\n" +
                "ASUS ROG Strix G15 G512-IAL013T : I5-10300H | 8GB RAM | 512GB SSD PCIe | UHD Graphics 630 + GTX 1650Ti 4GB | 15.6 FHD IPS 144Hz | RGB | WIN 10\n" +
                "Asus ROG Zephyrus G GA502DU-AL024T : Ryzen 7-3750H | 8GB RAM | 512GB SSD | GeForce GTX 1660Ti 6GB + Radeon RX Vega 10| 15.6 FHD 120Hz | Win10\n" +
                "Asus ROG Strix Zephyrus S GX701GXR-HG142T : i7-9750H | 32GB RAM | 1TB SSD PCIe | RTX 2080 8GB MAXQ + UHD Graphics 630 | 17.3 FHD IPS 300Hz | RGB Perkey | WIN 10\n" +
                "Asus ROG Strix SCAR III G731GW-EV103T | i7-9750H | 16GB DDR4 | 1TB PCIe SSD | GeForce RTX 2070 6GB + UHD Graphics 630 | 17.3 FHD IPS 144Hz | Win10\n" +
                "Asus ROG Strix Zephyrus S GX701GXR-H6072T : i7-9750H | 32GB RAM | 1TB SSD PCIe | RTX 2080 8GB MAXQ + UHD Graphics 630 | 17.3 FHD IPS 240Hz | RGB Perkey | WIN 10\n" +
                "Asus ROG Zephyrus S GX502GW-ES021T : i7-9750H | 16GB RAM | 512GB PCIe SSD | GeForce RTX 2070 8GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Win10\n" +
                "Asus ROG Zephyrus S GX502GV-ES018T : i7-9750H | 16GB RAM | 512GB PCIe SSD | GeForce RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Win10\n" +
                "Asus ROG Strix Scar III G531G-WAZ209T | i7-9750H | 16GB RAM | 1TB SSD PCIe | RTX 2070 8GB + UHD Graphics 630 | 15.6 FHD IPS 240Hz/3ms | Win 10\n" +
                "Asus ROG SCAR III G731GN-WH100T : i7-9750H | 16GB RAM | 1TB SSD PCIe | RTX 2070 8GB + UHD Graphics 630 | 17.3 FHD IPS 240HZ | RGB Perkey | Win10\n" +
                "ASUS ROG ZEPHYRUS G14 GA401II-HE155T : RYZEN 7-4800HS | 16GB RAM | 512GB SSD PCIe | GTX 1650TI 4GB MaxQ + AMD Radeon Graphics | 14.0 FHD 120Hz IPS | WIN 10 | ANIME\n" +
                "Asus ROG Strix G G531-UAL214T : i7-9750H | 8GB RAM | 512GB SSD PCIe | GTX 1660Ti 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | RGB | Win10\n" +
                "Asus ROG Strix G G531-VAL218T : i7-9750H | 8GB RAM | 512GB SSD PCIe | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | RGB | Win 10\n" +
                "Asus ROG Strix G G531G VAL319T : i7-9750H | 16GB RAM | 512GB PCIe SSD | GeForce RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 120Hz | RGB | Win10\n" +
                "Laptop Lenovo Ideapad S540 14IML 81NF0062VN (Core i5-10210U/ 8Gb/512Gb SSD/14.0 FHD/VGA ON/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Lenovo Thinkbook 15 IML 20RW0091VN(Core i5 10210U/4Gb/256Gb SSD/15.6inch/FHD/VGA ON/DOS/ Grey)\n" +
                "Laptop Lenovo Thinkpad E15 20RDS0DM00 (Core i5-10210U/8Gb/256Gb HDD/15.6 FHD/VGA ON/Finger Print/Dos/Black)\n" +
                "Laptop Lenovo Thinkbook 14 IIL 20SL00K3VN(Core i5 1035G1/8Gb/512 SSD/14.0FHD/VGA ON/DOS/ Grey)\n" +
                "Laptop Lenovo Thinkbook 14 IML 20RV00B8VN (Core i5 10210U/4Gb/256 SSD/14.0FHD/VGA ON/WIN10/ Grey/vỏ nhôm)\n" +
                "Laptop Lenovo Ideapad S145 14IWL 81W6001GVN (i3-1005G1/4GB/256GB SSD/VGA ON/14.0”FHD/Win10/Grey)\n" +
                "Laptop Lenovo Thinkbook 15 IML 20SM00A1VN(Core i5 1035G1/8Gb/512Gb SSD/15.6FHD/AMD Radeon 630 2GB /DOS/ Grey/ vỏ nhôm)\n" +
                "Laptop Lenovo Thinkpad L390 20NRS00100 (Core i5-8265U/4Gb/256Gb SSD/ 13.3'FHD/VGA ON/Dos/Black)\n" +
                "Laptop Lenovo Gaming Ideapad L340 15IRH 81LK00VUVN (Core i7-9750H/8Gb/512Gb SSD/15.6 FHD/GTX1050-3Gb/Win10/Black)\n" +
                "Laptop Lenovo Ideapad 5 15IIL05 81YK004VVN(Core i5-1035G1/ 8Gb/256Gb SSD/15.6 FHD/MX330-2Gb/Win10/Grey)\n" +
                "Laptop Lenovo Ideapad C340 15IIL 81XJ0027VN (Core i5 1035G1/8Gb/512Gb SSD/15.6 FHD/Touch/Xoay/VGA ON/Win10/Black)\n" +
                "Laptop Lenovo S730 13IWL 81J0008SVN (Core i5-8265U/8Gb/512GB SSD/13.3Inch FHD/VGA ON/Win10/FINGERPRINT/Silver/vỏ nhôm)\n" +
                "Laptop Lenovo Ideapad S145 15IWL 81W8001YVN (i5-1035G1/4GB/256GB SSD/VGA ON/15.6”FHD/Win10/Grey)\n" +
                "Laptop Lenovo Ideapad C340 14API 81N600A3VN (Ryzen7-3700U/8Gb/512Gb SSD/14.0 FHD/Touch/Xoay/VGA ON/Win10/Black)\n" +
                "Laptop Lenovo Ideapad S145 15IWL 81MV00F0VN (Core i3-8145U/4GB/256GB SSD/15.6” FHD/Win 10/Grey)\n" +
                "Laptop Lenovo Ideapad S340 15IIL 81VW00A8VN (Core i5 1035G4/8Gb/512Gb SSD/ 15.6 FHD/VGA ON/ Win10/Xám Bạc/vỏ nhôm)\n" +
                "Laptop Lenovo Thinkbook 14S IML 20RS004XVN (Core i5 10210U/8Gb/512Gb SSD/14.0FHD/VGA ON/DOS/ Grey/nhôm)\n" +
                "Laptop Lenovo Ideapad S340 15IWL 81N800EVVN (Core i3-8145U/4Gb/256Gb SSD/15.6' FHD/VGA ON/Win10/Grey)\n" +
                "Laptop Lenovo Ideapad S145 15IWL 81UT00DMVN (Ryzen 3-3200U 2.5G/4GB/256GB SSD/15.6” FHD/Win 10/Grey)\n" +
                "Laptop Lenovo V14 14IIL 82C400X3VN(Core i3 1005G1/4Gb/256Gb SSD/14.0 FHD/VGA ON/ DOS/Grey)\n" +
                "Laptop Lenovo Thinkbook 13s IML 20RR004UVN(Core i7-10510U/8Gb/512Gb SSD/13.3FHD/VGA ON/Win10/ Grey)\n" +
                "Laptop Lenovo Ideapad S540 15IML 81NG004TVN (I7-10510U/8Gb/1Tb SSD/15.6 FHD/MX250 2Gb/ Win10/Grey)\n" +
                "Laptop Lenovo Ideapad S540 15IML 81NG004RVN (Core i5-10210U/ 8Gb/512Gb SSD/15.6 FHD/MX250 2Gb/Win10/Grey/vỏ nhôm)\n" +
                "Laptop Lenovo Thinkpad E490S 20NGS01N00 (Core i7-8565U/8Gb/256Gb SSD/ 14.0' FHD/VGA ON/Finger Print/Dos/Black)\n" +
                "Dell Inspiron 3493A : i5-1035G1 | 4GB RAM | 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 14.0 HD | Win10 | Silver\n" +
                "Dell Vostro 3590A : i5-10210U | 4GB RAM | 1TB HDD | AMD Radeon 610 2GB | 15.6 FHD | Win10 | Black\n" +
                " Dell Inspiron 3593 : i5-1035G1 | 4GB RAM | 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | FreeDos\n" +
                "Dell Inspiron 5490 : i5-10210U | 8GB RAM | 256GB SSD PCIe | GeForce MX230 2GB + UHD Graphics 630 | 14.0 FHD | Win10 | Silver, Gold\n" +
                "Dell Vostro 3580I : i5-8265U | 4GB RAM | 1TB HDD |  AMD Radeon 520 2G + UHD Graphics 620 | 15.6 FHD | Win10 | Finger | Black\n" +
                "Dell Vostro 3490 70196714 : i5-10210U | 4GB RAM | 1TB HDD | UHD Graphics 630 | 14.0 HD | Win10 | Finger | Black\n" +
                "Dell Inspiron 3593B : i5-1035G1 | 4GB RAM | 1TB HDD | UHD Graphics 630 | 15.6 FHD | Win10 | Black\n" +
                "Dell Inspiron 3593 70197458: i5-1035G1 | 4GB RAM | 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Silver\n" +
                "Dell Vostro 3590 V3590B : i5-10210U | 8GB RAM | 256GB SSD | AMD Radeon 610 2GB | 15.6 FHD | Win10 | Black\n" +
                "Dell Inspiron 3493-N4I5122WA : I5-1035G1 | 8GB RAM |  256GB SSD | UHD Graphics 620 | 14.0 FHD | Win 10 | Silver\n" +
                "Dell Vostro 5490 : i3-10110U | 4GB RAM | 128GB SSD PCIe | UHD Graphics 630 | 14.0 FHD | FreeDos | Urban Gray\n" +
                "Dell Inspiron 5490 : i5-10210U | 4GB RAM | 256GB SSD | MX230 2GB + UHD Graphics 630 | 14 FHD IPS | WIN10 | SILVER\n" +
                "Dell Vostro 5490B : i5-10210U | 8GB RAM | 256GB SSD PCIe | GeForce MX230 2GB | 14.0 FHD | Finger | Win10 | Urban Grey\n" +
                "Dell Inspiron 5391: i3-10110U | 4GB RAM | 128GB SSD | HD Graphics 620 | 13.3 FHD | FreeDos | Finger | IceLilac\n" +
                "Dell Inspiron 3593 70197457 : i5-1035G1 | 4GB RAM | 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Black \n" +
                "Dell Vostro 5481 : i5-8265U | 4GB RAM | 128GB SSD + 1TB HDD | GeForce MX130 2GB + UHD Graphics 620 | 14.0 FHD IPS | Finger | Ledkey | FreeDos\n" +
                "Dell Inspiron 3593C : i3-1005G1 | 4GB RAM | 256GB SSD | UHD Graphics 630 | 15.6 FHD | Win10 | Black \n" +
                "Dell Vostro 3590 : i7-10510U | 8GB RAM | 256GB SSD | AMD Radeon 610 2GB + UHD Graphics 630  | 15.6 FHD | Win10\n" +
                "Dell Latitude 7390 : i5-8350U | 8GB RAM | 256GB SSD | UHD Graphics 620 | 13.3 FHD Touch | Win10 Pro | Ledkey | W/LTE | Grey\n" +
                "Dell Vostro 3490 70196712 : i3-10110U | 4GB RAM | 1TB HDD | UHD Graphics 630 | 14.0 HD | Win10 | Finger | Black\n" +
                "Dell Vostro 3490-70207360 : i5-10210U | 8GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD | Finger | WIN 10 | BLACK\n" +
                " Dell Vostro 5581 : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 15.6 FHD IPS | Finger | Ledkey | FreeDos\n" +
                "Dell Inspiron 5593 N5I5402W : i5-1035G1 | 4GB RAM | 128GB SSD + 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Silver\n" +
                "Dell Vostro 3490-2N1R82 : i5-10210U | 8GB RAM | 256GB SSD | AMD Radeon 610 + UHD Graphics 630 | 14.0 FHD | FINGER | WIN 10 | BLACK\n" +
                " Dell Vostro 5590 : i5-10210U | 8GB RAM | 128GB SSD + 1TB HDD | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Finger\n" +
                "Dell Vostro 5490 : i5-10210U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 14.0 FHD | Win10 | Urban Gray\n" +
                "Dell Inspiron 3593A : i3-1005G1 | 4GB RAM | 1TB HDD | UHD Graphics 630 | 15.6 HD | Win10 | Black \n" +
                "Dell Vostro 5490 : i5-10210U | 8GB RAM | 256GB SSD PCIe | GeForce MX230 2GB + UHD Graphics 630 | 14.0 FHD | Finger | Win10\n" +
                "Dell Alienware 15 R4 : i7-8750H | 8GB RAM | 256GB SSD PCIe | GeForce GTX 1060 6GB + UHD Graphics 630 | 15.6 FHD IPS | Win10\n" +
                "Dell G3 3590 : i7-9750H | 16GB RAM | 256GB SSD + 1TB HDD | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD IPS 60hz | Win10 | Finger | Black , White\n" +
                " Dell Inspiron 5593A : i7-1065G7 | 8GB RAM | 512GB SSD PCIe | GeForce MX230 4GB + UHD Graphics 630 | 15.6 FHD | Win10 | Led_key | Silver\n" +
                "Dell Alienware M15 : i7-8750H | 8GB RAM | 1TB HDD | GeForce GTX 1060 6GB + UHD Graphics 630 | 15.6 FHD IPS | Win10\n" +
                "Dell Alienware 15 R4 : i7-8750H | 16GB RAM | 1TB HDD + 256GB SSD | GeForce GTX 1070 8GB + UHD Graphics 630 | 15.6 FHD IPS | Win10\n" +
                " Dell XPS 9380 : i7-8565U | RAM 8GB | 256GB SSD | UHD Graphics 630 | 13.3 FHD | WIN 10 | Silver\n" +
                "Dell G7 7590Z : i7-9750H | 16GB RAM | 256GB SSD + 1TB HDD | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD 144Hz | RGB | Finger | Win10 \n" +
                "Dell XPS 7390 2in1 : i7-1065G7 | 16GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 13.4 FHD IPS Touch | WIN 10 | Black, White\n" +
                "Dell Inspiron 5593 : i7-1065G7 | 8GB RAM | 512GB SSD PCIe | GeForce MX230 4GB + UHD Graphics 630 | 15.6 FHD | Finger | Win10 | Led_key | Silver\n" +
                "Dell G5 5590 : i5-9300H | 8GB RAM | 128GB SSD + 1TB HDD | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD 60Hz | Win10 | Finger\n" +
                "Dell Inspiron 5401 : i7-1065G7 | 8GB RAM | 512GB SSD | GeForce MX330 2GB + UHD Graphics | 14.0 FHD WVA | WIN 10 | Platinum Silver\n" +
                "Dell XPS 7390 : i5-10210U | 8GB RAM | 256GB SSD PCIe | UHD Graphics 630 | 13.3 FHD IPS | Finger | WIN 10 \n" +
                "Dell Inspiron 7391A : i7-10510U | 8GB RAM | 512GB SSD PCIe | UHD Graphics 630 | 13.3 FHD Touch | Xoay 360 2in1 | Win10\n" +
                "Dell G5 5590 : i7-9750H | 16GB RAM | 1TB HDD + 128GB SSD | GTX 1650 4GB + UHD Graphics 630 | 15.6 FHD | Ledkey | Finger | Win10 | Black\n" +
                "Dell Inspiron 5401 : i5-1035G1 | 8GB RAM | 256GB SSD | GeForce MX330 2GB + UHD Graphics | 14.0 FHD WVA | WIN 10 | Platinum Silver\n" +
                "Dell Vostro 5590A : i7-10510U | 8GB RAM | 256GB SSD PCIe | GeForce MX250 2GB + UHD Graphics 630 | 15.0 FHD | Win10 | Finger\n" +
                "Dell G5 5590-4F4Y42 : i7-9750H | 16GB RAM | 512GB SSD | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS | Win10\n" +
                "Dell G5 5590 : i7-9750H | 16GB RAM | 512GB SSD | RTX 2060 6GB + UHD Graphics 630 | 15.6 FHD IPS 144Hz | Win10 | Black\n" +
                "Dell Inspiron 3593 70197459 : i7-1065G7 | 8GB RAM | 512GB SSD PCIe | GeForce MX230 2GB + UHD Graphics 630 | 15.6 FHD | Win10 | Black | DVD R/W\n" +
                "Dell Inspiron 5300 : i5-10210U | 8GB RAM | 256GB SSD | UHD Graphics | 13.3 FHD IPS | WIN 10 | Platinum Silver\n" +
                "Dell G7 7590 : i7-9750H | 16GB RAM | 512GB SSD | RTX 2070 8GB + UHD Graphics 630 | 15.6 FHD 144hz | RGB LedKey | Finger | Win 10\n" +
                "Dell Vostro 5590 : i7-10510U | 8GB RAM | 256GB SSD PCIe | GeForce MX250 2GB + UHD Graphics 630 | 15.0 FHD | Win10 | Finger";
        String[] result = text.split("\n");
        return result;
    }
}
