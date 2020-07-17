package huyng.utils;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
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
    public static ByteArrayOutputStream getByteArrayFromString(String string) throws UnsupportedEncodingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(string.getBytes("UTF-8").length);
        outputStream.write(string.getBytes("UTF-8"), 0, string.getBytes("UTF-8").length);





        return outputStream;
    }

    public static String getCPUFromXMLString(String xmlString) {
        return StringHelper.getStringByRegex("<cpu>.*?</cpu>", xmlString).get(0).replaceAll("</?cpu>", "");
    }
    public static String getRamFromXMLString(String xmlString) {
        return StringHelper.getStringByRegex("<ram>.*?</ram>", xmlString).get(0).replaceAll("</?ram>", "");
    }
    public static String getLcdFromXMLString(String xmlString) {
        return StringHelper.getStringByRegex("<lcd>.*?</lcd>", xmlString).get(0).replaceAll("</?lcd>", "");
    }
}
