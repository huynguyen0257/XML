package huyng.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Processor", schema = "dbo", catalog = "PRXProject")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "Processor.findByModel", query = "select p from ProcessorEntity p where p.model = :model"),
        @NamedQuery(name = "Processor.findAll", query = "select p from ProcessorEntity p"),
})
@XmlType(name = "Processor", propOrder = {
        "id",
        "brand",
        "name",
        "model",
        "core",
        "thread",
        "baseClock",
        "boostClock",
        "cache",
        "mark",
        "laptops"

})
@XmlRootElement(name = "processor")
public class ProcessorEntity {
    private int id;
    private String brand;
    private String name;
    private String model;
    private int core;
    private int thread;
    private double baseClock;
    private double boostClock;
    private double cache;
    private int mark;
    private Collection<LaptopEntity> laptops;

    public ProcessorEntity() {
    }

    public ProcessorEntity(String name, String model) {
        this.name = name;
        this.model = model;
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
    @Column(name = "Brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "Core")
    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    @Basic
    @Column(name = "Thread")
    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    @Basic
    @Column(name = "BaseClock")
    public double getBaseClock() {
        return baseClock;
    }

    public void setBaseClock(double baseClock) {
        this.baseClock = baseClock;
    }

    @Basic
    @Column(name = "BoostClock")
    public double getBoostClock() {
        return boostClock;
    }

    public void setBoostClock(double boostClock) {
        this.boostClock = boostClock;
    }

    @Basic
    @Column(name = "Cache")
    public double getCache() {
        return cache;
    }

    public void setCache(double cache) {
        this.cache = cache;
    }

    @Basic
    @Column(name = "Mark")
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessorEntity that = (ProcessorEntity) o;
        return id == that.id &&
                core == that.core &&
                thread == that.thread &&
                baseClock == that.baseClock &&
                boostClock == that.boostClock &&
                cache == that.cache &&
                mark == that.mark &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(name, that.name) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, name, model, core, thread, baseClock, boostClock, cache, mark);
    }

    @OneToMany(mappedBy = "processor")
    public Collection<LaptopEntity> getLaptops() {
        return laptops;
    }

    public void setLaptops(Collection<LaptopEntity> laptops) {
        this.laptops = laptops;
    }

    @Override
    public String toString() {
        return "ProcessorEntity{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", core=" + core +
                ", thread=" + thread +
                ", baseClock=" + baseClock +
                ", boostClock=" + boostClock +
                ", cache=" + cache +
                ", mark=" + mark +
                '}';
    }
}
