package huyng.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Monitor", schema = "dbo", catalog = "PRXProject")
@NamedQueries({
        @NamedQuery(name = "Monitor.findBySize", query = "select m from MonitorEntity m where m.size = :size"),
        @NamedQuery(name = "Monitor.findAll", query = "select m from MonitorEntity m"),
})
public class MonitorEntity {
    private int id;
    private double size;
    private int count;
    private double mark;
    @XmlTransient
    private Collection<LaptopEntity> laptops;

    public MonitorEntity() {
    }

    public MonitorEntity(double size, int count) {
        this.size = size;
        this.count = count;
    }

    @Id
    @Column(name = "Id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Size")
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Basic
    @Column(name = "Mark")
    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonitorEntity that = (MonitorEntity) o;
        return id == that.id &&
                Double.compare(that.size, size) == 0 &&
                Double.compare(that.mark, mark) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, mark);
    }

    @OneToMany(mappedBy = "monitor")
    @XmlTransient
    public Collection<LaptopEntity> getLaptops() {
        if (laptops == null) laptops = new ArrayList<>();
        return laptops;
    }

    public void setLaptops(Collection<LaptopEntity> laptops) {
        this.laptops = laptops;
    }

    @Basic
    @Column(name = "Count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
