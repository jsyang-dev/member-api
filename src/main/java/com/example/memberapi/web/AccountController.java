package com.example.memberapi.web;

import com.example.memberapi.dto.AccountResponseDto;
import com.example.memberapi.dto.AccountSaveRequestDto;
import com.example.memberapi.dto.AccountUpdateRequestDto;
import com.example.memberapi.exception.AccountException;
import com.example.memberapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/login", produces = "application/json; charset=utf8")
    public ResponseEntity<AccountResponseDto> login(final String userName, final String password) throws AccountException {
        AccountResponseDto accountResponseDto = accountService.findAccountByUserNameAndPassword(userName, password);
        return new ResponseEntity<>(accountResponseDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createAccount(@Valid AccountSaveRequestDto accountSaveRequestDto) throws AccountException {
        String userName = accountService.saveAccount(accountSaveRequestDto);
        return new ResponseEntity<>(userName, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<String> updateAccount(@Valid AccountUpdateRequestDto accountUpdateRequestDto) throws AccountException {
        String userName = accountService.updateAccount(accountUpdateRequestDto);
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }


}
