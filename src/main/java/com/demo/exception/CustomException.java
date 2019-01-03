package com.demo.exception;

import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 6589601144329594782L;

    private int code;

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }
}
