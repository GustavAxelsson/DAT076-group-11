package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
    private String category;
    @ManyToOne
    private ProductOrder productOrder;
}
