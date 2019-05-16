package nju.sephidator.yummybackend.model;

import lombok.Data;
import nju.sephidator.yummybackend.utils.MathUtil;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
public class Member {

    @Id
    private String email;

    private String password;

    private String phone;

    private String name;

    private Integer level;

    private Double amount;

    private boolean available;

    public void setAmount(Double amount) {
        this.amount = MathUtil.scaledDouble(amount, 2);
    }
}
