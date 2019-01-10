package com.demo.controller;

import com.demo.validator.Book;
import com.demo.validator.DateTime;
import com.demo.validator.Groups;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 参数校验
 */
@Validated
@RestController
@RequestMapping("/validator")
public class ValidatorController {
    @GetMapping("/test1")
    public String test1(String name) {
        if (null == name) {
            throw new NullPointerException("name不能为空");
        }

        if (name.length() < 2 || name.length() > 10) {
            throw new RuntimeException("name长度必须在2 - 10之间");
        }
        return "success";
    }

    /**
     * 参数验证
     * @param name
     * @return
     */
    @GetMapping("/test2")
    public String test2(@NotBlank(message = "name不能为空") @Length(min = 2, max = 10, message = "name长度必须在{} - {}之间") String name) {
        return "success";
    }

    /**
     * 参数验证
     * @param book
     * @return
     */
    @GetMapping("/test3")
    public String test3(@Validated Book book) {
        return "success";
    }

    /**
     * 日期格式验证
     * @param date
     * @return
     */
    @GetMapping("/test")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") String date) {
        return "success";
    }

    /**
     * 分组验证
     * @param book
     * @return
     */
    @GetMapping("/insert")
    public String insert(@Validated(value = Groups.Default.class) Book book) {
        return "insert";
    }

    /**
     * 分组验证
     * @param book
     * @return
     */
    @GetMapping("/update")
    public String update(@Validated(value = {Groups.Default.class, Groups.Update.class}) Book book) {
        return "update";
    }
}
