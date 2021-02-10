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
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private int customerId;

    private String name;

    @OneToMany
    private List<ProductOrder> productOrderList;
}
