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

    @OneToMany
    private List<Product> productList;
}
