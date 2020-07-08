package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Brand", schema = "dbo", catalog = "PRXProject")
public class BrandEntity {
    private int id;
    private String name;
    private Collection<LaptopEntity> laptops;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandEntity that = (BrandEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "brandByBrandId")
    public Collection<LaptopEntity> getLaptops() {
        return laptops;
    }

    public void setLaptops(Collection<LaptopEntity> laptops) {
        this.laptops = laptops;
    }
}
