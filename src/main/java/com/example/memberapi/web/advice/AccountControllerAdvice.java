package com.example.memberapi.web.advice;

import com.example.memberapi.domain.Account;
import com.example.memberapi.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(annotations = RestController.class)
public class AccountControllerAdvice {

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String accountException(AccountException accountException){
        return accountException.getMessage();
    }
}
