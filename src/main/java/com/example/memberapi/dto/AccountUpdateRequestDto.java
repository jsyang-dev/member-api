package com.example.memberapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public AccountUpdateRequestDto(@NotBlank String userName, @NotBlank String password, @NotBlank String phone, @NotBlank String email) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }
}
