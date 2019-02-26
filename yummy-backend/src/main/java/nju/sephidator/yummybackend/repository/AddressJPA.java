package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.dataobject.AddressDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPA extends JpaRepository<AddressDAO, String> {
}
