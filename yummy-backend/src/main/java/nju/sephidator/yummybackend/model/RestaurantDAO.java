package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class RestaurantDAO {

    @Id
    @GeneratedValue
    private Integer id = 1000000;

    private String password;

    private String phone;

    private String name;

    private BigDecimal amount;
}
