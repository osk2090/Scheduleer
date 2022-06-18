package com.www.scheduleer.controller;

import com.www.scheduleer.web.dto.member.MemberInfoDto;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OauthTestController {

    private final MemberService memberService;

    private final HttpSession httpSession;

    @GetMapping("/index")
    public String index(Model model) {
        MemberInfoDto user = (MemberInfoDto) httpSession.getAttribute("member");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "/admin/index";
    }
}
