package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
public class RestaurantDAO {

    @Id
    private String id;

    private String password;

    private String phone;

    private String name;

    private Double amount;
}
