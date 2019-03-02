package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.AddressDAO;
import nju.sephidator.yummybackend.model.RestaurantInfoCheckDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantInfoCheckJPA extends JpaRepository<RestaurantInfoCheckDAO, String> {
}
