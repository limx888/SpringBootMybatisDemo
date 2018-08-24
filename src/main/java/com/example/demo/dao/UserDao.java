package com.example.demo.dao;

import org.springframework.stereotype.Component;

@Component("userDao")
public interface UserDao {
    public void save();
}
