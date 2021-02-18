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
    private String email;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private String billingAddress;

    public Customer(String email, String firstName,
                    String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    public Customer(String email) {
        this.email = email;
    }

    public Customer(String email, String personalNumber) {
        this.email = email;
        this.personalNumber = personalNumber;
    }

    @OneToMany(mappedBy = "customer")
    private List<ProductOrder> productOrderList;

    public Customer(String email, List<ProductOrder> productOrderList) {
        this.email = email;
        this.productOrderList = productOrderList;
    }
}
