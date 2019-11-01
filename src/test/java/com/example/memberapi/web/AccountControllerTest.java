package com.example.memberapi.web;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.memberapi.domain.Account;
import com.example.memberapi.exception.AccountException;
import com.example.memberapi.service.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    private static final String API_URL = "/api/v1/account";

    @Autowired
    AmazonDynamoDB amazonDynamoDB;
    @Autowired
    DynamoDBMapper dynamoDBMapper;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MockMvc mockMvc;

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
    void 로그인_테스트() throws Exception {
        mockMvc.perform(post(API_URL+"/login")
        .param("userName", "admin")
        .param("password", "1234")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void 유저_생성_성공() throws Exception {
        mockMvc.perform(post(API_URL)
                .param("userName", "seojaeyeon")
                .param("email", "seojaeyeon@lotte.net")
                .param("name", "서재연")
                .param("password", "1234")
                .param("phone", "010-2854-2518")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void 유저_생성_실패_이미_존재하는_아이디() throws Exception {
        mockMvc.perform(post(API_URL)
                .param("userName", "admin")
                .param("email", "seojaeyeon@lotte.net")
                .param("name", "서재연")
                .param("password", "1234")
                .param("phone", "010-2854-2518")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect((rslt) -> assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(AccountException.class)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("이미 존재하는 사용자 이름입니다!"))
                .andReturn();
    }

    @Test
    void 유저_정상_수정() throws Exception {
        mockMvc.perform(put(API_URL)
                .param("userName", "admin")
                .param("email", "seojaeyeon@lotte.net")
                .param("name", "서재연")
                .param("password", "12345")
                .param("phone", "010-2854-2518")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Account account = accountRepository.findAccountByUserName("admin");
        assertThat(account.getPassword()).isEqualTo("12345");
    }
}
