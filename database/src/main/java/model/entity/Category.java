package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;


    public Category(String name) {
        this.setName(name);
    }
}
