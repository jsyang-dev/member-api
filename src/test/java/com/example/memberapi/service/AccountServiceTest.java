package com.example.memberapi.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.memberapi.domain.Account;
import com.example.memberapi.dto.AccountLoginResponseDto;
import com.example.memberapi.dto.AccountSaveRequestDto;
import com.example.memberapi.dto.AccountUpdateRequestDto;
import com.example.memberapi.exception.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
                .username("admin")
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
    void 로그인_성공_케이스() throws AccountException {
        AccountLoginResponseDto account = accountService.findAccountByUserNameAndPassword("admin", "1234");

        assertThat(account.getName()).isEqualTo("정찬희");
        assertThat(account.getEmail()).isEqualTo("admin@lotte.net");
    }

    @Test
    void 로그인_실패_케이스_비밀번호_틀림() {
        assertThatThrownBy(() -> accountService.findAccountByUserNameAndPassword("admin", "12345"))
                .isInstanceOf(AccountException.class);
    }

    @Test
    void 로그인_실패_케이스_없는_사용자() {
        assertThatThrownBy(() -> accountService.findAccountByUserNameAndPassword("admin1234", "1234"))
                .isInstanceOf(AccountException.class);
    }

    @Test
    void 유저_생성_성공_테스트() throws AccountException {
        AccountSaveRequestDto accountSaveRequestDto = AccountSaveRequestDto.createBuilder()
                .username("test")
                .password("1234")
                .name("테스트")
                .email("test@lotte.net")
                .phone("010-8888-8888")
                .build();

        accountService.saveAccount(accountSaveRequestDto);

        Account account = accountRepository.findAccountByUserName(accountSaveRequestDto.getUsername());

        assertThat(account).isEqualToComparingFieldByField(accountSaveRequestDto);
    }

    @Test
    void 유저_생성_실패_테스트_이미_존재하는_사용자(){
        AccountSaveRequestDto accountSaveRequestDto = AccountSaveRequestDto.createBuilder()
                .username("admin")
                .password("1234")
                .name("테스트")
                .email("test@lotte.net")
                .phone("010-8888-8888")
                .build();

        assertThatThrownBy(()-> accountService.saveAccount(accountSaveRequestDto))
                .isInstanceOf(AccountException.class);
    }

    @Test
    void 유저_생성_실패_테스트_이름입력안함(){
        AccountSaveRequestDto accountSaveRequestDto = AccountSaveRequestDto.createBuilder()
                .username("admin")
                .password("1234")
                .email("test@lotte.net")
                .phone("010-8888-8888")
                .build();

        assertThatThrownBy(()-> accountService.saveAccount(accountSaveRequestDto))
                .isInstanceOf(AccountException.class);
    }

    @Test
    void 유저_정보_수정() throws AccountException {
        AccountUpdateRequestDto accountUpdateRequestDto = AccountUpdateRequestDto.createBuilder()
                .username("admin")
                .email("admin@lotte.net")
                .password("12345")
                .phone("010-1234-1234")
                .build();

        accountService.updateAccount(accountUpdateRequestDto);

        Account account = accountRepository.findAccountByUserName(accountUpdateRequestDto.getUsername());

        assertThat(account.getPassword()).isEqualTo("12345");

    }

    @Test
    void 없는_유저_정보_수정(){
        AccountUpdateRequestDto accountUpdateRequestDto = AccountUpdateRequestDto.createBuilder()
                .username("admin123")
                .email("admin@lotte.net")
                .password("12345")
                .phone("010-1234-1234")
                .build();

        assertThatThrownBy(()-> accountService.updateAccount(accountUpdateRequestDto))
                .isInstanceOf(AccountException.class);
    }


}
