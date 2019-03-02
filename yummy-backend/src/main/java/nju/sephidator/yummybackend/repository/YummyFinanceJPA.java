package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.YummyFinanceDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YummyFinanceJPA extends JpaRepository<YummyFinanceDAO, String> {
}
