package com.example.memberapi.service;

import com.example.memberapi.model.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account loginByUserName(String userName, String password){
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
           // throws IllegalArgumentException("잘모 ㄴㅂ사ㄱㅗㄷㅅㄴㄴ")
        }
        Account account = accountRepository.findAccountByUserName(userName);

        return account;
    }
}
