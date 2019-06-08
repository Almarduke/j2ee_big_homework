package nju.sephidator.yummybackend.model;

import lombok.Data;
import nju.sephidator.yummybackend.utils.MathUtil;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class YummyOrder {

    @Id
    private String id;

    private String memberEmail;

    private String memberAddress;

    private String restaurantId;

    private Double amount;

    private Double discount;

    private Integer orderStatus;

    private Date createTime;

    public void setAmount(Double amount) {
        this.amount = MathUtil.scaledDouble(amount, 2);
    }

    public void setDiscount(Double discount) {
        this.discount = MathUtil.scaledDouble(discount, 2);
    }
}
