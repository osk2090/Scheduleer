package com.www.scheduleer.config;

import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

public class SessionUser implements Serializable {
    public SessionUser(User user) {
    }
}
