package huyng.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Ram", schema = "dbo", catalog = "PRXProject")
@NamedQueries({
        @NamedQuery(name = "Ram.findByMemory", query = "select r from RamEntity r where r.memory = :memory"),
        @NamedQuery(name = "Ram.getAll", query = "select r from RamEntity r"),
})
@XmlRootElement(name = "ram")
public class RamEntity {
    private int id;
    private int memory;
    private int count;
    private double mark;

    private Collection<LaptopEntity> laptops;

    public RamEntity() {
    }

    public RamEntity(int memory, int count) {
        this.memory = memory;
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
    @Column(name = "Memory")
    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
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
        RamEntity ramEntity = (RamEntity) o;
        return id == ramEntity.id &&
                memory == ramEntity.memory &&
                Double.compare(ramEntity.mark, mark) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memory, mark);
    }

    @OneToMany(mappedBy = "ram", fetch = FetchType.LAZY)
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
