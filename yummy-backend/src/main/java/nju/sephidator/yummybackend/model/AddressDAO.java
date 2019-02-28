package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
public class AddressDAO {

    @Id
    @GeneratedValue
    private String name;

    private Integer coordinateX;

    private Integer coordinateY;

}
