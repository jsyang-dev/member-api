package com.example.memberapi.web.advice;

import com.example.memberapi.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class AccountControllerAdvice {

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String accountException(AccountException accountException) {
        return accountException.getMessage();
    }
}
