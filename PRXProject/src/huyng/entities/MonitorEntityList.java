package huyng.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "monitors")
@XmlType(name = "", propOrder = {
        "monitor"
})
public class MonitorEntityList {
    protected List<MonitorEntity> monitor;

    public List<MonitorEntity> getMonitor() {
        if (monitor == null){
            monitor = new ArrayList<MonitorEntity>();
        }
        return monitor;
    }

    public void setMonitor(List<MonitorEntity> monitors) {
        this.monitor = monitors;
    }
}
