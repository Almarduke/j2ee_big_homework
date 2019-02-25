package nju.sephidator.yummybackend.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class MemberDAO {

    @Id
    private String email;

    private String password;

    private String phone;

    private String name;

    private Integer level;

    private BigDecimal amount;
}
