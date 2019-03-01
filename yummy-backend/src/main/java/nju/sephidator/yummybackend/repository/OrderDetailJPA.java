package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.OrderDetailDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailJPA extends JpaRepository<OrderDetailDAO, String> {
    List<OrderDetailDAO> findByOrderId(String orderId);
}
