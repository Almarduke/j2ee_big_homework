package nju.sephidator.yummybackend.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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
