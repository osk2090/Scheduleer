package com.www.scheduleer.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/member/main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("/admin/admin");
        registry.addViewController("/signup").setViewName("/member/signup");
        registry.addViewController("/list").setViewName("/member/list");
        registry.addViewController("/find").setViewName("/member/list");
        registry.addViewController("/info").setViewName("/member/info");

        registry.addViewController("/addBoard").setViewName("/board/write");
    }
}
