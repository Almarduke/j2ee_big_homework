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
public class FoodDAO {

    @Id
    @GeneratedValue
    private Integer id;

    private String restaurantId;

    private String name;

    private String type;

    private Double price;

    private Integer number;

    private boolean available;

    public void setPrice(Double price) {
        this.price = MathUtil.scaledDouble(price, 2);
    }
}
