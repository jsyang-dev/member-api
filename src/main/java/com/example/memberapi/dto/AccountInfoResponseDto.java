package com.example.memberapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResponseDto {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public AccountInfoResponseDto(String username, String password, String name, String phone, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
