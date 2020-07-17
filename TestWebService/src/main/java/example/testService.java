package example;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/getXML")
public class testService {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.APPLICATION_XML)
    public String getClichedMessage() {
        // Return some cliched textual content
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<laptops>\n" +
                "    <laptop>\n" +
                "        <id>1441</id>\n" +
                "        <name>HP Pavilion 14-ce2038TU 6YZ21PA : i5-8265U | 4GB RAM | 1TB HDD | UHD Graphics 620 | 14.0 FHD | Win10 | Pink</name>\n" +
                "        <model>6YZ21PA</model>\n" +
                "        <price>13190000</price>\n" +
                "        <weight>1.63</weight>\n" +
                "        <weightMark>0.28817286049728774</weightMark>\n" +
                "        <image>https://kimlongcenter.com/upload/product/3590.jpg</image>\n" +
                "        <brand>\n" +
                "            <id>34</id>\n" +
                "            <name>Laptop HP</name>\n" +
                "        </brand>\n" +
                "        <processor>\n" +
                "            <id>299</id>\n" +
                "            <brand>Intel</brand>\n" +
                "            <name>Intel® Core™ i5-8265U </name>\n" +
                "            <model>8265U </model>\n" +
                "            <core>4</core>\n" +
                "            <thread>8</thread>\n" +
                "            <baseClock>1.6</baseClock>\n" +
                "            <boostClock>3.9</boostClock>\n" +
                "            <cache>6.0</cache>\n" +
                "            <count>51</count>\n" +
                "            <mark>-0.3052259764181777</mark>\n" +
                "        </processor>\n" +
                "        <ram>\n" +
                "            <count>185</count>\n" +
                "            <id>6</id>\n" +
                "            <mark>-0.7857986313497338</mark>\n" +
                "            <memory>4</memory>\n" +
                "        </ram>\n" +
                "        <monitor>\n" +
                "            <count>19</count>\n" +
                "            <id>13</id>\n" +
                "            <mark>-0.7554306442250373</mark>\n" +
                "            <size>14.1</size>\n" +
                "        </monitor>\n" +
                "    </laptop>\n" +
                "    <laptop>\n" +
                "        <id>1435</id>\n" +
                "        <name>HP ProBook 440 G7 9GQ22PA : I5-10210U | 4GB RAM | 256GB SSD | UHD Graphics 630 | 14.0 FHD | Finger | FreeDos </name>\n" +
                "        <model>9GQ22PA</model>\n" +
                "        <price>16590000</price>\n" +
                "        <weight>1.65</weight>\n" +
                "        <weightMark>0.24019762410105722</weightMark>\n" +
                "        <image>https://kimlongcenter.com/upload/product/83457923754_2.jpg</image>\n" +
                "        <brand>\n" +
                "            <id>34</id>\n" +
                "            <name>Laptop HP</name>\n" +
                "        </brand>\n" +
                "        <processor>\n" +
                "            <id>233</id>\n" +
                "            <brand>Intel</brand>\n" +
                "            <name>Intel® Core™ i5-10210U </name>\n" +
                "            <model>10210U </model>\n" +
                "            <core>4</core>\n" +
                "            <thread>8</thread>\n" +
                "            <baseClock>1.6</baseClock>\n" +
                "            <boostClock>4.2</boostClock>\n" +
                "            <cache>6.0</cache>\n" +
                "            <count>99</count>\n" +
                "            <mark>-0.1769226153232543</mark>\n" +
                "        </processor>\n" +
                "        <ram>\n" +
                "            <count>185</count>\n" +
                "            <id>6</id>\n" +
                "            <mark>-0.7857986313497338</mark>\n" +
                "            <memory>4</memory>\n" +
                "        </ram>\n" +
                "        <monitor>\n" +
                "            <count>194</count>\n" +
                "            <id>10</id>\n" +
                "            <mark>-0.8546843055100788</mark>\n" +
                "            <size>14.0</size>\n" +
                "        </monitor>\n" +
                "    </laptop>\n" +
                "    <pageSize>0</pageSize>\n" +
                "    <pageNumber>0</pageNumber>\n" +
                "    <totalPage>0</totalPage>\n" +
                "    <haveNext>false</haveNext>\n" +
                "    <next>0</next>\n" +
                "    <previous>-1</previous>\n" +
                "</laptops>";
    }


}
