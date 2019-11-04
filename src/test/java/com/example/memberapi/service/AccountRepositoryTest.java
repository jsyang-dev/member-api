package com.example.memberapi.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.memberapi.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    DynamoDBMapper dynamoDBMapper;
    @Autowired
    AmazonDynamoDB amazonDynamoDB;
    @Autowired
    AccountRepository accountRepository;

    //TODO: BeforeEach, AfterEach 해결하자!
    @BeforeEach
    void createTable() {

        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Account.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);

        Account account = Account.createBuilder()
                .username("admin")
                .name("정찬희")
                .password("1234")
                .email("admin@lotte.net")
                .phone("010-2345-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

    }


    @AfterEach
    void cleanUp() {
        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(Account.class);
        TableUtils.deleteTableIfExists(amazonDynamoDB, deleteTableRequest);
    }


    @Test
    void userName으로_Account_찾기_테스트(){
        Account account = accountRepository.findAccountByUserName("admin");

        assertThat(account.getName()).isEqualTo("정찬희");
        assertThat(account.getEmail()).isEqualTo("admin@lotte.net");
    }

    @Test
    void Account_추가_테스트() {
        //before
        Account account = Account.createBuilder()
                .username("test")
                .name("서재연")
                .password("1234")
                .email("seojaeyeon@lotte.net")
                .phone("010-2345-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

        //then
        Account findAccount = accountRepository.findAccountByUserName(account.getUsername());
        assertThat(findAccount).isEqualToComparingFieldByField(account);

    }

    @Test
    void Account_패스워드_수정_테스트() {
        //before
        Account account = Account.createBuilder()
                .username("admin")
                .name("정찬희")
                .password("12345")
                .email("admin@lotte.net")
                .phone("010-2345-1234")
                .build();

        //when
        accountRepository.updateAccount(account);

        //then
        Account findAccount = accountRepository.findAccountByUserName(account.getUsername());
        assertThat(findAccount).isEqualToComparingFieldByField(account);
    }


}
