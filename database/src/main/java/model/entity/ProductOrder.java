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
public class ProductOrder implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToMany()
    @JoinTable(name="productorder_product",
            joinColumns=
            @JoinColumn(name="productorder_id"),
            inverseJoinColumns=
            @JoinColumn(name="product_id"))
    private List<Product> productList;
}
