package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.CheckCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckCodeJPA extends JpaRepository<CheckCode, String> {
    boolean existsByEmailAndCode(String email, String code);
}
