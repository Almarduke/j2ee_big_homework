package nju.sephidator.yummybackend.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class FoodDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private String password;

    private String name;

    private String type;

    private BigDecimal price;
}
