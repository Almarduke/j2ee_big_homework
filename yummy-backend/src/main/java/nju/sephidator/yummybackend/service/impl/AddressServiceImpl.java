package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.AddressDAO;
import nju.sephidator.yummybackend.repository.AddressJPA;
import nju.sephidator.yummybackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressJPA addressJPA;

    @Override
    public List<String> getAll() {
        return addressJPA.findAll().stream().map(AddressDAO::getName).collect(Collectors.toList());
    }
}
