package huyng.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "rams")
@XmlType(name = "", propOrder = {
        "ram"
})
public class RamEntityList {
    protected List<RamEntity> ram;

    public List<RamEntity> getRam() {
        if (ram == null){
            ram = new ArrayList<RamEntity>();
        }
        return ram;
    }

    public void setRam(List<RamEntity> rams) {
        this.ram = rams;
    }
}
