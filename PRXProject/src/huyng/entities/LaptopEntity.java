package huyng.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "Laptop", schema = "dbo", catalog = "PRXProject")
@XmlRootElement(name = "laptop")
@XmlType(name = "laptop")
@NamedQueries({
        @NamedQuery(name = "Laptop.findAll", query = "select l from LaptopEntity l"),
        @NamedQuery(name = "Laptop.findByModel", query = "select l from LaptopEntity l where l.model = :model"),
        @NamedQuery(name = "Laptop.findByName", query = "select l from LaptopEntity l where l.name LIKE :name"),
        @NamedQuery(name = "Laptop.findByBrandId", query = "select l from LaptopEntity l where l.brand.id = :brandId"),
        @NamedQuery(name = "Laptop.countByBrand", query = "select count (l.name) from LaptopEntity l where l.brand.id = :brandId"),
        @NamedQuery(name = "Laptop.countAll", query = "select count (l.name) from LaptopEntity l"),
        @NamedQuery(name = "Laptop.findBySuggest", query = "select l from LaptopEntity l where l.processor.name like :processorLevel and l.ram.memory > :minMemory and l.ram.memory <= :maxMemory and l.monitor.size <= :maxSize and l.monitor.size > :minSize and l.price > :fromPrice and l.price < :toPrice"),
})
public class LaptopEntity {
    private int id;
    private String name;
    private String model; //xsl
    private int price;
    private double weight; //xsl
    private double weightMark;
    private String image;
    private BrandEntity brand;
    private ProcessorEntity processor;//xsl
    private RamEntity ram;//xsl
    private MonitorEntity monitor;//xsl

    public LaptopEntity() {
    }

    public LaptopEntity(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public LaptopEntity(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
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
    @Column(name = "Price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "Weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "Image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaptopEntity that = (LaptopEntity) o;
        return id == that.id &&
                price == that.price &&
                Objects.equals(name, that.name) &&
                Objects.equals(model, that.model) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model, price, weight, image);
    }

    @ManyToOne
    @JoinColumn(name = "BrandId", referencedColumnName = "Id", nullable = false)
    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    @ManyToOne
    @JoinColumn(name = "ProcessorId", referencedColumnName = "Id", nullable = false)
    public ProcessorEntity getProcessor() {
        return processor;
    }

    public void setProcessor(ProcessorEntity processor) {
        this.processor = processor;
    }

    @ManyToOne
    @JoinColumn(name = "RamId", referencedColumnName = "Id", nullable = false)
    public RamEntity getRam() {
        return ram;
    }

    public void setRam(RamEntity ram) {
        this.ram = ram;
    }

    @ManyToOne
    @JoinColumn(name = "MonitorId", referencedColumnName = "Id", nullable = false)
    public MonitorEntity getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorEntity monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return "LaptopEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", weightMark=" + weightMark +
                ", image='" + image + '\'' +
                ", brand=" + brand +
                ", processor=" + processor +
                ", ram=" + ram +
                ", monitor=" + monitor +
                '}';
    }

    @Column(name = "WeightMark")
    public double getWeightMark() {
        return weightMark;
    }

    public void setWeightMark(double weightMark) {
        this.weightMark = weightMark;
    }
}



