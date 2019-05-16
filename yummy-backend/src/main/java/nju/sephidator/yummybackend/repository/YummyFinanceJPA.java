package nju.sephidator.yummybackend.repository;

import nju.sephidator.yummybackend.model.YummyFinance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YummyFinanceJPA extends JpaRepository<YummyFinance, String> {
}
