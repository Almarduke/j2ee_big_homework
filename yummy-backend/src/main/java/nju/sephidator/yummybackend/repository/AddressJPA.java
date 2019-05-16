package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPA extends JpaRepository<Address, String> {
}
