package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class YummyFinanceDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private String orderId;

    private Double income;

    private Date time;
}
