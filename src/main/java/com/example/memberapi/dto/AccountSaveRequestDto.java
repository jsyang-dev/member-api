package com.example.memberapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSaveRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public AccountSaveRequestDto(@NotBlank String userName, @NotBlank String password, @NotBlank String name, @NotBlank String phone, @NotBlank String email) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
