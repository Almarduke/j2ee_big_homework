package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.AddressDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPA extends JpaRepository<AddressDAO, String> {
}
