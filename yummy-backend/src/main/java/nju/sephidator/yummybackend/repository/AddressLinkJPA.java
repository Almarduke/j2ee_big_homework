package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.AddressLinkDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressLinkJPA extends JpaRepository<AddressLinkDAO, String> {
}
