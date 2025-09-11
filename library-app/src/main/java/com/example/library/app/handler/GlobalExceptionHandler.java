package com.example.library.app.handler;

import com.example.library.app.dto.ResponseResult;
import com.example.library.core.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseResult<Void>> handleBusiness(BusinessException ex) {
        return ResponseEntity.ok(ResponseResults.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Void>> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(ResponseResults.error("请求参数错误"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<Void>> handleOther(Exception ex) {
        return ResponseEntity.ok(ResponseResults.error("系统异常"));
    }
}
