package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.AddressDAO;
import nju.sephidator.yummybackend.model.AddressLinkDAO;
import nju.sephidator.yummybackend.repository.AddressJPA;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressJPA addressJPA;

    @Autowired
    private AddressLinkJPA addressLinkJPA;

    @Override
    public List<String> getAll() {
        return addressJPA.findAll().stream().map(AddressDAO::getName).collect(Collectors.toList());
    }

    @Override
    public List<AddressVO> addUserAddressVO(String userId, String addressName) {
        AddressLinkDAO addressLinkDAO = new AddressLinkDAO
                (userId, addressName, AddressStatus.COMMON.getCode());
        addressLinkJPA.save(addressLinkDAO);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> setAsDefault(String userId, String addressName) {
        for (AddressLinkDAO addressLinkDAO:
                addressLinkJPA.findByUserId(userId)) {
            addressLinkDAO.setStatus(AddressStatus.COMMON.getCode());
            addressLinkJPA.save(addressLinkDAO);
        }
        AddressLinkDAO defaultAddress = addressLinkJPA.findByUserIdAndAddressName(userId, addressName).get(0);
        defaultAddress.setStatus(AddressStatus.DEFAULT.getCode());
        addressLinkJPA.save(defaultAddress);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> delete(String userId, String addressName) {
        AddressLinkDAO addressToDelete = addressLinkJPA.findByUserIdAndAddressName(userId, addressName).get(0);
        addressLinkJPA.delete(addressToDelete);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> getUserAddressList (String id) {
        List<AddressVO> addressList = new ArrayList<>();
        for (AddressLinkDAO addressLinkDAO:
                addressLinkJPA.findByUserIdAndStatus(id, AddressStatus.DEFAULT.getCode())) {
            addressList.add(new AddressVO(addressLinkDAO.getAddressName(), true));
        }
        for (AddressLinkDAO addressLinkDAO:
                addressLinkJPA.findByUserIdAndStatus(id, AddressStatus.COMMON.getCode())) {
            addressList.add(new AddressVO(addressLinkDAO.getAddressName(), false));
        }
        return addressList;
    }

    @Override
    public boolean alreadyExists(String userId, String addressName) {
        return addressLinkJPA.existsByUserIdAndAddressName(userId, addressName);
    }
}
