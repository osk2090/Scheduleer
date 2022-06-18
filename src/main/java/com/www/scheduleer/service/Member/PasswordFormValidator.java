package com.www.scheduleer.service.Member;

import com.www.scheduleer.web.dto.member.PasswordFormDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordFormDto passwordFormDto = (PasswordFormDto) target;
        if (!passwordFormDto.getNewPassword().equals(passwordFormDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword", "wrong value", "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }
}
