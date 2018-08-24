package com.example.demo.service;

import com.example.demo.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser() {
        userDao.save();
        System.out.println("add user");
    }
}
