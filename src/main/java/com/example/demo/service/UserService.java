package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component("userService")
public interface UserService {
    public abstract void addUser();
}
