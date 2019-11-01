package com.example.memberapi.service;

import com.example.memberapi.domain.Account;
import com.example.memberapi.dto.AccountResponseDto;
import com.example.memberapi.dto.AccountSaveRequestDto;
import com.example.memberapi.dto.AccountUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponseDto loginByUserName(final String userName, final String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("잘못된 값이 들어왔습니다!");
        }
        Account account = accountRepository.findAccountByUserName(userName);
        if (account == null) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다!");
        }
        if (account.isNotEqualPassword(password)) {
            throw new IllegalArgumentException("패스워드가 잘못되었습니다!");
        }

        AccountResponseDto accountResponseDto = AccountResponseDto.createBuilder()
                .userName(account.getUserName())
                .email(account.getEmail())
                .name(account.getName())
                .phone(account.getPhone())
                .build();

        return accountResponseDto;
    }

    public String saveAccount(@NotNull AccountSaveRequestDto accountSaveRequestDto){
        if(accountRepository.findAccountByUserName(accountSaveRequestDto.getUserName()) != null){
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다!");
        }
        Account account = Account.createBuilder()
                .userName(accountSaveRequestDto.getUserName())
                .password(accountSaveRequestDto.getPassword())
                .name(accountSaveRequestDto.getName())
                .email(accountSaveRequestDto.getEmail())
                .phone(accountSaveRequestDto.getPhone())
                .build();

        accountRepository.updateAccount(account);
        return account.getUserName();
    }

    public String updateAccount(@NotNull AccountUpdateRequestDto accountUpdateRequestDto){
        Account account = accountRepository.findAccountByUserName(accountUpdateRequestDto.getUserName());
        if(account == null){
            throw new IllegalArgumentException("존재하지 않는 사용자입니다!");
        }
        account.setPhone(accountUpdateRequestDto.getPhone());
        account.setEmail(accountUpdateRequestDto.getEmail());
        account.setPassword(accountUpdateRequestDto.getPassword());

        accountRepository.updateAccount(account);

        return account.getUserName();
    }

}

