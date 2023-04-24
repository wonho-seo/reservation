package com.example.reservation.exception;

import javax.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customExceptionHandler(final CustomException e){
        return ResponseEntity.status(e.getErrorCode().getCode()).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> MethodArgumentNotValidExceptionHandler(final Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 올바르지 않습니다.");
    }
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> UnexpectedTypeExceptionHandler(final Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력이 올바르지 않습니다.");
    }
}
