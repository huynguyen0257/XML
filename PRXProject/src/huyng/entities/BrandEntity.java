package huyng.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Brand", schema = "dbo", catalog = "PRXProject")
@NamedQueries({
        @NamedQuery(name = "Brand.findAll", query = "select b from BrandEntity b"),
        @NamedQuery(name = "Brand.findByName", query = "select b from BrandEntity b where b.name like :name"),
})
public class BrandEntity {
    private int id;
    private String name;
    private Collection<LaptopEntity> laptops;

    public BrandEntity() {
    }

    public BrandEntity(String name) {
        this.name = name;
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

    @OneToMany(mappedBy = "brand")
    public Collection<LaptopEntity> getLaptops() {
        return laptops;
    }

    public void setLaptops(Collection<LaptopEntity> laptops) {
        this.laptops = laptops;
    }
}
