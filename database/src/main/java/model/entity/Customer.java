package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private int customerId;

    private String name;
}
