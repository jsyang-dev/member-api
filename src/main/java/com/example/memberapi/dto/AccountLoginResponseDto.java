package com.example.memberapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountLoginResponseDto {
    private String username;
    private String name;
    private String email;
    private String phone;

    @Builder(builderMethodName = "createBuilder")
    public AccountLoginResponseDto(String username, String name, String email, String phone) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
