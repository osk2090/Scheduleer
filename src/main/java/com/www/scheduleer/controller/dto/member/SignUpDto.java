package com.www.scheduleer.controller.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class SignUpDto {
    @Email(message = "NOT_VALID_EMAIL")
    private String email;
    @NotBlank(message="PASSWORD_IS_MANDATORY")
    private String password;
    @NotBlank(message="NAME_IS_MANDATORY")
    private String name;
    private MultipartFile picture;

}
