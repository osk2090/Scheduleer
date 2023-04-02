package com.www.scheduleer.controller.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ChangePasswdDto {
    private String beforePasswd;
    private String afterPasswd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePasswdDto that = (ChangePasswdDto) o;
        return Objects.equals(beforePasswd, that.beforePasswd) && Objects.equals(afterPasswd, that.afterPasswd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beforePasswd, afterPasswd);
    }
}
