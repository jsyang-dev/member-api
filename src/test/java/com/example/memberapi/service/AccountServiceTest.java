package com.example.memberapi.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.memberapi.domain.Account;
import com.example.memberapi.dto.AccountResponseDto;
import com.example.memberapi.dto.AccountSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {
    @Autowired
    AmazonDynamoDB amazonDynamoDB;
    @Autowired
    DynamoDBMapper dynamoDBMapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    //TODO: BeforeEach, AfterEach 해결하자!
    @BeforeEach
    void createTable() {
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Account.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);

        Account account = Account.createBuilder()
                .userName("admin")
                .name("정찬희")
                .password("1234")
                .email("admin@lotte.net")
                .phone("010-1234-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

    }


    @AfterEach
    void cleanUp(){
        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(Account.class);
        TableUtils.deleteTableIfExists(amazonDynamoDB, deleteTableRequest);
    }

    @Test
    void 로그인_성공_케이스() {
        AccountResponseDto account = accountService.loginByUserName("admin", "1234");

        assertThat(account.getName()).isEqualTo("정찬희");
        assertThat(account.getEmail()).isEqualTo("admin@lotte.net");
    }

    @Test
    void 로그인_실패_케이스_비밀번호_틀림() {
        assertThatThrownBy(() -> accountService.loginByUserName("admin", "12345"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 로그인_실패_케이스_없는_사용자() {
        assertThatThrownBy(() -> accountService.loginByUserName("admin1234", "1234"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저_생성_성공_테스트(){
        AccountSaveRequestDto accountSaveRequestDto = AccountSaveRequestDto.createBuilder()
                .userName("test")
                .password("1234")
                .name("테스트")
                .email("test@lotte.net")
                .phone("010-8888-8888")
                .build();

        accountService.saveAccount(accountSaveRequestDto);

        Account account = accountRepository.findAccountByUserName(accountSaveRequestDto.getUserName());

        assertThat(account).isEqualToComparingFieldByField(accountSaveRequestDto);
    }

}
