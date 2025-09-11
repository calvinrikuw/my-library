package com.example.library.app.dto;

import java.time.LocalDateTime;

public class ResponseResult<T> {
    private final String code;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ResponseResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }
}
