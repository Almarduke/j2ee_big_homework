package nju.sephidator.yummybackend.dataobject;

import lombok.Data;
import nju.sephidator.yummybackend.enums.OrderStatus;
import nju.sephidator.yummybackend.enums.PayStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private String memberEmail;

    private String restaurantId;

    private BigDecimal amount;

    private Integer orderStatus = OrderStatus.NEW.getCode();

    private Integer payStatus = PayStatus.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}
