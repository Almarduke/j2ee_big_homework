package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.AddressLinkDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressLinkJPA extends JpaRepository<AddressLinkDAO, String> {
    List<AddressLinkDAO> findByUserId(String userId);

    List<AddressLinkDAO> findByUserIdAndStatus(String userId, Integer status);

    List<AddressLinkDAO> findByUserIdAndAddressName(String userId, String addressName);

    boolean existsByUserIdAndAddressName(String userId, String addressName);
}
