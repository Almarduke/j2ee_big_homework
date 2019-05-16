package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.AddressLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressLinkJPA extends JpaRepository<AddressLink, String> {
    List<AddressLink> findByUserId(String userId);

    List<AddressLink> findByUserIdAndStatus(String userId, Integer status);

    List<AddressLink> findByUserIdAndAddressName(String userId, String addressName);

    boolean existsByUserIdAndAddressName(String userId, String addressName);
}
