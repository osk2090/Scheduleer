package com.www.scheduleer.service.Member;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {//만약 세션이 있다면?
            String redirectUrl = (String) session.getAttribute("prevPage");//이전페이지의 주소를 로그인 후 리다이렉트할 주소로 선언
            if (redirectUrl != null) {//만약 이전페이지가 null이 아니면?
                session.removeAttribute("prevPage");//이전 페이지 삭제
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);//이전페이지로 리다이렉트
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
