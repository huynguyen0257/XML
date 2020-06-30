package huyng.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "Laptop", schema = "dbo", catalog = "PRXProject")
@XmlRootElement(name = "laptop")
public class LaptopEntity {
    private int id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String model;
    @XmlElement(required = true)
    private int price;
    @XmlElement(required = true)
    private String cpu;
    private String vga;
    private String ram;
    private String hardDisk;
    private String lcd;
    private String options;
    private String port;
    private boolean webcam;
    private boolean fingerprintRecognition;
    private boolean faceRecognition;
    private String os;
    private String battery;
    private String size;
    private String weight;
    private String color;

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
    @Column(name = "Webcam")
    public boolean isWebcam() {
        return webcam;
    }

    public void setWebcam(boolean webcam) {
        this.webcam = webcam;
    }

    @Basic
    @Column(name = "FingerprintRecognition")
    public boolean isFingerprintRecognition() {
        return fingerprintRecognition;
    }

    public void setFingerprintRecognition(boolean fingerprintRecognition) {
        this.fingerprintRecognition = fingerprintRecognition;
    }

    @Basic
    @Column(name = "FaceRecognition")
    public boolean isFaceRecognition() {
        return faceRecognition;
    }

    public void setFaceRecognition(boolean faceRecognition) {
        this.faceRecognition = faceRecognition;
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
    @Column(name = "Size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
                webcam == that.webcam &&
                fingerprintRecognition == that.fingerprintRecognition &&
                faceRecognition == that.faceRecognition &&
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
                Objects.equals(size, that.size) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model, price, cpu, vga, ram, hardDisk, lcd, options, port, webcam, fingerprintRecognition, faceRecognition, os, battery, size, weight, color);
    }

    @Override
    public String toString() {
        return "LaptopEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", cpu='" + cpu + '\'' +
                ", vga='" + vga + '\'' +
                ", ram='" + ram + '\'' +
                ", hardDisk='" + hardDisk + '\'' +
                ", lcd='" + lcd + '\'' +
                ", options='" + options + '\'' +
                ", port='" + port + '\'' +
                ", webcam=" + webcam +
                ", fingerprintRecognition=" + fingerprintRecognition +
                ", faceRecognition=" + faceRecognition +
                ", os='" + os + '\'' +
                ", battery='" + battery + '\'' +
                ", size='" + size + '\'' +
                ", weight='" + weight + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
