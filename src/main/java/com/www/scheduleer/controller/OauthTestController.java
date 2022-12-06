package com.www.scheduleer.controller;

import com.www.scheduleer.config.auth.LoginUser;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/accounts")
public class OauthTestController {

    private final MemberService memberService;

    private final HttpSession httpSession;

    @GetMapping("/index")
    public String index(Model model, @AuthenticationPrincipal MemberInfoDto memberInfoDto) {

        if (memberInfoDto != null) {
            model.addAttribute("userName", memberInfoDto.getName());
        }

        return "/admin/index";
    }
}
