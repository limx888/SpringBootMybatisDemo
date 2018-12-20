package com.demo.controller;

import com.demo.model.Addresslist;
import com.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/testboot")
@Api(tags = "1.1", description = "用户地址管理", value = "用户地址管理")
public class TestBootController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据用户id获取地址信息")
    @ApiImplicitParam(name = "id", value = "用户ID",dataType = "int", paramType = "query", required = true)
    @GetMapping(value = "/getAddress")
    public Addresslist getAddress(@RequestParam int id) {
        return userService.getAddressById(id);
    }

    @ApiOperation(value = "修改用户地址信息")
    @PutMapping(value = "/updateAddress")
    public ResponseEntity updateAddress(@RequestBody Addresslist address) {
        return userService.updateAddress(address) > 0 ? ResponseEntity.ok("更新成功") : ResponseEntity.ok("更新失败");
    }

    @ApiOperation(value = "添加地址信息")
    @PostMapping(value = "/addAddress")
    public ResponseEntity addAddress(@RequestBody Addresslist address) {
        return userService.addAddress(address) > 0 ? ResponseEntity.ok("添加成功") :ResponseEntity.ok("添加失败");
    }

    @ApiOperation(value = "根据用户id删除地址信息")
    @ApiImplicitParam(name = "id", value = "用户ID",dataType = "int", paramType = "query", required = true)
    @DeleteMapping(value = "/deleteAddress")
    public ResponseEntity deleteById(@RequestParam int id) {
        return userService.deleteById(id) > 0 ? ResponseEntity.ok("删除成功") :ResponseEntity.ok("删除失败");
    }

    @ApiOperation(value = "获取所有地址信息")
    @GetMapping(value = "/getAllAddress")
    public List<Addresslist> getAllAddress() {
        return userService.selectAddress();
    }
}
