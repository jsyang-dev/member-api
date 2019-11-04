package com.example.memberapi.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "ACCOUNT")
public class Account {
    @DynamoDBHashKey(attributeName = "USERNAME")
    private String username;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBAttribute
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public Account(String username, String password, String name, String phone, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public boolean isNotEqualPassword(String password) {
        return !this.password.equals(password);
    }
}
