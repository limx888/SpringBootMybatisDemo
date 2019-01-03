package com.demo.controller;

import com.demo.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常捕获测试
 */
@RestController
public class ExceptionController {
    @GetMapping("/test1")
    public String test1() {
        int i = 10 / 0;
        return "test1";
    }

    @GetMapping("/test2")
    public Map<String, String> test2() {
        Map<String, String> result = new HashMap<>(16);
        try {
            int i = 10 / 0;
            result.put("code", "200");
            result.put("data", "具体返回的结果集");
        } catch (Exception e) {
            result.put("code", "500");
            result.put("data", "请求错误");
        }
        return result;
    }

    @GetMapping("/test3")
    public String test3(Integer num) {
        if (num == null) {
            throw new CustomException(400, "num不能为空");
        }

        int i = 10 / num;
        return "result:" + i;
    }
}
