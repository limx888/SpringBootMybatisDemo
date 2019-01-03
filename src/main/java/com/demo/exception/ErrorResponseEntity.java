package com.demo.exception;

import lombok.Data;

/**
 * 异常信息模板
 */
@Data
public class ErrorResponseEntity {
    private int code;
    private String message;

    public ErrorResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
