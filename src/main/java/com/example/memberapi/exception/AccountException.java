package com.example.memberapi.exception;

import lombok.RequiredArgsConstructor;

public class AccountException extends Exception{

    public AccountException(String message) {
        super(message);
    }

}
