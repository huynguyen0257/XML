package huyng.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "brands")
@XmlType(name = "", propOrder = {
        "brand"
})
public class BrandEntityList {
    protected List<BrandEntity> brand;

    public List<BrandEntity> getBrand() {
        if (brand == null){
            brand = new ArrayList<BrandEntity>();
        }
        return brand;
    }

    public void setBrand(List<BrandEntity> brands) {
        this.brand = brands;
    }
}
