package org.example.hd.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        System.err.println("成功拦截到异常: " + e.getMessage());
        // 向前端返回 400 状态码和具体的文字错误信息
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}