package restApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;


    public Category(String name) {
        this.setName(name);
    }
}
