package com.www.scheduleer.web.dto.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordFormDto {
    @Length(min = 8, max = 50)
    private String newPassword;

    @Length(min = 8, max = 50)
    private String newPasswordConfirm;
}
