package com.example.memberapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;

    @Builder(builderMethodName = "createBuilder")
    public AccountUpdateRequestDto(@NotBlank String username, @NotBlank String password, @NotBlank String name, @NotBlank String phone, @NotBlank String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
