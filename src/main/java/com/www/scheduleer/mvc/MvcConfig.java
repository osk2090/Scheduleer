package com.www.scheduleer.mvc;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user");
        registry.addViewController("signup").setViewName("/member/signup");
        registry.addViewController("login").setViewName("login");
        registry.addViewController("admin").setViewName("/admin/admin");

    }
}
