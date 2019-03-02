package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
public class RestaurantInfoCheckDAO {

    @Id
    private String id;

    private String phone;

    private String name;

    private String address;

}
