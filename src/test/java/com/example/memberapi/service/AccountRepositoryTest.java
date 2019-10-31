package com.example.memberapi.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.example.memberapi.model.Account;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    DynamoDBMapper dynamoDBMapper;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account = Account.createBuilder()
                .userName("admin")
                .name("정찬희")
                .password("1234")
                .email("admin@lotte.net")
                .phone("010-1234-1234")
                .build();

        dynamoDBMapper.save(account);
    }

    @Test
    void userName으로_Account_찾기_테스트(){
        Account account = accountRepository.findAccountByUserName("admin");

        assertThat(account.getName()).isEqualTo("정찬희");
        assertThat(account.getPassword()).isEqualTo("1234");
        assertThat(account.getPhone()).isEqualTo("010-1234-1234");
        assertThat(account.getEmail()).isEqualTo("admin@lotte.net");
    }

    @Test
    void Account_추가_테스트() {
        //before
        Account account = Account.createBuilder()
                .userName("test")
                .name("서재연")
                .password("1234")
                .email("seojaeyeon@lotte.net")
                .phone("010-2345-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

        //then
        Account findAccount = accountRepository.findAccountByUserName(account.getUserName());
        assertThat(findAccount).isEqualToComparingFieldByField(account);

        dynamoDBMapper.delete(account);
    }

    @Test
    void Account_패스워드_수정_테스트() {
        //before
        Account account = Account.createBuilder()
                .userName("admin")
                .name("정찬희")
                .password("12345")
                .email("lotte@lotte.net")
                .phone("010-2345-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

        //then
        Account findAccount = accountRepository.findAccountByUserName(account.getUserName());
        assertThat(findAccount).isEqualToComparingFieldByField(account);
    }


}
