package com.demo.controller;

import com.demo.model.Addresslist;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/testboot")
public class TestBootController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getAddress")
    public Addresslist getUser(@RequestParam int id) {
        return userService.getAddressById(id);
    }

    @PutMapping(value = "/updateAddress")
    public ResponseEntity updateAddress(@RequestBody Addresslist address) {
        return userService.updateAddress(address) > 0 ? ResponseEntity.ok("更新成功") : ResponseEntity.ok("更新失败");
    }

    @PostMapping(value = "/addAddress")
    public ResponseEntity addAddress(@RequestBody Addresslist address) {
        return userService.addAddress(address) > 0 ? ResponseEntity.ok("添加成功") :ResponseEntity.ok("添加失败");
    }

    @DeleteMapping(value = "/deleteAddress")
    public ResponseEntity deleteById(@RequestParam int id) {
        return userService.deleteById(id) > 0 ? ResponseEntity.ok("删除成功") :ResponseEntity.ok("删除失败");
    }

    @GetMapping(value = "/getAllAddress")
    public List<Addresslist> getAllAddress() {
        return userService.selectAddress();
    }
}
