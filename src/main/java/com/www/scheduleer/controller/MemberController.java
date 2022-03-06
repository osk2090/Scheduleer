package com.www.scheduleer.controller;

import com.www.scheduleer.VO.Member;
import com.www.scheduleer.service.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String insertMemberForm() {
        return "/member/insertMember";
    }

    @PostMapping("/new")
    public String insertMember(@ModelAttribute Member member) {
        memberService.save(member);
        return "/member/insertMember";
    }
}
