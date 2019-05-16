package nju.sephidator.yummybackend.vo.food;

import lombok.Data;
import nju.sephidator.yummybackend.model.Food;

@Data
public class FoodVO {

    private Integer id;

    private String restaurantId;

    private String name;

    private String type;

    private Double price;

    private Integer number;

    public FoodVO() {}

    public FoodVO(Food food) {
        this.id = food.getId();
        this.restaurantId = food.getRestaurantId();
        this.name = food.getName();
        this.type = food.getType();
        this.price = food.getPrice();
        this.number = food.getNumber();
    }

    public Food generateFoodDAO(boolean available) {
        Food food = new Food();
        food.setId(id);
        food.setRestaurantId(restaurantId);
        food.setName(name);
        food.setType(type);
        food.setPrice(price);
        food.setNumber(number);
        food.setAvailable(available);
        return food;
    }
}
