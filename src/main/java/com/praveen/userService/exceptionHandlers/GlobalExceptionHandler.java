package com.praveen.userService.exceptionHandlers;

import com.praveen.userService.dtos.ExceptionDto;
import com.praveen.userService.exceptions.UserAlreadyExistException;
import com.praveen.userService.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundException(UserNotFoundException exception) {
        ExceptionDto error = new ExceptionDto(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserAlreadyExistException exception) {
        ExceptionDto error = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(IllegalAccessException exception) {
        ExceptionDto error = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(OAuth2AuthenticationException exception) {
        ExceptionDto error = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
