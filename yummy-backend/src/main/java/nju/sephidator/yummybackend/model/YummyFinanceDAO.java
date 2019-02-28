package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
public class YummyFinanceDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer orderId;

    private Double income;
}
