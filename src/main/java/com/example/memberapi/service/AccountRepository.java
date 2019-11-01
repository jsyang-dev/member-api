package com.example.memberapi.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.memberapi.domain.Account;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Account findAccountByUserName(final String userName) {
        return dynamoDBMapper.load(Account.class, userName);
    }

    public Account updateAccount(Account account) {
        dynamoDBMapper.save(account);
        return account;
    }
}
