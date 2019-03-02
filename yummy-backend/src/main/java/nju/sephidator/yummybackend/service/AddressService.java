package nju.sephidator.yummybackend.service;

import nju.sephidator.yummybackend.vo.AddressVO;

import java.util.List;

public interface AddressService {
    List<String> getAll();

    List<AddressVO> addUserAddressVO(String userId, String addressName);

    List<AddressVO> setAsDefault(String userId, String addressName);

    List<AddressVO> delete(String userId, String addressName);

    List<AddressVO> getUserAddressList (String id);

    boolean alreadyExists(String userId, String addressName);
}
