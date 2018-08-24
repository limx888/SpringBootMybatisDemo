package com.demo.service;


import com.demo.model.Addresslist;

import java.util.List;

public interface UserService {
    public Addresslist getAddressById(int userId);

    public int addAddress(Addresslist record);

    public int updateAddress(Addresslist record);

    public int deleteById(int userId);

    public List<Addresslist> selectAddress();
}
