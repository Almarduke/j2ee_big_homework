package nju.sephidator.yummybackend.repository;


import nju.sephidator.yummybackend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPA extends JpaRepository<Member, String> {
    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmailAndAvailable(String email, boolean available);
}
