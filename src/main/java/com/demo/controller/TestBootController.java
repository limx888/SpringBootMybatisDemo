package com.demo.controller;

import com.demo.model.Addresslist;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/testboot")
public class TestBootController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAddress")
    public Addresslist getUser(@RequestParam int id) {
        return userService.getAddressById(id);
    }

    @RequestMapping(value = "/updateAddress")
    public int updateAddress(@RequestBody Addresslist address) {
        return userService.updateAddress(address);
    }

    @RequestMapping(value = "/addAddress")
    public int addAddress(@RequestBody Addresslist address) {
        return userService.addAddress(address);
    }
}
