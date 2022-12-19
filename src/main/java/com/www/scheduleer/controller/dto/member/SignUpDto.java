package com.www.scheduleer.controller.dto.member;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignUpDto {
    @Email(message = "NOT_VALID_EMAIL")
    private String email;
    @NotBlank(message="PASSWORD_IS_MANDATORY")
    private String password;
    @NotBlank(message="NAME_IS_MANDATORY")
    private String name;
    private String picture;

}
