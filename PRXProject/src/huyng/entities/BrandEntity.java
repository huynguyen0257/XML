package huyng.entities;

import javax.persistence.*;
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


    public BrandEntity(String name) {
        this.name = name;
    }

    public BrandEntity() {
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
        BrandEntity entity = (BrandEntity) o;
        return id == entity.id &&
                Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
