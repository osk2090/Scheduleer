package com.www.scheduleer.controller;

import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String index(Model model, @AuthenticationPrincipal MemberDto memberDto) {

        if (memberDto != null) {
            model.addAttribute("userName", memberDto.getName());
        }

        return "/admin/index";
    }
}
