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
public class Sale implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;

    @OneToMany(mappedBy = "sale")
    private List<Product> productList;

    private double percentage;
    private boolean currentSale;

    public Sale(String name, double percentage) {
        this.setName(name);
        this.setPercentage(percentage);
    }
}
