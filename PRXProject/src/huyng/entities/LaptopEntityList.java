package huyng.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "laptops")
@XmlType(name = "", propOrder = {
        "laptop"
})
public class LaptopEntityList {

    protected List<LaptopEntity> laptop;

    public List<LaptopEntity> getLaptop() {
        if (laptop == null){
            laptop = new ArrayList<LaptopEntity>();
        }
        return laptop;
    }

    public void setLaptop(List<LaptopEntity> laptops) {
        this.laptop = laptops;
    }
}
