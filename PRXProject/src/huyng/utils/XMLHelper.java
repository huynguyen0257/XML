package huyng.utils;

import com.sun.xml.internal.stream.events.EndElementEvent;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLHelper {
    public static void main(String[] args) throws IOException, XMLStreamException {
//        String document = (parseHTML("https://tiki.vn/nha-sach-tiki/c8322?src=c.8322.hamburger_menu_fly_out_banner&_lc=Vk4wMzkwMTYwMDU=&utm_expid=.tWaaGq4BT36INEYHSMsXpw.0&utm_referrer=https%253A%252F%252Ftiki.vn%252Fnha-sach-tiki%252Fc8322%253Fsrc%253Dc.8322.hamburger_menu_fly_out_banner&page=2", "div", "class", "product-box-list"));
        String document = getDocument(getBufferedReaderForURL("https://tiki.vn/nha-sach-tiki/c8322?src=c.8322.hamburger_menu_fly_out_banner&_lc=Vk4wMzkwMTYwMDU=&utm_expid=.tWaaGq4BT36INEYHSMsXpw.0&utm_referrer=https%253A%252F%252Ftiki.vn%252Fnha-sach-tiki%252Fc8322%253Fsrc%253Dc.8322.hamburger_menu_fly_out_banner&page=2")
                , "<div class=\"product-box-list\"", "div", new String[]{"Của Việc \"Đếch\" Quan"});
        Iterator<XMLEvent> a = autoAddMissingEndTag(parseStringToXMLEventReader(document));
    }

    public static String getDocument(BufferedReader reader, String beginSignal, String tag, String[] IGNORE_TEXTS) throws IOException {
        String devide = " ";
//            devide = "\n";
        String line = "";
        String document = "";
        int tagCount = 0;
        boolean isFound = false;
        //make sure we don't have case like this <div><div></div></div>
        while ((line = reader.readLine()) != null) {
            //check the begin tag
            if (!isFound && document.length() == 0 && line.contains(beginSignal)) {
                isFound = true;
            }
            if (isFound && line.contains("<" + tag)) {
                tagCount = tagCount + getAllMatches(line, "<" + tag);
            }
            if (tagCount > 0 && line.contains("</" + tag + ">")) {
                tagCount = tagCount - getAllMatches(line, "</" + tag + ">");
                if (tagCount == 0) {
                    if (IGNORE_TEXTS != null) {
                        for (String ignore_text : IGNORE_TEXTS) {
                            line = line.replaceAll(ignore_text, "");
                        }
                    }
                    document += line.trim() + devide;
                    isFound = false;
                }
            }
            if (isFound) {
                if (IGNORE_TEXTS != null) {
                    for (String ignore_text : IGNORE_TEXTS) {
                        line = line.replaceAll(ignore_text, "");
                    }
                }
                document += line.trim() + devide;
            }
        }
        document = document.replaceAll("&", "và");
        document = document.replaceAll("<input.*?>", "");
        document = document.replaceAll("<br.*?>", "");
        document = document.replaceAll("<!--.*?-->", "");
        document = document.replaceAll("<style.*?</style>", "");
        document = document.replaceAll("<script.*?</script>", "");

        return document;
    }

    private static Iterator<XMLEvent> autoAddMissingEndTag(XMLEventReader reader) {
        ArrayList<XMLEvent> events = new ArrayList<>();
        int endTagMarker = 0;
        while (endTagMarker >= 0) {
            XMLEvent event = null;
            try {
                event = reader.nextEvent();
            } catch (XMLStreamException e) {
                String msg = e.getMessage();
                String msgErrorString = "The element type \"";
                if (msg.contains(msgErrorString)) {
                    String missingTagName = msg.substring(msg.indexOf(msgErrorString) + msgErrorString.length(), msg.indexOf("\" must be terminated"));
                    EndElement missingTag = new EndElementEvent(new QName(missingTagName));
                    event = missingTag;
                }
            } catch (NullPointerException e) {
                break;
            }

            if (event != null) {
                if (event.isStartElement()) endTagMarker++;
                else if (event.isEndElement()) endTagMarker--;
                if (endTagMarker >= 0) events.add(event);
            }
        }
        return events.iterator();
    }

    private static int getAllMatches(String text, String regex) {
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        return m.groupCount();
    }

    protected static XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] buteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }

    protected static BufferedReader getBufferedReaderForURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }
}
