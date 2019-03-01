package nju.sephidator.yummybackend.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@DynamicUpdate
public class AddressLinkDAO implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String addressName;

    private Integer status;
}