package nju.sephidator.yummybackend.repository;


import nju.sephidator.yummybackend.dataobject.MemberDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJPA extends JpaRepository<MemberDAO, String> {
}
