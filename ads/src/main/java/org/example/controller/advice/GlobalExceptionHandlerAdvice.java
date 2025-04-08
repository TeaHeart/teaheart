package org.example.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException e) {
        log.warn(e.getMessage());
        Result<?> result = Result.builder()
                .code(Optional.ofNullable(e.getCode()).orElse(HttpStatus.BAD_REQUEST.value()))
                .message(Optional.ofNullable(e.getCause()).orElse(e).getMessage())
                .build();
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<?>> handleRuntimeException(RuntimeException e) {
        log.warn(e.getMessage());
        Result<?> result = Result.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Optional.ofNullable(e.getCause()).orElse(e).getMessage())
                .build();
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception e) {
        log.error(e.getMessage());
        Result<?> result = Result.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Optional.ofNullable(e.getCause()).orElse(e).getMessage())
                .build();
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
