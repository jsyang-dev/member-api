package com.example.memberapi.web;

import com.example.memberapi.dto.AccountInfoResponseDto;
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

    /**
     * 조회 API
     * @param username
     * @return AccountInfoResponseDto(username, password, name, phone, email)
     * @throws AccountException
     */
    @GetMapping("/{username}")
    public ResponseEntity<AccountInfoResponseDto> findAccount(@PathVariable String username) throws AccountException {
        AccountInfoResponseDto accountResponseDto = accountService.findAccountByUsername(username);
        return new ResponseEntity<>(accountResponseDto, HttpStatus.OK);
    }

    /**
     * 저장 API
     * @param accountSaveRequestDto(username, password, name, phone, email)
     * @return usesrname
     * @throws AccountException
     */
    @PostMapping()
    public ResponseEntity<String> createAccount(@RequestBody @Valid AccountSaveRequestDto accountSaveRequestDto) throws AccountException {
        String userName = accountService.saveAccount(accountSaveRequestDto);
        return new ResponseEntity<>(userName, HttpStatus.CREATED);
    }

    /**
     * 수정 API
     * @param accountUpdateRequestDto(username, password, name, phone, email)
     * @return username
     * @throws AccountException
     */
    @PutMapping()
    public ResponseEntity<String> updateAccount(@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto) throws AccountException {
        String userName = accountService.updateAccount(accountUpdateRequestDto);
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }


}
