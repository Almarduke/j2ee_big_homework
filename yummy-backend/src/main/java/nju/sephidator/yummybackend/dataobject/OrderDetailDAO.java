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
public class OrderDetailDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer orderId;

    private Integer foodId;

    private Integer foodNum;

    private BigDecimal price;
}
