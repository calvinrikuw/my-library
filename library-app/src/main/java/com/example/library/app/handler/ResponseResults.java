package com.example.library.app.handler;

import com.example.library.app.dto.ResponseResult;

public final class ResponseResults {

    private ResponseResults() {
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<>("OK", "success", data);
    }

    public static <T> ResponseResult<T> ok(String message, T data) {
        return new ResponseResult<>("OK", message, data);
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>("ERROR", message, null);
    }
}
