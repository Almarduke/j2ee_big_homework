package nju.sephidator.yummybackend.repository;


import nju.sephidator.yummybackend.model.MemberDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPA extends JpaRepository<MemberDAO, String> {
    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmailAndAvailable(String email, boolean available);
}
