package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.dataobject.AddressLinkDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressLinkJPA extends JpaRepository<AddressLinkDAO, String> {
}
