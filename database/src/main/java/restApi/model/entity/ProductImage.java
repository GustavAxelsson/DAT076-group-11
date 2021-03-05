package restApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage implements Serializable {
    @Id
    @GeneratedValue()
    private long id;

    private String fileName;

    @Lob
    private byte[] data;

    @OneToOne
    Product product;

    public ProductImage(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }



}
