package com.demo.service;


import com.demo.model.Addresslist;

public interface UserService {
    public Addresslist getAddressById(int userId);

    public int addAddress(Addresslist record);

    public int updateAddress(Addresslist record);
}
