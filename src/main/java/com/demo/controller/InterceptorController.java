package com.demo.controller;

import com.demo.annotation.CacheLock;
import com.demo.annotation.CacheParam;
import com.demo.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class InterceptorController {

    @LocalLock(key = "book:arg[0]")
    @GetMapping("/local")
    public String query(@RequestParam String token) {
        return "success - " + token;
    }

    @CacheLock(prefix = "books")
    @GetMapping("/redis")
    public String redisQuery(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }
}
