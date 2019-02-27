package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.dataobject.CheckCodeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckCodeJPA extends JpaRepository<CheckCodeDAO, String> {
    boolean existsByEmailAndCode(String email, String code);
}
