package com.www.scheduleer.config.annotation;

import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    /*쿠키가 있으면 세션에 유저 정보 등록*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();

        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
        if (loginCookie != null) {
            Member member = memberService.checkMemberWithSessionKey(loginCookie.getValue());
            if (member != null) {
                httpSession.setAttribute("member", member);
            }
        }

        return true;
    }
}