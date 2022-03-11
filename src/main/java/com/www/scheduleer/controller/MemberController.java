package com.www.scheduleer.controller;

import com.www.scheduleer.VO.Member;
import com.www.scheduleer.service.Member.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String insertMemberForm() {
        return "/member/insertMember";
    }

    @PostMapping("/new")
    public String insertMember(@ModelAttribute Member member) {
        memberService.addMember(member);
        return "/member/insertMember";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

//    @GetMapping("/find")
//    public String findMember(@RequestParam(value = "email") String email, Model model) {
//        Member m = memberService.getMember(email);
//        model.addAttribute("member", m);
//        return "/member/findMember";
//    }

}
