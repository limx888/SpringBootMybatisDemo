package com.demo.service;

import com.demo.mapper.AddresslistMapper;
import com.demo.model.Addresslist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private AddresslistMapper addresslistMapper;

    @Override
    public Addresslist getAddressById(int userId) {
        return addresslistMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int addAddress(Addresslist record) {
        return addresslistMapper.insert(record);
    }

    @Override
    public int updateAddress(Addresslist record) {
        return addresslistMapper.updateByPrimaryKey(record);
    }
}
