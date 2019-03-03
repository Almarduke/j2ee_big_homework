package nju.sephidator.yummybackend.model;

import lombok.Data;
import nju.sephidator.yummybackend.utils.MathUtil;
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

    private String address;

    private Double amount;

    private Double revenue;

    public void setAmount(Double amount) {
        this.amount = MathUtil.scaledDouble(amount, 2);
    }

    public void addToRevenue(Double amount) {
        this.amount = MathUtil.scaledDouble(revenue + amount, 2);
    }
}
