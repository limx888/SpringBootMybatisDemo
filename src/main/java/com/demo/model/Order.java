package com.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日期格式化强烈推荐
 */
@Data
public class Order {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime payTime;
}
