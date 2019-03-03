package nju.sephidator.yummybackend.vo.food;

import lombok.Data;
import nju.sephidator.yummybackend.model.AddressLinkDAO;
import nju.sephidator.yummybackend.model.FoodDAO;
import nju.sephidator.yummybackend.model.MemberDAO;

import java.io.Serializable;

@Data
public class FoodVO {

    private Integer id;

    private String restaurantId;

    private String name;

    private String type;

    private Double price;

    private Integer number;

    public FoodVO() {}

    public FoodVO(FoodDAO foodDAO) {
        this.id = foodDAO.getId();
        this.restaurantId = foodDAO.getRestaurantId();
        this.name = foodDAO.getName();
        this.type = foodDAO.getType();
        this.price = foodDAO.getPrice();
        this.number = foodDAO.getNumber();
    }

    public FoodDAO generateFoodDAO(boolean available) {
        FoodDAO foodDAO = new FoodDAO();
        foodDAO.setId(id);
        foodDAO.setRestaurantId(restaurantId);
        foodDAO.setName(name);
        foodDAO.setType(type);
        foodDAO.setPrice(price);
        foodDAO.setNumber(number);
        foodDAO.setAvailable(available);
        return foodDAO;
    }
}