package nju.sephidator.yummybackend.dataobject;

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
    private Integer Id;

    private String userId;

    private Integer coordinateX;

    private Integer coordinateY;

    private String description;
}
