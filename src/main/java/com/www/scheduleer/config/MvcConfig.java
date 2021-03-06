package com.www.scheduleer.config;

import com.www.scheduleer.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/member/main");
        registry.addViewController("/main").setViewName("main");//전체글이 나오는 실제메인 위에 있는 메인은 수정/삭제 예정
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("/admin/admin");
        registry.addViewController("/signup").setViewName("/member/signup");
        registry.addViewController("/signup1").setViewName("/member/agree");
        registry.addViewController("/list").setViewName("/member/list");
        registry.addViewController("/find").setViewName("/member/list");
        registry.addViewController("/info").setViewName("/member/info");
        registry.addViewController("/member/password").setViewName("/member/password");

        registry.addViewController("/addBoard").setViewName("/board/write");
        registry.addViewController("/board/detail").setViewName("/board/detail");
        registry.addViewController("/board/update").setViewName("/board/update");

        registry.addViewController("/index").setViewName("/admin/index");
    }
}
