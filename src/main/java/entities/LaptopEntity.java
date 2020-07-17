package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Laptop", schema = "dbo", catalog = "PRXProject")
public class LaptopEntity {
    private int id;
    private String name;
    private String model;
    private int price;
    private String cpu;
    private String vga;
    private String ram;
    private String hardDisk;
    private String lcd;
    private String options;
    private String port;
    private String os;
    private String battery;
    private String weight;
    private String color;
    private BrandEntity brandByBrandId;

    @Id
    @Column(name = "Id")
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
    @Column(name = "CPU")
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Basic
    @Column(name = "VGA")
    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    @Basic
    @Column(name = "RAM")
    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    @Basic
    @Column(name = "HardDisk")
    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    @Basic
    @Column(name = "LCD")
    public String getLcd() {
        return lcd;
    }

    public void setLcd(String lcd) {
        this.lcd = lcd;
    }

    @Basic
    @Column(name = "Options")
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Basic
    @Column(name = "Port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "OS")
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Basic
    @Column(name = "Battery")
    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    @Basic
    @Column(name = "Weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "Color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
                Objects.equals(cpu, that.cpu) &&
                Objects.equals(vga, that.vga) &&
                Objects.equals(ram, that.ram) &&
                Objects.equals(hardDisk, that.hardDisk) &&
                Objects.equals(lcd, that.lcd) &&
                Objects.equals(options, that.options) &&
                Objects.equals(port, that.port) &&
                Objects.equals(os, that.os) &&
                Objects.equals(battery, that.battery) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model, price, cpu, vga, ram, hardDisk, lcd, options, port, os, battery, weight, color);
    }

    @ManyToOne
    @JoinColumn(name = "BrandId", referencedColumnName = "Id", nullable = false)
    public BrandEntity getBrandByBrandId() {
        return brandByBrandId;
    }

    public void setBrandByBrandId(BrandEntity brandByBrandId) {
        this.brandByBrandId = brandByBrandId;
    }
}
