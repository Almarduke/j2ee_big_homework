package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.enums.AddressStatus;
import nju.sephidator.yummybackend.model.Address;
import nju.sephidator.yummybackend.model.AddressLink;
import nju.sephidator.yummybackend.repository.AddressJPA;
import nju.sephidator.yummybackend.repository.AddressLinkJPA;
import nju.sephidator.yummybackend.service.AddressService;
import nju.sephidator.yummybackend.vo.address.AddressVO;
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
        return addressJPA.findAll().stream().map(Address::getName).collect(Collectors.toList());
    }

    @Override
    public List<AddressVO> addUserAddressVO(String userId, String addressName) {
        AddressLink addressLink = new AddressLink
                (userId, addressName, AddressStatus.COMMON.getCode());
        addressLinkJPA.save(addressLink);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> setAsDefault(String userId, String addressName) {
        for (AddressLink addressLink :
                addressLinkJPA.findByUserId(userId)) {
            addressLink.setStatus(AddressStatus.COMMON.getCode());
            addressLinkJPA.save(addressLink);
        }
        AddressLink defaultAddress = addressLinkJPA.findByUserIdAndAddressName(userId, addressName).get(0);
        defaultAddress.setStatus(AddressStatus.DEFAULT.getCode());
        addressLinkJPA.save(defaultAddress);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> delete(String userId, String addressName) {
        AddressLink addressToDelete = addressLinkJPA.findByUserIdAndAddressName(userId, addressName).get(0);
        addressLinkJPA.delete(addressToDelete);
        return getUserAddressList(userId);
    }

    @Override
    public List<AddressVO> getUserAddressList (String id) {
        List<AddressVO> addressList = new ArrayList<>();
        for (AddressLink addressLink :
                addressLinkJPA.findByUserIdAndStatus(id, AddressStatus.DEFAULT.getCode())) {
            addressList.add(new AddressVO(addressLink.getAddressName(), true));
        }
        for (AddressLink addressLink :
                addressLinkJPA.findByUserIdAndStatus(id, AddressStatus.COMMON.getCode())) {
            addressList.add(new AddressVO(addressLink.getAddressName(), false));
        }
        return addressList;
    }

    @Override
    public boolean alreadyExists(String userId, String addressName) {
        return addressLinkJPA.existsByUserIdAndAddressName(userId, addressName);
    }
}
