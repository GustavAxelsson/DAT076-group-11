package model.entity;

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
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String url;
    private int price;
    private String description;

    @ManyToOne()
    private Category category;


    @ManyToMany(mappedBy = "productList")
    private List<ProductOrder> productOrder;

    public Product(String name, String url, int price, String description) {
        this.setName(name);
        this.setUrl(url);
        this.setPrice(price);
        this.setDescription(description);
    }
}
