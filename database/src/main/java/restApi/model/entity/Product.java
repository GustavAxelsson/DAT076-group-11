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
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String url;
    private int price;
    private String description;
    private int stock;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(mappedBy = "productList")
    private List<ProductOrder> productOrder;

    public Product(String name, String url, int price, String description) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.description = description;
    }

    public Product(String name, String url, int price, String description, int stock) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public Product(String name) {
        this.name = name;
    }
}
