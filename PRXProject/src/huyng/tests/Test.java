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
        LaptopService laptopService = new LaptopService();
        String brandName = " ";
        List<LaptopEntity> laptops = laptopService.getByBrandName(brandName);
//        String[] model = getStringTest();
        for (int i = 0; i < laptops.size(); i++) {
            brandName = laptops.get(i).getBrand().getName();
            String newModel = laptops.get(i).getModel();
            System.out.print("Original: " + newModel + "\t|");
            if (newModel.equals("G5 5590 | USA US011")
                    || newModel.contains("THINKPAD X1 C7")
                    || newModel.equals("THINKBOOK 15")
                    || newModel.equals("G3 3590")
                    || newModel.equals("N5593")
                    || newModel.equals("V5490 | DN160")
                    || newModel.equals("V5490 | DN164")
                    || newModel.equals("V5490 DN211")
                    || newModel.equals("3593A")
                    || newModel.contains("70211826 - BLACK")) {
                newModel = null;
                System.out.println(newModel);
                continue;
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
                List<String> stringfromRegex = StringHelper.getStringByRegex(regex,newModel);
                if (newModel.contains("42 R5Z6")) {
                    newModel = "42 R5Z6";
                } else if (stringfromRegex.size() != 0) {
                    newModel = stringfromRegex.get(0).replaceAll(" NH","").trim();
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
                //do not thing
            }
            System.out.println(newModel);
        }
    }

    public static void textString(String text) {
        System.out.println(StringHelper.getStringByRegex("[\\d\\w -]+(([(][\\d\\w]+[)])|)", text).get(0).trim());
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
        String text = "THINKPAD X1 C7\n" +
                "81SY00FAVN (FPT)\n" +
                "81NG004PVN\n" +
                "S540-15IML\n" +
                "20RAS01000\n" +
                "THINKBOOK 15\n" +
                "82AU004XVN (FPT)\n" +
                "LENOVO LEGION Y545\n" +
                "81YT001QVN (FPT)\n" +
                "81Y40067VN (FPT)\n" +
                "81Y4006SVN (FPT)\n" +
                "S540-14IML\n" +
                "IDEAPAD 3-14IIL05\n" +
                "20RAS0KX00\n" +
                "82AU004YVN (FPT)\n" +
                "81SY004WVN (FPT)\n" +
                "ZENBOOK UX334FLC-A4096T\n" +
                "PRO DUO UX581GV-H2029T\n" +
                "D509DA-EJ116T\n" +
                "GAMING SCAR GU502GV-AZ079T\n" +
                "ROG-ZEPHYRUS-S GX502GW-AZ129T\n" +
                "UX534FT-A9168T\n" +
                "ASPIRE A514 53 3821 NX.HUSSV.001\n" +
                "ASPIRE A315 54K 37B0 NX.HEESV.00D\n" +
                "NITRO SERIES AN515 54 51X1 NH.Q5ASV.011\n" +
                "PREDATOR TRITON 500 PT515-52-72U2 NH.Q6WSV.001\n" +
                "NITRO SERIES AN515-54-76RK NH.Q59SV.023\n" +
                "NITRO SERIES AN515-54-71UP NH.Q5ASV.008\n" +
                "NITRO SERIES AN515 71HS NH.Q59SV.018 BLACK/FHD\n" +
                "ASPIRE A315 55G 59BC NX.HNSSV.003\n" +
                "ASPIRE A315 23G R33Y NX.HVSSV.001\n" +
                "\n" +
                "ASPIRE A315 23 R0ML NX.HVUSV.004\n" +
                "ASPIRE A315 42 R4XD NX.HF9SV.008\n" +
                "ASPIRE A315 23 R8BA NX.HVUSV.001\n" +
                "ASPIRE A515-53-3153 NX.H6BSV.005\n" +
                "ASPIRE A315 56 37DV NX.HS5SV.001\n" +
                "GRAM 15Z980-G AH55A5\n" +
                "15Z90N-V.AJ55A5\n" +
                "17Z90N-V.AH75A5\n" +
                "14ZD90N-V.AX55A5\n" +
                "14ZD90N-V.AX53A5\n" +
                "GRAM 17Z990-V.AH75A5 (REVIEW CHI TIẾT\n" +
                "14Z90N-V.AJ52A5\n" +
                "15ZD90N-V.AX56A5\n" +
                "OMEN GAMING 15-DH0169TX 8ZR37PA\n" +
                "PROBOOK 440 G7 9MV53PA\n" +
                "PROBOOK 450 G7 9GQ40PA\n" +
                "ELITEBOOK 1050 G1 5JJ65PA\n" +
                "PROBOOK 450 G6 6FG83PA\n" +
                "ENVY X360-AR0072AU 6ZF34PA\n" +
                "PROBOOK 430 G7 9GQ05PA\n" +
                "PROBOOK 430 G7 9GQ02PA\n" +
                "PAVILION 14-CE3018TU 8QN89PA\n" +
                "PROBOOK 450 G7 9GQ27PA\n" +
                "9SD-070VN (SPC)\n" +
                "9SF-1014VN\n" +
                "9SD-224VN (SPC)\n" +
                "A10RB 888VN (SPC)\n" +
                "9SE-223VN\n" +
                "9SF-222VN (VSC)\n" +
                "10SF-285\n" +
                " 9SF-806VN (SPC)\n" +
                "10SFS 076VN (VSC)\n" +
                "A10M-692VN (VSC)\n" +
                "PRESTIGE 15 10S-222VN (SPC)\n" +
                "A10RAS-1041VN (SPC)\n" +
                "10SDK-242VN (SPC)\n" +
                "A10M-068VN\n" +
                "10SER-235VN (VSC)\n" +
                "9SC-1031VN (VSC)\n" +
                "10SCX-292VN (VSC)\n" +
                "AN515-55-58A7 (FPT)\n" +
                "SF314-58-55RJ (FPT)\n" +
                "A315-23G-R33Y (FPT)\n" +
                "A514-53-3821 (FPT)\n" +
                "A315-23-R0ML (FPT)\n" +
                "SF714-52T-7134 BLACK | SF714-52T-710F WHITE\n" +
                "A315-23-R8BA (FPT)\n" +
                "AS A515-55-55HG (DGW)\n" +
                "LAPTOP 3 I7/512GB\n" +
                "AN515-43-R9FD | NTR5\n" +
                "A715-41G-R8KQ (DGW)\n" +
                "LAPTOP 3 I5/256GB\n" +
                "A515-54G-56JG (DGW)\n" +
                "A515-55-55JA (DGW)\n" +
                "SF314-57-54B2 (FPT)\n" +
                "PH315-52-78MG (DGW)\n" +
                "AN515-55-73VQ (DGW)\n" +
                "A514-53-50JA (DGW)\n" +
                "A315-56-59XY (FPT)\n" +
                "AN515-43-R4VJ | NTR5\n" +
                "SF514-54T-55TT (DGW)\n" +
                "A514-53-50P9 (DGW)\n" +
                "AN515-55-5304 (FPT)\n" +
                "AN515-55-70AX (DGW)\n" +
                "A514-53-346U (FPT)\n" +
                "SF314-57G-53T1 (FPT)\n" +
                "SF314-57-52GB (FPT)\n" +
                "SF314-42-R5Z6 (DGW)\n" +
                "A315-56-37DV (DGW)\n" +
                "SF514-54T-793C (DGW)\n" +
                "PH315-52-75R6 (DGW)\n" +
                "SF314-58-39BZ (FPT)\n" +
                "A315-55G-504M (FPT)\n" +
                "10SCXR 248VN (VSC)\n" +
                "10SE-213VN (VSC)\n" +
                "A3DDK-212VN\n" +
                "A10RB-028VN (VSC)\n" +
                "9SF-1019VN (VSC)\n" +
                "A10M-1028VN (SPC)\n" +
                "A10M 693VN (VSC)\n" +
                "10SCXR 038VN\n" +
                "10SER-622VN (VSC)\n" +
                "10SCXR 074VN\n" +
                "A10SC-402VN\n" +
                "A4DCR-052VN\n" +
                "9SE-1000VN\n" +
                "10SCSR 076VN\n" +
                "10SDR-623VN (VSC)\n" +
                "10SCXR-208VN (SPC)\n" +
                "9SC-1030VN (SPC)\n" +
                "9SC-447VN (SPC)\n" +
                "10SCSR-077VN (SPC)\n" +
                "9SG-097VN (SPC)\n" +
                "A515-55-37HD (FPT)\n" +
                "A10M-1053VN\n" +
                "MACBOOK PRO MV912 SA/A 512GB (2019)\n" +
                "MACBOOK PRO MV912 512GB (2019)\n" +
                "U937-FPC04866DK\n" +
                "E749 L00U749VN00000113\n" +
                "U747-FPC07427DK\n" +
                "E549 L00E549VN00000111\n" +
                "E559 L00E559VN00000074\n" +
                "E549 L00E549VN00000110\n" +
                "LATITUDE 5400 42LT540001\n" +
                "INSPIRON 7591 KJ2G41 GREY/VGA/FHD/WIN\n" +
                "LATITUDE 5400 L5400I714WP\n" +
                "LATITUDE 3400 70188730\n" +
                "LATITUDE 7400 42LT740001\n" +
                "LATITUDE 3400 L3400I5HDD\n" +
                "INSPIRON 3493 WTW3M2\n" +
                "LATITUDE 5400 42LT540003\n" +
                "LATITUDE 5400 L5400I714DF\n" +
                "LATITUDE 3400 3400I5HDD8G\n" +
                "XPS 13 7390 70197462\n" +
                "XPS 15 7590 70196707\n" +
                "INSPIRON 3493 N4I5136W\n" +
                "UX334FAC-A4059T\n" +
                "TP412FA-EC123T\n" +
                "S533FA-BQ011T\n" +
                "D409DA-EK151T\n" +
                "TUF GAMING FA506II-AL012T\n" +
                "A412DA-EK144T\n" +
                "P1440FA-FA0420\n" +
                "X509JA-EJ021T\n" +
                "A412FA-EK1187T\n" +
                "S433FA-EB052T\n" +
                "A512DA-EJ406T\n" +
                "M533IA-BQ165T\n" +
                "A412FA-EK734T\n" +
                "X409JA-EK014T\n" +
                "M533IA-BQ132T\n" +
                "UX333FN-A4125T\n" +
                "ZENBOOK FLIP UX362FA-EL205T\n" +
                "S533FA-BQ026T\n" +
                "S333JA-EG003T\n" +
                "S530FA-BQ185T\n" +
                "X509JA-EJ427T\n" +
                "D509DA-EJ285T\n" +
                "S433FA-EB437T\n" +
                "TUF GAMING FX505DT-AL003T\n" +
                "A512DA-EJ422T\n" +
                "S530FA-BQ186T\n" +
                "TUF GAMING FA506IV-HN202T\n" +
                "TUF GAMING FA706II-H7125T\n" +
                "A512FA-EJ1281T\n" +
                "A412DA-EK346T\n" +
                "DUO UX481FL-BM048T\n" +
                "TUF GAMING FX505DD-AL186T\n" +
                "X409FA-EK156T\n" +
                "GAMING G731-VEV089T\n" +
                "S431FA-EB077T\n" +
                "UX434FAC-A6116T\n" +
                "S533JQ-BQ024T\n" +
                "UX333FA-A4115T\n" +
                "S433FA-EB053T\n" +
                "TUF GAMING FA506II-AL016T\n" +
                "X509FJ-EJ155T\n" +
                "X509JP-EJ013T\n" +
                "UX434FLC-A6143T\n" +
                "UM462DA-AI091T\n" +
                "X409FA-EK199T\n" +
                "D409DA-EK152T\n" +
                "VIVOBOOK X409FA-EK469T\n" +
                "TUF GAMING FX505DT-AL118T\n" +
                "UX461UA-E1147T\n" +
                "GAMING GF75 THIN 10SCXR 248VN\n" +
                "GE75 RAIDER 10SFS 076VN\n" +
                "GAMING GF75 THIN 10SCSR 208VN\n" +
                "MODERN 14 A10RB 888VN\n" +
                "TUF GAMING FX705DT-H7138T\n" +
                "X509FA-EJ199T\n" +
                "A512DA-EJ829T\n" +
                "VIVOBOOK P4103FA-EB226T\n" +
                "S531FA-BQ104T\n" +
                "UX333FA-A4118T\n" +
                "GAMING GF65 THIN 10SDR 623VN\n" +
                "X509JA-EJ020T\n" +
                "TUF GAMING FA706IU-H7133T\n" +
                "S530FA-BQ431T\n" +
                "GF63 THIN 9SCSR 076VN\n" +
                "GAMING GF75 THIN 10SCXR 038VN\n" +
                "TUF GAMING FA506IU-AL127T\n" +
                "S531FA-BQ105T\n" +
                "MODERN 14 A10RAS 1041VN\n" +
                "GE75 RAIDER 9SE 688VN\n" +
                "GAMING GL65 LEOPARD 10SDK 242VN\n" +
                "GF75 THIN 8SC 025VN\n" +
                "GAMING GL65 LEOPARD 10SEK 235VN\n" +
                "GAMING GF63 THIN 9SC 1030VN\n" +
                "GS66 STEALTH 10SE 213VN\n" +
                "GAMING GF63 THIN 10SCXR 292VN\n" +
                "GF63 THIN 9SC 071VN\n" +
                "BRAVO 15 A4DCR 052VN\n" +
                "A412FA-EK287T\n" +
                "S530FN-BQ128T\n" +
                "GAMING GF65 THIN 10SER 622VN\n" +
                "GF63 THIN 9RCX 645VN\n" +
                "GF63 THIN 10SCSR 077VN\n" +
                "GS65 STEALTH 9SD 1409VN\n" +
                "GAMING GF75 THIN 9RCX-432VN\n" +
                "PRESTIGE 15 A10SC 222VN\n" +
                "GF63 THIN 9SC 070VN\n" +
                "GE63 8SE 280VN RAIDER\n" +
                "GF63 THIN 9RCX 646VN\n" +
                "GF63 THIN 10SCXR 074VN\n" +
                "GE66 RAIDER 10SF 044VN\n" +
                "GF63 8RD 242VN\n" +
                "GF63 THIN 9SCXR 075VN\n" +
                "GT75 TITAN 8RF 231VN\n" +
                "GF63 THIN 9SC 1031VN\n" +
                "P65 8RF 488VN\n" +
                "NITRO SERIES AN515 55 58A7 NH.Q7RSV.002\n" +
                "ASPIRE A315 56 59XY NX.HS5SV.003\n" +
                "ASPIRE A315 54K 36X5 NX.HEESV.00J\n" +
                "SWIFT 3 SF314 58 39BZ NX.HPMSV.007\n" +
                "ASPIREA514 53 50P9 NX.HUSSV.004\n" +
                "SWIFT 3 SF314 57 54B2 NX.HJKSV.001\n" +
                "ASPIRE A514 53 346U NX.HUSSV.005\n" +
                "SWIFT 3 42 R5Z6 NX.HSESV.001\n" +
                "SWIFT 3 SF314 57 52GB NX.HJFSV.001\n" +
                "PREDATOR TRITON 500 PT515-52-75FR NH.Q6YSV.002\n" +
                "NITRO SERIES AN515 55 73VQ NH.Q7RSV.001\n" +
                "NITRO SERIES AN515 595DNH.Q59SV.025\n" +
                "SWIFT 3 SF314 56 50AZ NX.H4CSV.008\n" +
                "SWIFT 3 SF314 41 R4J1 NX.HFDSV.001\n" +
                "SWIFT 3 SF314 41 R8G9 NX.HFDSV.003\n" +
                "ASPIRE A315 54 368N NX.HM2SV.004\n" +
                "SWIFT 5 SF514-53T-58PN NX.H7HSV.001 GOLD/FHD/TOUCH\n" +
                "ASPIRE A514-51-525E NX.H6VSV.002\n" +
                "SWIFT 3 SF314 41 R8VS NX.HFDSV.002\n" +
                "SWIFT 3 SF314 57G 53T1 NX.HJESV.001\n" +
                "NITRO SERIES AN515 55 70AX NH.Q7NSV.001\n" +
                "ASPIRE A515-53-330E NX.H6CSV.001\n" +
                "ASPIRE A515 54G 56JG NX.HVGSV.002\n" +
                "ASPIRE A514 52 516K NX.HMHSV.002\n" +
                "ASPIRE 7 A715 41G R8KQ NH.Q8DSV.001\n" +
                "SWIFT 3 SF314 58 55RJ NX.HPMSV.006\n" +
                "SWIFT 3 SF314 56G 78QS NX.HAQSV.001\n" +
                "ASPIRE A515 55 55HG NX.HSMSV.004\n" +
                "NITRO SERIES AN515-54-779S NH.Q5BSV.009\n" +
                "PREDATOR TRITON 500 PT515-52-78PN NH.Q6XSV.001\n" +
                "SWIFT 5 SF514-53T-51EX NX.H7KSV.001 GREY/FHD/TOUCH\n" +
                "NITRO SERIES AN515 55 5304 NH.Q7NSV.002\n" +
                "SWIFT 3 SF314-54-58KB NX.GXZSV.002\n" +
                "NITRO SERIES AN515 43 R9FD NH.Q6ZSV.003\n" +
                "ASPIRE A514 53 50JA NX.HUSSV.002\n" +
                "PAVILION 15-CS3014TU 8QP20PA\n" +
                "15S-DU1040TX 8RE77PA\n" +
                "348 G7 9PG98PA\n" +
                "15S-FQ1021TU 8VY74PA\n" +
                "PAVILION 15-CS3011TU 8QN96PA\n" +
                "PAVILION 14-CE3013TU 8QN72PA\n" +
                "PAVILION GAMING 15-DK0233TX 8DS86PA\n" +
                "PROBOOK 450 G6 5YM80PA\n" +
                "PAVILION 14-E2036TU 6YZ19PA\n" +
                "348 G7 9PG79PA\n" +
                "ENVY 13-AQ1057TX 8XS68PA\n" +
                "PAVILION X360 14-DW0062TU 19D53PA\n" +
                "PAVILION X360 14-DH1139TU 8QP77PA\n" +
                "445R G6 9VC64PA\n" +
                "14S-DQ1065TU9TZ44PA\n" +
                "PROBOOK 440 G7 9GQ22PA\n" +
                "PROBOOK 430 G7 9GQ10PA\n" +
                "PAVILION 15-CS3012TU 8QP30PA\n" +
                "PROBOOK 440 G7 9GQ14PA\n" +
                "445 G6 6XP98PA\n" +
                "PROBOOK 450 G7 9MV54PA\n" +
                "ENVY 13-AQ1021TU 8QN79PA\n" +
                "PAVILION 15-CS3116TX 9AV24PA\n" +
                "ENVY 13-BA0046TU 171M7PA\n" +
                "15S-FQ1022TU 8VY75PA\n" +
                "348 G7 9PG95PA\n" +
                "PAVILION GAMING 15-DK0231TX 8DS89PA\n" +
                "PAVILION X360 14-DH0103TU 6ZF24PA\n" +
                "PROBOOK 450 450 G7 9GQ39PA\n" +
                "348 G7 9PG85PA\n" +
                "PAVILION 14-CE3026TU 8WH93PA\n" +
                "PAVILION 14-CE2038TU 6YZ21PA\n" +
                "PAVILION GAMING 15-DK0000TX 7HR10PA\n" +
                "15S-DU0126TU 1V888PA\n" +
                "ENVY 13-AQ1022TU 8QN69PA\n" +
                "PAVILION 15-CS3061TX 8RE83PA\n" +
                "PROBOOK 450 G7 9GQ43PA\n" +
                "PAVILION 14-CE3019TU 8QP00PA\n" +
                "PAVILION GAMING 15-DK0232TX 8DS85PA\n" +
                "PROBOOK 440 G7 9GQ11PA\n" +
                "PROBOOK 430 G7 9GQ08PA\n" +
                "PROBOOK 450 G7 9GQ30PA\n" +
                "250 G7 15H25PA\n" +
                "15S-DU1037TX 8RK37PA\n" +
                "ENVY 13-AQ0025TU 6ZF33PA\n" +
                "PAVILION 15-CS2032TU 6YZ04PA\n" +
                "ENVY 13-AQ1023TU 8QN84PA\n" +
                "PAVILION 15-CS3063TX 8RK42PA\n" +
                "PAVILION 15-CS3015TU 8QP15PA\n" +
                "PAVILION 15-CS3010TU 8QN78PA\n" +
                "15S-FQ1017TU 8VY69PA\n" +
                "240 G7 3K075PA\n" +
                "PAVILION 14-CE3014TU 8QP03PA\n" +
                "14S-DK0117AU 8TS51PA\n" +
                "PAVILION 14-CE3029TU 8WH94PA\n" +
                "15S-FQ1106TU 193Q2PA\n" +
                "PROBOOK 440 G7 9GQ24PA\n" +
                "PAVILION 15-CS3008TU 8QP02PA\n" +
                "14S-DQ1022TU 8QN41PA\n" +
                "15S-FQ1107TU 193Q3PA\n" +
                "PAVILION 15-CS3060TX 8RJ61PA\n" +
                "PAVILION 15-CS3119TX 9FN16PA\n" +
                "ENVY 13-AQ1048TU 8XS70PA\n" +
                "348 G7 9PH16PA\n" +
                "ENVY 13-AQ1047TU 8XS69PA\n" +
                "14S-DQ1100TU 193U0PA\n" +
                "PROBOOK 450 G7 9GQ34PA\n" +
                "PAVILION X360 14-DW0060TU 195M8PA\n" +
                "ZENBOOK FLIP UX362FA-EL206T\n" +
                "S533JQ-BQ015T\n" +
                "GAMING G531G_N-VAZ160T\n" +
                "UX434FL-A6212T\n" +
                "A412FA-EK155T\n" +
                "UX434FLC-A6173T\n" +
                "D409DA-EK095T\n" +
                "S433FA-EB054T\n" +
                "S533FA-BQ025T\n" +
                "TUF GAMING FX505DU-AL070T\n" +
                "X409FA-EK098T\n" +
                "UX534FTC-A9169T\n" +
                "A412FA-EK156T\n" +
                "DUO UX481FL-BM049T\n" +
                "GAMING G531GT-AL007T\n" +
                "UX534FTC-AA189T\n" +
                "UX333FN-A4124T\n" +
                "ROG STRIX SCAR G532L-VAZ044T\n" +
                "GAMING G731GT-H7114T\n" +
                "A412FA-EK223T\n" +
                "TUF GAMING FA506IH-AL018T\n" +
                "M533IA-BQ162T\n" +
                "ROG STRIX ROG STRIX G712L-UEV075T\n" +
                "UX434FAC-A6064T\n" +
                "B9450FA-BM0324T\n" +
                "B9450FA-BM0616R\n" +
                "A512FL-EJ165T\n" +
                "M533IA-BQ164T\n" +
                "ROG STRIX G712L-VEV055T\n" +
                "X509FJ-EJ153T\n" +
                "INSPIRON3593C P75F013N93C\n" +
                "VOSTRO 3590A P90F002N93A\n" +
                "GAMING G3 3590 70191515\n" +
                "VOSTRO 5490A P116G001\n" +
                "INSPIRON 3593A P75F013N93A\n" +
                "VOSTRO 5581 70194501\n" +
                "INSPIRON 3593B P75F013N93B\n" +
                "INSPIRON 5491 C1JW82\n" +
                "GAMING G3 3590 70203973\n" +
                "LATITUDE 3400 L3400I5SSD4G\n" +
                "INSPIRON5391 N3I3001W\n" +
                "GAMING G5 5590M P82F001\n" +
                "INSPIRON 7490 N4I5106W\n" +
                "INSPIRON 5584 CXGR01\n" +
                "XPS 15 7590 70196711\n" +
                "INSPIRON 3493 N4I7131W\n" +
                "INSPIRON 5491 N4TI5024W\n" +
                "INSPIRON 7391 P113G001T91\n" +
                "LATITUDE 5490 70205623\n" +
                "INSPIRON 3593 70197460\n" +
                "XPS 15 7590 70196708\n" +
                "LATITUDE 3400 L3400I5SSD\n" +
                "XPS 13 7390 04PDV1\n" +
                "INSPIRON 3593 70211828\n" +
                "VOSTRO 3590 GRMGK1\n" +
                "LATITUDE 7400 70194805\n" +
                "GAMING G7 7590Z P82F001\n" +
                "VOSTRO 3580I P75F010\n" +
                "GAMING G5 5590 4F4Y41\n" +
                "LATITUDE 5400 70194817\n" +
                "GAMING G3 3590 N5I5517W\n" +
                "GAMING G5 5590 4F4Y31\n" +
                "VOSTRO 5490 70197464\n" +
                "INSPIRON 5491 C1JW81\n" +
                "6YZ17PA\n" +
                "5NK55PA\n" +
                "HP 348 G7 9PH01PA\n" +
                "8RK37PA\n" +
                "9GQ34PA\n" +
                "5JR57PA\n" +
                "9AV24PA\n" +
                "8QP02PA\n" +
                "8QP75PA\n" +
                "8ZR43PA\n" +
                "9GQ22PA\n" +
                "8QN89PA\n" +
                "HP 348 G7 9PH09PA\n" +
                "8VY74PA\n" +
                "8QN78PA\n" +
                "5YM63PA\n" +
                "6YZ13PA\n" +
                "8QP15PA\n" +
                "8QN68PA\n" +
                "6YZ15PA\n" +
                "9PG92PA\n" +
                "8QN41PA (VSC)\n" +
                "8RE77PA\n" +
                "8WH93PA (GOLD) |  8WH94PA (PINK)\n" +
                "8QN30PA\n" +
                "9GQ16PA\n" +
                "9PG86PA\n" +
                "9PH06PA\n" +
                "9GQ24PA\n" +
                "HP 348 G7 9PG98PA\n" +
                "8QN72PA\n" +
                "8VY75PA\n" +
                "HP 348 G7 9PG95PA\n" +
                "8QP20PA\n" +
                "HP 348 G7 9PG93PA\n" +
                "8RJ61PA\n" +
                "193Q3PA\n" +
                "DQ1065TU (VSC)\n" +
                "348 G7 9PH01PA\n" +
                "8QP00PA\n" +
                "PAVILION 15-CS2033TU 6YZ14PA\n" +
                "9GQ07PA\n" +
                "PAVILION X360 14-DH0104TU 6ZF32PA\n" +
                "9MV54PA\n" +
                "PROBOOK 430 G7 9GQ01PA\n" +
                "8VY69PA\n" +
                "348 G7 9PG86PA\n" +
                "8RE83PA\n" +
                "PROBOOK 440 G7 9MV57PA\n" +
                "9GQ38PA\n" +
                "9GQ43PA\n" +
                "PROBOOK 430 G7 9GQ07PA\n" +
                "PAVILION 14-CE3015TU 8QN68PA\n" +
                "193Q2PA\n" +
                "15S-DU0038TX 6ZF72PA\n" +
                "8QN33PA (VSC)\n" +
                "6YZ21PA\n" +
                "ELITEBOOK 745 G5 5ZU71PA\n" +
                "ELITEBOOK X360 1040 G6 6QH36AV\n" +
                "9PH13PA\n" +
                "8QN96PA\n" +
                "14S-CF1040TU 7PU14PA\n" +
                "9PH16PA\n" +
                "348 G7 9PG93PA\n" +
                "9GQ14PA\n" +
                "OMEN GAMING 15-DH0172TX 8ZR42PA\n" +
                "8XS69PA\n" +
                "PROBOOK 450 G7 9GQ26PA\n" +
                "8RK42PA\n" +
                "445R G6 9VC65PA\n" +
                "8QP77PA\n" +
                "348 G5 7CS43PA\n" +
                "8QN79PA\n" +
                "PAVILION 14-CE2034TU 6YZ17PA\n" +
                "8QN69PA\n" +
                "PAVILION 14-CE3037TU 8ZR43PA\n" +
                "9PH23PA\n" +
                "PAVILION 15-CS2031TU 6YZ03PA\n" +
                "9MV57PA\n" +
                "ENVY X360-AR0071AU 6ZF30PA\n" +
                "9TN61PA\n" +
                "PAVILION GAMING 15-EC0050AX 9AV28PA\n" +
                "PAVILION GAMING 15-DK0001TX 7HR11PA\n" +
                "9PH19PA\n" +
                "9GQ32PA\n" +
                "455 G6 6XA87PA\n" +
                "8XS70PA\n" +
                "348 G7 9PH21PA\n" +
                "8DS86PA (FPT)\n" +
                "PROBOOK 430 G7 9GQ06PA\n" +
                "8DS89PA\n" +
                "348 G5 7CS45PA\n" +
                "ELITEBOOK 830 G6 7QR67PA\n" +
                "PROBOOK 450 G7 9GQ38PA\n" +
                "ELITEBOOK 735 G5 5ZU72PA\n" +
                "SPECTRE X360 CONVERTIBLE W0181TU 8YQ35PA\n" +
                "ELITEBOOK 745 G5 5ZU69PA\n" +
                "MACBOOK PRO MVVM2 SA/A 1TB (2019)\n" +
                "MACBOOK PRO MVVL2 SA/A 512GB (2019)\n" +
                "MACBOOK PRO MVVK2 SA/A 1TB (2019)\n" +
                "MACBOOK PRO MVVJ2 SA/A 512GB (2019)\n" +
                "A4060T (SPC)\n" +
                "EB171T (SPC)\n" +
                "AL003T (FPT)\n" +
                "EJ101T\n" +
                "EJ099T (SPC)\n" +
                "EB525T\n" +
                "EK378T\n" +
                "BQ420T\n" +
                "EJ117T (SPC)\n" +
                "EJ286T (SPC)\n" +
                "FX505GT-AL003T\n" +
                "E4028T (FPT)\n" +
                "AL007T (SPC)\n" +
                "FA506IH-AL018T (SPC)\n" +
                "A6064T (VSC)\n" +
                "EB075T\n" +
                "EJ163T\n" +
                "EJ158T (SPC)\n" +
                "BQ266T ( VSC )\n" +
                "EJ563T\n" +
                "EJ440T\n" +
                "BQ319T (VSC)\n" +
                "EJ1281T\n" +
                "EJ1170T\n" +
                "EK388T (SPC)\n" +
                "S433FA EB052T | S433FA EB053T | S433FA EB054T\n" +
                "EY116T (SPC)\n" +
                "EJ406T\n" +
                "EB163T\n" +
                "EK056T\n" +
                "EK346T\n" +
                "EK144T\n" +
                "EK380T | EK223T | EK342T\n" +
                "FA506II-AL012T (DGW)\n" +
                "F571GT-BQ266T ( HÀNG TRƯNG BÀY )\n" +
                "EK100T\n" +
                "EJ285T (SPC)\n" +
                "P3540FA-BR0539\n" +
                "EJ418T\n" +
                "A512FL-EJ569T\n" +
                "EJ570T\n" +
                "EK098T\n" +
                "EK734T\n" +
                "FA706II-H7125T (FPT)\n" +
                "EJ837T\n" +
                "EJ116T\n" +
                "EJ571T\n" +
                "EK377T\n" +
                "FA506IU-AL127T (DGW)\n" +
                "GA502IU-AL007T (DGW)\n" +
                "G531GT-AL017T (DGW)\n" +
                "FA506IV-HN202T (DGW)\n" +
                "GA401II-HE152T\n" +
                "AA189T (VSC)\n" +
                "AZ082R\n" +
                "G512-IAL013T (SPC)\n" +
                "FA506II-AL016T (FPT)\n" +
                "A6143T (VSC)\n" +
                "GX701GXR HG142T (DGW)\n" +
                "GX701GXR H6072T (DGW)\n" +
                "GX502GW-AZ129T (DGW)\n" +
                "A9168T\n" +
                "WAZ209T (FPT)\n" +
                "G731GN-WH100T (DGW)\n" +
                "G512L-VAZ068T (SPC)\n" +
                "GA401II-HE155T\n" +
                "FA706IU-H7133T (FPT)\n" +
                "A6173T (VSC)\n" +
                "VAL319T (SPC)\n" +
                "VOSTRO 5490 V4I5106W\n" +
                "VOSTRO 3590 GRMGK3\n" +
                "VOSTRO 5590 70197465\n" +
                "VOSTRO 3590A P90F002N93A/P75F010N90A\n" +
                "VOSTRO 3490 2N1R82\n" +
                "INSPIRON 3593D P75F013\n" +
                "GAMING G3 3590 N5I5518W\n" +
                "INSPIRON 5491 C9TI7007W\n" +
                "INSPIRON 5584Y P85F001\n" +
                "INSPIRON 5593 70196703\n" +
                "VOSTRO 3590 V5I3101W\n" +
                "INSPIRON 3493 N4I5122W/WA\n" +
                "VOSTRO 5490B P116G001V90B\n" +
                "VOSTRO 5581-70175950\n" +
                "VOSTRO 3490 70211829\n" +
                "INSPIRON5391 70197461\n" +
                "INSPIRON 7391 N3TI5008W\n" +
                "INSPIRON 3493 N4I5122W\n" +
                "INSPIRON 5593 7WGNV1\n" +
                "INSPIRON 5490 FMKJV1\n" +
                "INSPIRON 3593 70211826\n" +
                "VOSTRO 5590A P88F001N90A\n" +
                "INSPIRON 5593 N5I5461W\n" +
                "INSPIRON 3593 70205743\n" +
                "VOSTRO 5590 HYXT91\n" +
                "INSPIRON 5593A P90F002N93A\n" +
                "INSPIRON5490 70196706\n" +
                "VOSTRO 3590 GRMGK2\n" +
                "INSPIRON 7490 6RKVN1\n" +
                "INSPIRON 3593 70205744\n" +
                "GAMING G3 3590 N5I517WF\n" +
                "VOSTRO 3490 70207360\n" +
                "INSPIRON 7591 N5I5591W GREY/VGA/FHD/WIN\n" +
                "VOSTRO 5490 V4I3101W\n" +
                "INSPIRON 5593 N5I5513W\n" +
                "THINKPAD T14S GEN 1 20T0S01P00\n" +
                "THINKPAD E15 20RDS0DU00\n" +
                "IDEAPAD S340 15IWL 81N800RSVN\n" +
                "YOGA S730 13IWL 81J0008TVN\n" +
                "THINKBOOK 13S IML 20RR004TVN\n" +
                "THINKPAD T490S 20NYSBHC00\n" +
                "THINKPAD E590 20NBS07000 PA\n" +
                "THINKBOOK 13S IML 20RR004UVN\n" +
                "THINKPAD E590 20NBS00100 PA\n" +
                "THINKPAD L390 20NRS0NC00\n" +
                "THINKBOOK 13S IWL 20R900DJVN\n" +
                "THINKPAD T14S GEN 1 20T0S01N00\n" +
                "THINKPAD L13 20R30025VA\n" +
                "THINKPAD X1 CARBON 7 20R1S01N00\n" +
                "THINKBOOK 15 IML 20RW0091VN\n" +
                "LEGION GAMING Y540 15IRH 81SY004WVN\n" +
                "IDEAPAD 5 14IIL05 81YH00ENVN\n" +
                "MACBOOK PRO MPXR2 128GB (2017)\n" +
                "THINKBOOK 13S IML 20RR00B8VN\n" +
                "THINKPAD X390 20NYSBHC00\n" +
                "THINKPAD L13 20R30023VA\n" +
                "IDEAPAD S340 14IIL 81VV003TVN\n" +
                "IDEAPAD C340 14API 81N6007JVN\n" +
                "THINKPAD L390 20NRS00100 PA\n" +
                "THINKPAD T14S GEN 1 20T0S01R00\n" +
                "THINKPAD X390 20SDS0PD00\n" +
                "THINKPAD E490S 20NGS01K00\n" +
                "IDEAPAD C340 14API 81N600A3VN\n" +
                "THINKPAD L380 20M5S01200\n" +
                "IDEAPAD S340 15API 81NC00G8VN\n" +
                "MACBOOK PRO MR9U2 256GB (2018)\n" +
                "MACBOOK PRO MV902 SA/A 256GB (2019)\n" +
                "MACBOOK PRO MR9Q2 256GB (2018)\n" +
                "MACBOOK PRO MPXQ2 128GB (2017)\n" +
                "MACBOOK PRO MV902 256GB (2019)\n" +
                "MACBOOK PRO MV922 SA/A 256GB (2019)\n" +
                "MACBOOK PRO MV922 256GB (2019)\n" +
                "IDEAPAD S540 14IML 81NF0062VN\n" +
                "THINKPAD E15 20RDS0DM00\n" +
                "THINKBOOK 14 IIL 20SL00K3VN\n" +
                "THINKBOOK 14 IML 20RV00B8VN\n" +
                "IDEAPAD S145 14IWL 81W6001GVN\n" +
                "THINKBOOK 15 IML 20SM00A1VN\n" +
                "THINKPAD L390 20NRS00100\n" +
                "IDEAPAD L340 15IRH 81LK00VUVN\n" +
                "IDEAPAD 5 15IIL05 81YK004VVN\n" +
                "THINKBOOK 14S IML 20RS004AVN\n" +
                "IDEAPAD C340 15IIL 81XJ0027VN\n" +
                "S730 13IWL 81J0008SVN\n" +
                "IDEAPAD S145 15IWL 81W8001YVN\n" +
                "THINKPAD E14 20RAS0KX00\n" +
                "THINKPAD X1 CARBON 7 20R1S00100\n" +
                "IDEAPAD S145 15IWL 81MV00F0VN\n" +
                "IDEAPAD S340 15IIL 81VW00A8VN\n" +
                "THINKBOOK 14 IML 20RV00BGVN\n" +
                "IDEAPAD S340 15IWL 81N800EVVN\n" +
                "IDEAPAD S145 15IWL 81UT00DMVN\n" +
                "V14 14IIL 82C400X3VN\n" +
                "IDEAPAD S540 15IML 81NG004TVN\n" +
                "THINKBOOK 15 IML 20RW008WVN\n" +
                "IDEAPAD S540 15IML 81NG004RVN\n" +
                "THINKPAD E14 20RAS01000\n" +
                "THINKPAD E490S 20NGS01N00\n" +
                "V3590 GRMGK3\n" +
                "N3493A\n" +
                "VOSTRO 3590A\n" +
                "N3593 DN206B | DN206S\n" +
                "N5490 | DN162G | DN162S\n" +
                "FMKJV1\n" +
                "3580I\n" +
                "70196703\n" +
                "N3493 WTW3M2\n" +
                "HYXT91\n" +
                "N5593  7WGNV1\n" +
                "70196714\n" +
                "N3593B\n" +
                "70197465\n" +
                "70197458\n" +
                "V3590B\n" +
                "N3493 N4I5122WA\n" +
                "V5490 DN201U\n" +
                "LD580 DN212\n" +
                "N5I5461W\n" +
                "VOSTRO 5490B\n" +
                "V4I5106W\n" +
                "N5391 DN205\n" +
                "N3493 N4I5122W\n" +
                "70197457\n" +
                "N3593C\n" +
                "V3490 70211829\n" +
                "V3590 DN204\n" +
                "N5I5513W\n" +
                "V5481 | DN158\n" +
                "N5490 | DN163\n" +
                "LATITUDE E7390 | USA\n" +
                "70196712\n" +
                "V3490-70207360\n" +
                "DN128 | DN129\n" +
                "GRMGK1\n" +
                "N5I5402W\n" +
                "N4I5136W\n" +
                "V3590 V5I3101W\n" +
                "V3490-2N1R82\n" +
                "V5590 | DN209\n" +
                "N3I3001W\n" +
                "V4I3101W\n" +
                "N4I7131W\n" +
                "V5490 DN211\n" +
                "3593A\n" +
                "N3593 70205743\n" +
                "V5481 | DN134\n" +
                "V5490 | DN164\n" +
                "G5-5500-3085GTX4G-FHD\n" +
                "V5490 | DN160\n" +
                "GRMGK2\n" +
                "N4TI5024W\n" +
                "G3 3590\n" +
                "N5I5517W\n" +
                "C1JW81\n" +
                "70211826 - BLACK | 70211828 - SILVER\n" +
                "KJ2G41\n" +
                "N7501-3085GTX4G-FHD\n" +
                "N5593A\n" +
                "N4I5106W\n" +
                "ALIENWARE M15 USA\n" +
                "XPS 9380 USA\n" +
                "70197461\n" +
                "N7590Z\n" +
                "6RKVN1\n" +
                "4F4Y41\n" +
                "XPS 7390 USA REFURBISHED\n" +
                "N5593\n" +
                "N5I5591W\n" +
                "C9TI7007W\n" +
                "G5-5500-7585GTX6G-FHD\n" +
                "70197462\n" +
                "70197464\n" +
                "70191515\n" +
                "N5401-6585MX2G-FHD\n" +
                "70196708\n" +
                "XPS 7390 USA OUTLET NEW\n" +
                "XPS 7390 OUTLET NEW\n" +
                "70196707\n" +
                "N5490 | DN161G | DN161S\n" +
                "7501-7585GTX4G-FHD\n" +
                "N7391A\n" +
                "XPS 7390 USA\n" +
                "70196706\n" +
                "G5 5590 USA\n" +
                "N3TI5008W\n" +
                "N5401-3582MX2G-FHD\n" +
                "V5590A\n" +
                "N5I5518W\n" +
                "4F4Y42\n" +
                "G3-3500-3082GTX4G-FHD\n" +
                "G5 5590 | USA US011\n" +
                "70197459\n" +
                "N5300-2182SG-FHD\n" +
                "DN165 PRO\n" +
                "V5590 DN208";
        String[] result = text.split("\n");
        return result;
    }
}
