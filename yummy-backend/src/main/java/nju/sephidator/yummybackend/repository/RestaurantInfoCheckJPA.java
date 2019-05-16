package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.RestaurantInfoCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantInfoCheckJPA extends JpaRepository<RestaurantInfoCheck, String> {
}
