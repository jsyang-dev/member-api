package com.example.memberapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamoDBTable(tableName = "ACCOUNT")
public class Account {
    @DynamoDBHashKey(attributeName = "USERNAME")
    private String userName;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBAttribute
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public Account(String userName, String password, String name, String phone, String email) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
