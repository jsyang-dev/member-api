package com.example.memberapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponseDto {
    private String userName;
    private String name;
    private String email;
    private String phone;

    @Builder(builderMethodName = "createBuilder")
    public AccountResponseDto(String userName, String name, String email, String phone) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
