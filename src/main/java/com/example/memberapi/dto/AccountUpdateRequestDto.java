package com.example.memberapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
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
